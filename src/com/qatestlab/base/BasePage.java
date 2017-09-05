package com.qatestlab.base;

import com.qatestlab.properties.Properties;
import com.qatestlab.reporting.Reporter;
import org.openqa.selenium.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.awt.*;
import java.awt.event.InputEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public abstract class BasePage extends DriverContainer {
    public static int ELEMENT_MICRO_TIMEOUT_SECONDS = 1;
    public static int ELEMENT_EXTRASMALL_TIMEOUT_SECONDS = 5;
    public static int ELEMENT_SMALL_TIMEOUT_SECONDS = 15;
    public static int ELEMENT_TIMEOUT_SECONDS = 30;
    public static int ELEMENT_LONG_TIMEOUT_SECONDS = 60;
    public static int ELEMENT_EXTRALONG_TIMEOUT_SECONDS = 120;
    public static int ELEMENT_MEGA_EXTRALONG_TIMEOUT_SECONDS = 300;
  //  public static String DROPBOX_PROJECT_ENTITY_LINK = "https://www.dropbox.com/s/r6er5vbq10vkcdt/" +
  //          "Mayor%20Elections%202013%2C%20ENG%20-%20weeks%20encoded%2C%20MR-groups.zip?dl=0";
    public static String DROPBOX_PROJECT_ENTITY_LINK = "https://www.dropbox.com/sh/i2px1x63kt4fu1v/AABe6QX3Khntbn3DuWWUFV5oa?dl=0";


    public static String BASE_URL = Properties.getBaseUrl();
    public static String PROJECT_ENTITY_URL = Properties.getDropBoxLink(DROPBOX_PROJECT_ENTITY_LINK);
    public static String ADMIN_URL = Properties.getAdminUrl();

    protected void click(String message, Locator locator, Object... args) {
        Reporter.log(message);
        WebElement element = driver().findElement(locator.getLocator(args));
        driver().switchTo().defaultContent();
        element.click();
    }

    protected void clickEnterMain(){
        driver().findElement(By.id("Value")).sendKeys(Keys.ENTER);
    }

    protected void click(String message, WebElement element) {
        Reporter.log(message);
        element.click();
    }

    protected void clickJS(String message, Locator locator, Object... args) {
        Reporter.log(message);
        ((JavascriptExecutor) driver()).executeScript("arguments[0].click()", driver().findElement(locator.getLocator(args)));


    }

    protected void clickJS(String message, WebElement element) {
        Reporter.log(message);
        ((JavascriptExecutor) driver()).executeScript("arguments[0].click()", element);
    }

    protected void type(String message, String value, Locator locator, Object... args) {
        Reporter.log(message);
        WebElement input = driver().findElement(locator.getLocator(args));
        input.clear();

        input.sendKeys(value);

    }

    protected void typeNewline(String message, Locator locator, Object... args) {
        Reporter.log(message);
        WebElement input = driver().findElement(locator.getLocator(args));
        input.clear();
        input.sendKeys(Keys.ENTER);
    }

    protected Dimension getDimension(String message, Locator locator, Object... args) {
        Reporter.log(message);
        WebElement element = driver().findElement(locator.getLocator(args));
        return element.getSize();
    }

    protected List<WebElement> getElements(String message, Locator locator, Object... args) {
        Reporter.log(message);
        List<WebElement> elements = driver().findElements(locator.getLocator(args));
        return elements;
    }

    protected String getText(String message, Locator locator, Object... args) {
        Reporter.log(message);

        WebElement element = driver().findElement(locator.getLocator(args));
        String type = element.getTagName().toLowerCase();

        if (type.equals("input") || type.equals("textarea")) {
            String placeholder = element.getAttribute("placeholder");
            return (placeholder != null && placeholder.length() > 0)
                    ? element.getAttribute("value").replace(placeholder, "")
                    : element.getAttribute("value");
        }
        if (type.equals("select")) {
            return new Select(element).getFirstSelectedOption().getText();
        }
        return element.getText();
    }

    protected void waitForPresence(String message, Locator locator, Object... args) {
        waitForPresence(ELEMENT_TIMEOUT_SECONDS, message, locator, args);
    }

    protected void waitForPresence(int timeout, String message, Locator locator, Object... args) {
        Reporter.log(message);

        driver().manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(driver(), timeout);
        wait.until(ExpectedConditions.presenceOfElementLocated(locator.getLocator(args)));
        driver().manage().timeouts().implicitlyWait(ELEMENT_TIMEOUT_SECONDS, TimeUnit.SECONDS);
    }

    protected void waitForVisibility(String message, Locator locator, Object... args) {
        waitForVisibility(ELEMENT_TIMEOUT_SECONDS, message, locator, args);
    }

    protected void waitForVisibility(int timeout, String message, Locator locator, Object... args) {
        Reporter.log(message);

        driver().manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(driver(), timeout);
        /*Workaround to IEDriver bug with WebDriverException*/
        if (BaseTest.isDriverIE) {
            try {
                wait.until(ExpectedConditions.visibilityOfElementLocated(locator.getLocator(args)));
            } catch (WebDriverException e) {
                wait.until(ExpectedConditions.visibilityOfElementLocated(locator.getLocator(args)));
            }
        } else {
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator.getLocator(args)));
        }
        driver().manage().timeouts().implicitlyWait(ELEMENT_TIMEOUT_SECONDS, TimeUnit.SECONDS);
    }

    protected void waitForInvisibility(String message, Locator locator, Object... args) {
        waitForInvisibility(ELEMENT_MICRO_TIMEOUT_SECONDS, ELEMENT_TIMEOUT_SECONDS, message, locator, args);
    }

    protected void waitForInvisibility(
            int waitElementTimeout, int timeout, String message,
            Locator locator, Object... args) {
        Reporter.log(message);

        driver().manage().timeouts().implicitlyWait(waitElementTimeout, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(driver(), timeout);
        /*Workaround to IEDriver bug with WebDriverException*/
        if (BaseTest.isDriverIE) {
            try {
                wait.until(ExpectedConditions.invisibilityOfElementLocated(locator.getLocator(args)));
            } catch (WebDriverException e) {
                wait.until(ExpectedConditions.invisibilityOfElementLocated(locator.getLocator(args)));
            }
        } else {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(locator.getLocator(args)));
        }
        driver().manage().timeouts().implicitlyWait(ELEMENT_TIMEOUT_SECONDS, TimeUnit.SECONDS);
    }

    protected void waitToBeClickable(String message, Locator locator, Object... args) {
        waitToBeClickable(ELEMENT_TIMEOUT_SECONDS, message, locator, args);
    }

    protected void waitToBeClickable(int timeout, String message, Locator locator, Object... args) {
        Reporter.log(message);

        driver().manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(driver(), timeout);
        wait.until(ExpectedConditions.elementToBeClickable(locator.getLocator(args)));
        driver().manage().timeouts().implicitlyWait(ELEMENT_TIMEOUT_SECONDS, TimeUnit.SECONDS);

        // wait until the element on the same place
        WebElement element = driver().findElement(locator.getLocator(args));
        Point location;
        do {
            location = element.getLocation();
            BaseActions.wait(0, 500);
        } while (!location.equals(element.getLocation()));
    }

    /*
    * Checkboxes
    */
    protected boolean isCheckboxChecked(Locator locator, Object... args) {
        return driver().findElement(locator.getLocator(args)).isSelected();
    }

    protected boolean isCheckboxChecked(WebElement element) {
        return element.isSelected();
    }

    protected void setCheckboxState(String message, boolean checked, Locator locator, Object... args) {
        if (checked ^ this.isCheckboxChecked(locator, args)) {
            this.click(message, locator, args);
        }
    }

    protected void setCheckboxStateForAll(String message, boolean checked, Locator locator, Object... args) {
        List<WebElement> elements = driver().findElements(locator.getLocator(args));
        for (WebElement element : elements) {
            if (checked && !isCheckboxChecked(element)) {
                //this.click(message, element);
                clickJS(message, element);
            }
        }
    }

    protected int getCount(String message, Locator locator, Object... args) {
        return this.getCount(0, message, locator, args);
    }

    protected int getCount(int waitInSeconds, String message, Locator locator, Object... args) {
        Reporter.log(message);

        driver().manage().timeouts().implicitlyWait(waitInSeconds, TimeUnit.SECONDS);
        int size = driver().findElements(locator.getLocator(args)).size();
        driver().manage().timeouts().implicitlyWait(ELEMENT_TIMEOUT_SECONDS, TimeUnit.SECONDS);
        return size;
    }

    protected boolean isPresent(String message, Locator locator, Object... args) {
        return getCount(message, locator, args) > 0;
    }

    protected boolean isPresent(int waitInSeconds, String message, Locator locator, Object... args) {
        return getCount(waitInSeconds, message, locator, args) > 0;
    }

    protected boolean isVisible(String message, Locator locator, Object... args) {
        return driver().findElement(locator.getLocator(args)).isDisplayed();
    }

    protected boolean isVisible(int waitInSeconds, String message, Locator locator, Object... args) {
        driver().manage().timeouts().implicitlyWait(waitInSeconds, TimeUnit.SECONDS);
        return driver().findElement(locator.getLocator()).isDisplayed();
    }

    protected void executeJS(String message, String script, Locator locator, Object... args) {
        Reporter.log(message);
        ((JavascriptExecutor) driver()).executeScript(script, driver().findElement(locator.getLocator(args)));
    }

    protected void checkAlert(String message) {
        Reporter.log(message);
        WebDriverWait wait = new WebDriverWait(driver(), 10);
       // wait.until(ExpectedConditions.alertIsPresent());
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver().switchTo().alert();
        alert.accept();
    }

    protected String getAttributeValue(String message, String attribute, Locator locator, Object... args) {
        WebElement element = driver().findElement(locator.getLocator(args));
        return getAttributeValue(message, attribute, element);
    }

    protected String getAttributeValue(String message, String attribute, WebElement element) {
        Reporter.log(message);
        String value = element.getAttribute(attribute);
        return value;
    }

    protected boolean isAttributePresent(String message, String attribute, Locator locator, Object... args) {
        Reporter.log(message);
        Boolean result = false;
        WebElement element = driver().findElement(locator.getLocator(args));
        String value = element.getAttribute(attribute);
        if (value != null)
            result = true;
        return result;
    }

    protected void pasteText(String message, Locator locator, Object... args) {
        Reporter.log(message);
        WebElement element = driver().findElement(locator.getLocator(args));
        element.click();
        new Actions(driver()).keyDown(Keys.CONTROL).sendKeys(String.valueOf('\u0076')).perform();
        new Actions(driver()).keyUp(Keys.CONTROL).perform();

    }

    protected void uploadFile(String message, String filePath, Locator locator, Object... args) {
        Reporter.log(message);
        WebElement fileInput = driver().findElement(locator.getLocator(args));
        fileInput.sendKeys(filePath);
    }

    protected void dragAndDrop(String message, Locator from, Locator to, Object... args) {


/*
//        Point p = fromElement.getLocation();
//        int x = p.getX();
//        int y = p.getY();
//        Dimension d = fromElement.getSize();
//        int h = d.getHeight();
//        int w = d.getWidth();
//
//        Point p1 = toElement.getLocation();
//        int x1 = p1.getX();
//        int y1 = p1.getY();
//        Dimension d1 = toElement.getSize();
//        int h1 = d1.getHeight();
//        int w1 = d1.getWidth();
//
//        Robot r = null;
//        try {
//            r = new Robot();
//        } catch (AWTException e) {
//            e.printStackTrace();
//        }
//        r.mouseMove(x + (w/2), y+(h/2) +100);
//        r.mousePress(InputEvent.BUTTON1_MASK);
//
//        BaseActions.wait(1);
//
//        r.mouseMove(x1 + (w1/2), y1+(h1/2) +100);
//        r.mouseRelease(InputEvent.BUTTON1_MASK);
//        BaseActions.wait(1);
*/
        Reporter.log(message);
        WebElement fromElement = driver().findElement(from.getLocator(args));
        WebElement toElement = driver().findElement(to.getLocator());
        BaseActions.wait(1);

        (new Actions(driver()).clickAndHold(fromElement)).build().perform();
        BaseActions.wait(1);
        (new Actions(driver()).moveToElement(toElement)).perform();
        BaseActions.wait(1);
        (new Actions(driver()).release(toElement)).perform();

    }

    protected void dragAndDropReverse(String message, Locator from, Locator to, Object... args) {
        Reporter.log(message);
        WebElement fromElement = driver().findElement(from.getLocator());
        WebElement toElement = driver().findElement(to.getLocator(args));
        BaseActions.wait(1);

        (new Actions(driver()).clickAndHold(fromElement)).build().perform();
        BaseActions.wait(1);
        (new Actions(driver()).moveToElement(toElement)).perform();
        BaseActions.wait(1);
        (new Actions(driver()).release(toElement)).perform();
    }

    protected void selectDropDownListOptionByText(String message, String selectItemText, Locator locator, Object... args) {
        Reporter.log(message);

        Select dropDownList = new Select(driver().findElement(locator.getLocator(args)));
        // if element has wrong value we can try select item only by text
        try {
            dropDownList.selectByValue(selectItemText);
        } catch (NoSuchElementException e) {
            dropDownList.selectByVisibleText(selectItemText);
        }
    }

    protected String getSelectedOption(String message, Locator locator, Object... args) {

        Select listOptions = new Select(driver().findElement(locator.getLocator(args)));

        WebElement option = listOptions.getFirstSelectedOption();
        return option.getText();
    }

    protected boolean isEnabled(String message, Locator locator, Object... args) {
        Reporter.log(message);
        return driver().findElement(locator.getLocator(args)).isEnabled();
    }

    public void clickWithJS(String logMessage, Locator locator, Object... args) {
        Reporter.log(logMessage);
        final String javaScript = "if(document.createEvent){" +
                "var evObj = document.createEvent('MouseEvents');" +
                "evObj.initEvent('click', true, false);" + "" +
                "arguments[0].dispatchEvent(evObj);" +
                "} else if(document.createEventObject){" +
                "arguments[0].fireEvent('onclick');" +
                "}";
        ((JavascriptExecutor) driver()).executeScript(javaScript, driver().findElement(locator.getLocator(args)));
    }

//    public String getBrowserVersion() {
//
//        final String javaScript = "navigator.sayswho= (function(){\n" +
//                "    var ua= navigator.userAgent, tem, \n" +
//                "    M= ua.match(/(opera|chrome|safari|firefox|msie|trident(?=\\/))\\/?\\s*(\\d+)/i) || [];\n" +
//                "    if(/trident/i.test(M[1])){\n" +
//                "        tem=  /\\brv[ :]+(\\d+)/g.exec(ua) || [];\n" +
//                "        return 'IE '+(tem[1] || '');\n" +
//                "    }\n" +
//                "    if(M[1]=== 'Chrome'){\n" +
//                "        tem= ua.match(/\\b(OPR|Edge)\\/(\\d+)/);\n" +
//                "        if(tem!= null) return tem.slice(1).join(' ').replace('OPR', 'Opera');\n" +
//                "    }\n" +
//                "    M= M[2]? [M[1], M[2]]: [navigator.appName, navigator.appVersion, '-?'];\n" +
//                "    if((tem= ua.match(/version\\/(\\d+)/i))!= null) M.splice(1, 1, tem[1]);\n" +
//                "    return M.join(' ');\n" +
//                "})();";
//        return (String) ((JavascriptExecutor) driver()).executeScript(javaScript);
//    }

    protected Actions moveToElement(String logMessage, Locator locator, int xOffset, int yOffset, Object... args) {
        Reporter.log(logMessage);
        WebElement element = driver().findElement(locator.getLocator(args));

        return new Actions(driver()).moveToElement(element, xOffset, yOffset);
    }

    protected void scrollDown(String message){
        Reporter.log(message);
        ((JavascriptExecutor) driver()).executeScript("scroll(0, 250);");
    }

    protected void scrollToElement(String logMessage, Locator locator,Object... args) {
        Reporter.log(logMessage);
        WebElement element = driver().findElement(locator.getLocator(args));
        ((JavascriptExecutor) driver()).executeScript("arguments[0].scrollIntoView();", element);
    }

    public void closeTab(String windowHandle) {
        driver().switchTo().window(windowHandle);
        driver().close();
    }

    public void acceptAlertWindow(){
        BaseActions.wait(3);
        Alert alert = driver().switchTo().alert();
        alert.accept();
    }

    public boolean isDriverIE(){
        return BaseTest.isDriverIE;
    }

    protected boolean isElementEnabledAndDisplayed(String logMessage, Locator locator, Object... args){
        Reporter.log(logMessage);
        WebElement element = driver().findElement(locator.getLocator(args));
        return element.isDisplayed() && element.isEnabled();
    }

    protected String getElementCssValue(String message, String attributeName, Locator locator, Object... args) {
        Reporter.log(message);
        return getElement(locator, args).getCssValue(attributeName);

    }

    protected WebElement getElement(Locator locator, Object... args) {
        By by = locator.getLocator(args);
        return BaseTest.driver().findElement(by);
    }

    protected void css(Locator locator, Object... args){
        WebElement we = driver().findElement(locator.getLocator(args));
        JavascriptExecutor executor = (JavascriptExecutor)driver();
        String script = "var s = '';" +
                "var o = getComputedStyle(arguments[0]);" +
                "for(var i = 0; i < o.length; i++){" +
                "s+=o[i] + ':' + o.getPropertyValue(o[i])+';';}" +
                "return s;";

        Reporter.log((String) executor.executeScript(script, we));
    }

    protected void doubleClick(String logMessage,Locator locator, Object... args){
        Reporter.log(logMessage);
        Actions act = new Actions(driver());
        act.doubleClick(driver().findElement(locator.getLocator(args))).build().perform();

    }

}
