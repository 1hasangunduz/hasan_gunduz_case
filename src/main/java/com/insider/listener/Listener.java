package com.insider.listener;

import com.insider.base.Driver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import static com.insider.base.Driver.takeScreenshot;


public class Listener implements ITestListener {

    private static final Logger LOGGER = LogManager.getLogger(Listener.class);

    private static String getTestMethodName(ITestResult iTestResult) {
        return iTestResult.getMethod().getConstructorOrMethod().getName();
    }

    @Override
    public void onStart(ITestContext iTestContext) {
        LOGGER.info("I am in onStart method " + iTestContext.getName());
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        LOGGER.info("I am in onFinish method " + iTestContext.getName());
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {
        LOGGER.info("Test Started: " + getTestMethodName(iTestResult));
    }


    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        LOGGER.info(getTestMethodName(iTestResult) + " test is succeed.");
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        LOGGER.info("Test Failed: " + getTestMethodName(iTestResult));
        LOGGER.error(iTestResult.getThrowable());

        WebDriver driver = Driver.getDriver();

        if (driver == null) {
            LOGGER.warn("Driver is null at test failure. Screenshot cannot be taken.");
            return;
        }

        try {
            LOGGER.info("Fail URL: " + driver.getCurrentUrl());
            takeScreenshot("Fail Screenshot");
        } catch (Exception e) {
            LOGGER.error("Screenshot or URL capture failed: " + e.getMessage());
        }
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        LOGGER.info("Test Skipped: " + getTestMethodName(iTestResult));

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        LOGGER.info("Test Failed Within Success Percentage:  ", getTestMethodName(iTestResult));
    }


}
