package com.qatestlab.base;

import com.qatestlab.properties.Properties;
import com.qatestlab.properties.PropertiesNames;
import com.qatestlab.reporting.Reporter;
import eu.datatiler.actions.Actions;
import eu.datatiler.pages.Pages;

import org.apache.commons.lang3.SystemUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;

import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerDriverLogLevel;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public abstract class BaseTest {
    private static WebDriver driver;
    public static boolean isDriverIE = false;
    public static boolean isDriverEDGE = false;
    public static boolean isDriverFIREFOX = false;
    public static String downloadFilepath  = null ;
    public static String winDir  = "C:\\datatileProjects";
    public static String linDir  = "/tmp/datalineEntities";

    public static WebDriver driver() {
        return driver;
    }
   // public static String nodeURL = "http://192.168.244.1:5555/wd/hub";
    public static String nodeURL = System.getProperty("nodeURL");
    public static boolean isGrid = Boolean.parseBoolean(System.getProperty("gridStatus"));

    @BeforeMethod
    public void setUp() throws MalformedURLException {

        String platformName = System.getProperty("platform");

        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.CLIENT, Level.SEVERE);

        Platform platform;
        if (SystemUtils.IS_OS_WINDOWS) {
            platform = Platform.WINDOWS;
            downloadFilepath = winDir;
        } else if (SystemUtils.IS_OS_LINUX) {
            platform = Platform.LINUX;
            downloadFilepath = linDir;
        } else if (SystemUtils.IS_OS_MAC || SystemUtils.IS_OS_MAC_OSX || SystemUtils.IS_OS_MAC_OSX_CHEETAH || SystemUtils.IS_OS_MAC_OSX_PUMA || SystemUtils.IS_OS_MAC_OSX_JAGUAR
                || SystemUtils.IS_OS_MAC_OSX_TIGER || SystemUtils.IS_OS_MAC_OSX_LEOPARD || SystemUtils.IS_OS_MAC_OSX_LEOPARD || SystemUtils.IS_OS_MAC_OSX_SNOW_LEOPARD
                || SystemUtils.IS_OS_MAC_OSX_LION || SystemUtils.IS_OS_MAC_OSX_MOUNTAIN_LION || SystemUtils.IS_OS_MAC_OSX_MAVERICKS || SystemUtils.IS_OS_MAC_OSX_YOSEMITE) {
            platform = Platform.MAC;
        } else {
            platform = Platform.ANY;
        }

        switch (Properties.getBrowser()) {

            case FIREFOX:
                String firefoxDriverPath = Properties.getFirefoxDriverPath();
                System.setProperty("webdriver.gecko.driver", firefoxDriverPath);
                DesiredCapabilities capabilities = DesiredCapabilities.firefox();
                capabilities.setBrowserName("firefox");
                capabilities.setPlatform(platform);
                capabilities.setCapability("marionette", true);
                capabilities.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
                capabilities.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
                if (isGrid){
                    driver = new RemoteWebDriver(new URL(nodeURL), capabilities){
                        @Override
                        public WebElement findElement(By by) {
                            try {
                                return by.findElement(this);
                            } catch (org.openqa.selenium.NoSuchElementException nse) {
                                Field f = null;
                                try {
                                    f = Throwable.class.getDeclaredField("detailMessage");
                                } catch (NoSuchFieldException e) {
                                    throw nse;
                                }
                                f.setAccessible(true);
                                try {
                                    String error = "\n" + by.toString() + "\n" + f.get(nse);
                                    f.set(nse, error);
                                } catch (IllegalAccessException ia) {
                                }
                                throw nse;
                            }
                        }
                    };
                }
                else {
                    driver = new FirefoxDriver(capabilities){
                        @Override
                        public WebElement findElement(By by) {
                            try {
                                return by.findElement(this);
                            } catch (org.openqa.selenium.NoSuchElementException nse) {
                                Field f = null;
                                try {
                                    f = Throwable.class.getDeclaredField("detailMessage");
                                } catch (NoSuchFieldException e) {
                                    throw nse;
                                }
                                f.setAccessible(true);
                                try {
                                    String error = "\n" + by.toString() + "\n" + f.get(nse);
                                    f.set(nse, error);
                                } catch (IllegalAccessException ia) {
                                }
                                throw nse;
                            }
                        }
                    };
                }
                isDriverFIREFOX=true;
                break;

            case EDGE:

                String edgeDriverPath = Properties.getEdgeDriverPath();
                System.setProperty("webdriver.edge.driver", edgeDriverPath);
                capabilities = DesiredCapabilities.edge();
                capabilities.setBrowserName("edge");
                capabilities.setPlatform(org.openqa.selenium.Platform.WIN10);

                capabilities.setBrowserName(BrowserType.EDGE);
                capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
                capabilities.setCapability(CapabilityType.SUPPORTS_JAVASCRIPT , true);
                driver = new EdgeDriver(capabilities);
                isDriverEDGE = true;

                break;

            case CHROME:
            default:
                String chromeDriverPath = Properties.getChromeDriverPath();
                System.setProperty("webdriver.chrome.driver", chromeDriverPath);
                ChromeOptions options = new ChromeOptions();
                options.addArguments("test-type");
                capabilities = DesiredCapabilities.chrome();
                capabilities.setBrowserName("chrome");
                capabilities.setPlatform(platform);
                capabilities.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
               /*
                if(SystemUtils.IS_OS_WINDOWS){
                    downloadFilepath = "C:\\datatileProjects";
                }
                if(SystemUtils.IS_OS_LINUX){
                    downloadFilepath = "\\home\\tester\\datatileProjects";
                }
                */
                HashMap<String, Object> chromePrefs = new HashMap<>();
                chromePrefs.put("profile.default_content_settings.popups", 0);
                chromePrefs.put("download.default_directory", downloadFilepath);

                options.setExperimentalOption("prefs", chromePrefs);

                capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                capabilities.setCapability(ChromeOptions.CAPABILITY, options);


                if (isGrid){
                    driver = new RemoteWebDriver(new URL(nodeURL), capabilities){
                        @Override
                        public WebElement findElement(By by) {
                            try {
                                return by.findElement(this);
                            } catch (org.openqa.selenium.NoSuchElementException nse) {
                                Field f = null;
                                try {
                                    f = Throwable.class.getDeclaredField("detailMessage");
                                } catch (NoSuchFieldException e) {
                                    throw nse;
                                }
                                f.setAccessible(true);
                                try {
                                    String error = "\n" + by.toString() + "\n" + f.get(nse);
                                    f.set(nse, error);
                                } catch (IllegalAccessException ia) {
                                }
                                throw nse;
                            }
                        }
                    };
                    driver.manage().window().setSize(new Dimension(1920, 1080));
                }
                else {
                    driver = new ChromeDriver(capabilities) {
                        @Override
                        public WebElement findElement(By by) {
                            try {
                                return by.findElement(this);
                            } catch (org.openqa.selenium.NoSuchElementException nse) {
                                Field f = null;
                                try {
                                    f = Throwable.class.getDeclaredField("detailMessage");
                                } catch (NoSuchFieldException e) {
                                    throw nse;
                                }
                                f.setAccessible(true);
                                try {
                                    String error = "\n" + by.toString() + "\n" + f.get(nse);
                                    f.set(nse, error);
                                } catch (IllegalAccessException ia) {
                                }
                                throw nse;
                            }
                        }
                    };
                }
                break;

            case IE:
                String IEDriverPath = Properties.getIEDriverPath();
                System.setProperty("webdriver.ie.driver", IEDriverPath);
                System.setProperty("webdriver.ie.driver.logfile", System.getProperty(PropertiesNames.REPORT_DIR.toString()) + File.separator + "consoleLog" +
                        Properties.getBrowser().toString() + ".txt");
                System.setProperty("webdriver.ie.driver.loglevel", String.valueOf(InternetExplorerDriverLogLevel.TRACE));
                capabilities = DesiredCapabilities.internetExplorer();
                capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
                InternetExplorerDriverService service = InternetExplorerDriverService.createDefaultService();
                capabilities.setPlatform(platform);
                capabilities.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
                capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                capabilities.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, true);
                capabilities.setCapability(InternetExplorerDriver.UNEXPECTED_ALERT_BEHAVIOR, true);
                capabilities.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, false);
                capabilities.setJavascriptEnabled(true);
                capabilities.setCapability("nativeEvents", true);
                // new EventFiringWebDriver(new RemoteWebDriver(new URL("- hub name - :4444/wd/hub"), capability));
                if (isGrid){
                    driver = new EventFiringWebDriver(new RemoteWebDriver(new URL(nodeURL), capabilities));
                }
                else {
                    driver = new InternetExplorerDriver(capabilities);
                }
                isDriverIE = true;
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(BasePage.ELEMENT_EXTRALONG_TIMEOUT_SECONDS, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(BasePage.ELEMENT_MEGA_EXTRALONG_TIMEOUT_SECONDS, TimeUnit.SECONDS);

    }

    @AfterMethod
    public void savingLog(Method method) {
        switch (Properties.getBrowser()) {
            case IE:
                break;

            case FIREFOX:
                break;

            case EDGE:
                break;

            default:
                LogEntries logEntries = driver.manage().logs().get(LogType.BROWSER);
                try (BufferedWriter bufferWritter = new BufferedWriter(
                        new FileWriter(System.getProperty(PropertiesNames.REPORT_DIR.toString()) + File.separator + "consoleLog" +
                                Properties.getBrowser().toString(), true))) {

                    bufferWritter.append("Method: " + method.getName());
                    bufferWritter.newLine();
                    for (LogEntry entry : logEntries) {
                        bufferWritter.append("Log:" + (new Date(entry.getTimestamp()) + " " + entry.getLevel() + " " + entry.getMessage()));
                        bufferWritter.newLine();
                        Reporter.log("Log:" + (new Date(entry.getTimestamp()) + " " + entry.getLevel() + " " + entry.getMessage()));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
        tearDown();
    }

    @AfterClass
    public void tearDown() {
        boolean makeAllScreenshots = Reporter.isMakeScreenShotOnLog();
        Reporter.makeScreenshotOnLog(false);
        Reporter.log(" * Stopping WebDriver");
        this.stopDriver();
        Reporter.makeScreenshotOnLog(makeAllScreenshots);
    }

    protected void stopDriver() {
        Actions.clear();
        Pages.clear();


        org.testng.Reporter.log("Windows: close app", true);
        Reporter.log("stop driver method");
        driver.quit();
    }

    protected void skipTest(String message) {
        throw new SkipException(message);
    }
}
