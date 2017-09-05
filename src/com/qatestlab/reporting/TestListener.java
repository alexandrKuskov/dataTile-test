package com.qatestlab.reporting;

import com.qatestlab.base.BaseTest;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;



public class TestListener extends TestListenerAdapter {

    /*
    * When IE bug with black screenshot occurs, uses Robot to make one.
    * */
    @Override
    public void onTestFailure(ITestResult result) {
        org.testng.Reporter.setCurrentTestResult(result);
        String imageName = result.getName() + System.currentTimeMillis() + ".png";
        String path = System.getProperty("image.dir") + File.separatorChar + "html" + File.separatorChar;
        try {
            File screenshotAs = ((TakesScreenshot) BaseTest.driver()).getScreenshotAs(OutputType.FILE);
            /* Normally, screenshot length would be about 30_000*/
            if (screenshotAs.length() < 100_000) {
                FileUtils.copyFile(screenshotAs, new File(path + imageName));
            } else {
                Robot robot = new Robot();
                BufferedImage bi = robot.createScreenCapture(new Rectangle(1920, 1080));
                FileUtils.forceMkdir(new File(path));
                ImageIO.write(bi, "png", new File(path + imageName));
            }
            Reporter.log("screenshot saved  <br></br> <a href = '" + imageName + "'><img src = '" + imageName + "' width='600'/> </a>");

        } catch (Exception e) {
            Reporter.log("Failed to save screenshot");
            e.printStackTrace();
        }
    }
}
