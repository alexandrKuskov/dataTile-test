package eu.datatiler.pages;

import com.qatestlab.base.BasePage;
import com.qatestlab.base.Locator;
import com.qatestlab.base.LocatorTypes;

public class LoginPage extends BasePage {
    private Locator loginForm = new Locator(LocatorTypes.CSS, "form.t_loginForm");
    private Locator emailField = new Locator(LocatorTypes.NAME, "username");
    private Locator passwordField = new Locator(LocatorTypes.NAME, "password");
    private Locator loginButton = new Locator(LocatorTypes.CSS, "button[type='submit']");

    public void waitForLoginFormLoad(){
        waitForVisibility("Wait until login form is displayed", loginForm);
    }

    public boolean loginFormIsDisplayed() {
        return isElementEnabledAndDisplayed("Login form enabled and displayed", loginForm);
    }

    public void typeEmail(String value){
        type("Input email in an email field", value, emailField);
    }

    public String getEmailText() {
        return getText("Get email text", emailField);
    }

    public void typePassword(String value){
        type("Input password in a password field", value, passwordField);
    }

    public String getPasswordText() {
        return getText("Get password text", passwordField);
    }

    public void clickOnLoginButton(){
        click("Click on login button", loginButton);
    }
}
