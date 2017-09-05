package com.qatestlab.base;


public abstract class BaseActions extends DriverContainer {
    public static void wait(int seconds) {
        wait(seconds, 0);
    }

    public static void wait(int seconds, int milliseconds) {
        try {
            Thread.sleep(seconds * 1000 + milliseconds);
        } catch (Exception e) { }
    }

    /*Unfortunately, IE can't delete HttpOnly cookies*/
    public void clearSession() {
        if (!BaseTest.isDriverEDGE){
            driver().manage().deleteAllCookies();
        }
        if (BaseTest.isDriverIE){
            driver().manage().deleteCookieNamed("JSESSIONID");
        }
       wait(BasePage.ELEMENT_EXTRASMALL_TIMEOUT_SECONDS);
    }


}
