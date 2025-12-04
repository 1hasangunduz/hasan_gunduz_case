package com.insider.base;

import com.insider.listener.Listener;
import com.insider.listener.RetryListener;
import org.testng.ITestResult;
import org.testng.annotations.*;

@Listeners({Listener.class, RetryListener.class})
public class BaseTest extends BasePage {

    public static final ThreadLocal<String> testNameTL = new ThreadLocal<>();

    @BeforeMethod(alwaysRun = true)
    @Parameters({"browser"})
    public void setup(@Optional("chrome") String browser, ITestResult result) {

        if (result != null) {
            testNameTL.set(result.getMethod().getMethodName());
        }

        Driver.createDriver(browser);

        System.out.println("Thread: " + Thread.currentThread().getName()
                + " - Driver: " + System.identityHashCode(Driver.getDriver()));
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        System.out.println("QUIT CALLED by thread " + Thread.currentThread().getId()
                + " for test: " + result.getName()
                + " | Driver hash: " + System.identityHashCode(Driver.getDriver()));
        Driver.quitDriver();
    }
}
