package com.qatestlab.reporting;

import com.qatestlab.base.BaseTest;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Reporter {
    public static void logAction(String msg) {
        log(String.format("<b>%s</b>", msg));
    }

    private static boolean active = true;
    private static boolean makeScreenShotOnLog = System.getProperty("makeScreenShotOnLog", "false").equalsIgnoreCase("true");
    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("H:mm:ss:SSS");

    public static void log(String s) {
        if (active) {
            org.testng.Reporter.log("[" + FORMAT.format(System.currentTimeMillis()) + "]: " + Formatter.escapeCharacters(s) + "<br></br>");
        }
        if (active && makeScreenShotOnLog) {
            String imageName = System.currentTimeMillis() + ".png";
            org.testng.Reporter.log("<a href = '" + imageName + "'><img src = '" + imageName + "' width='400'/></a><br></br>");
            try {
                File scrFile = ((TakesScreenshot) BaseTest.driver()).getScreenshotAs(OutputType.FILE);
                FileUtils.copyFile(scrFile, new File(System.getProperty("report.dir") + File.separatorChar + "html" + File.separatorChar + imageName));
            } catch (Exception e) {
                org.testng.Reporter.log("Error saving screenshot!<br></br>");
            }
        }
    }

    public static void log(Throwable throwable) {
        if (active) {
            org.testng.Reporter.log("[" + FORMAT.format(Calendar.getInstance().getTime()) + "]: " + Formatter.escapeCharacters(throwable.getMessage()) + "<br></br>");
            for (StackTraceElement stack : throwable.getStackTrace()) {
                org.testng.Reporter.log(stack.toString() + "<br></br>");
            }
        }
    }

    public static void setActive(boolean active) {
        Reporter.active = active;
    }

    public static boolean isMakeScreenShotOnLog() {
        return makeScreenShotOnLog;
    }

    public static void makeScreenshotOnLog(boolean makeScreenShotOnLog) {
        Reporter.makeScreenShotOnLog = makeScreenShotOnLog;
    }

    public static class Formatter {

        /**
         * Escape special characters for HTML logs
         * @param string string with unescaped characters
         * @return string with escaped characters
         */
        public static String escapeCharacters(String string) {
            return string.replace("&", "&amp;");
        }

        /**
         * Rounds to 2 digits after point.
         */
        public static float roundFractional(float number) {
            return roundFractional(number, 2);
        }

        public static float roundFractional(float number, int precision) {
            return (float) Math.round(number * (float) Math.pow(10, precision)) / (float) Math.pow(10, precision);
        }

        public static String unformatPhone(String formatterPhone) {
            return formatterPhone.replace("(", "").replace(")", "").replace("-", "").replace(" ", "");
        }
    }
}
