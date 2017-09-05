package com.qatestlab.properties;

import org.apache.commons.lang3.SystemUtils;

import java.nio.file.Paths;

public class Properties {

    public static BrowserTypes getBrowser() {
        try {
            return BrowserTypes.valueOf(System.getProperty(PropertiesNames.BROWSER.toString()).toUpperCase());
        } catch (Exception e) {
            return BrowserTypes.CHROME;
        }
    }

    private static String getDriversDir() {
        return System.getProperty(PropertiesNames.DRIVERS_DIR.toString());
    }

    public static String getChromeDriverPath() {
        String basePath = getDriversDir();
        if (basePath == null)
            return null;

        if (SystemUtils.IS_OS_WINDOWS) {
            return Paths.get(basePath, "chromedriver.exe").toString();
        }
        if (SystemUtils.IS_OS_LINUX) {
            return Paths.get(basePath, "chromedriver_linu").toString();
        }
        if (SystemUtils.IS_OS_MAC) {
            return Paths.get(basePath, "chromedriver_mac").toString();
        }

        return null;
    }

    public static String getEdgeDriverPath(){

        return Paths.get(getDriversDir(), "MicrosoftWebDriver.exe").toString();
    }

    public static String getFirefoxDriverPath() {
        String basePath = getDriversDir();
        if (basePath == null)
            return null;

        if (SystemUtils.IS_OS_WINDOWS) {
            return Paths.get(getDriversDir(), "geckodriver.exe").toString();
        }
        if (SystemUtils.IS_OS_LINUX) {
            return Paths.get(basePath, "geckodriver_lin").toString();
        }
            return null;
    }


    public static String getIEDriverPath() {
        return Paths.get(getDriversDir(), "IEDriverServer.exe").toString();
    }

    public static String getUploadFile() {
        return System.getProperty(PropertiesNames.UPLOAD_DIR.toString()) + PropertiesNames.PROJECT_NAME;
    }

    public static String getEncodeFile() {
        return System.getProperty(PropertiesNames.UPLOAD_DIR.toString()) + PropertiesNames.ENCODER_NAME;
    }

    public static String getImageFile() {
        return System.getProperty(PropertiesNames.UPLOAD_DIR.toString()) + PropertiesNames.IMAGE_NAME;
    }

    public static String getBaseUrl() {
        return System.getProperty(PropertiesNames.BASE_URL.toString(), "http://develop.labs.datatile.eu/");
    }

    public static String getDropBoxLink(String link) {
        return System.getProperty(PropertiesNames.BASE_URL.toString(), link);
    }

    public static String getAdminUrl() {
        return System.getProperty(PropertiesNames.ADMIN_URL.toString(), getBaseUrl() + "entity/account/admin");
    }

    public static String getConfigDir() {
        return System.getProperty(PropertiesNames.CONFIG_DIR.toString());
    }

    public static String getVersionNumber() {
        return System.getProperty(PropertiesNames.BUILD_VERSION.toString());
    }

    public static String getFooterReferenceText() {
        return System.getProperty(PropertiesNames.REFERENCE.toString(), "Powered by DataTile");
    }
}
