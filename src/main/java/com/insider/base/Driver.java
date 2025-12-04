package com.insider.base;

import com.insider.configs.Configs;
import com.insider.utilities.Log;
import io.qameta.allure.Allure;
import lombok.Getter;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.io.ByteArrayInputStream;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class Driver {

    private Driver() {}

    private static final ThreadLocal<WebDriver> DRIVER_TL = new ThreadLocal<>();
    private static final Configs config = Configs.getConfigs();

    @Getter
    private static String driverType;

    private static final int SCRIPT_TIMEOUT = 7;
    private static final int DEFAULT_TIMEOUT = 20;
    private static final int PAGE_LOAD_TIMEOUT = 120;

    public static WebDriver createDriver(String browser) {

        WebDriver driver;

        switch (browser.toLowerCase()) {
            case "chrome" -> driver = initializeChromeDriver();
            case "firefox" -> driver = initializeFirefoxDriver();
            default -> driver = initializeMobileDriver(browser);
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(DEFAULT_TIMEOUT));
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(SCRIPT_TIMEOUT));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(PAGE_LOAD_TIMEOUT));

        DRIVER_TL.set(driver);
        return driver;
    }

    public static void quitDriver() {
        WebDriver driver = DRIVER_TL.get();
        if (driver != null) {
            try {
                driver.quit();
                Log.pass("Driver quit successfully");
            } catch (Exception e) {
                Log.error("Error during driver.quit(): " + e.getMessage());
            } finally {
                DRIVER_TL.remove();
            }
        }
    }

    public static void setDriverType(String driverType) {
        Driver.driverType = driverType;
    }

    public static WebDriver getDriver() {
        WebDriver driver = DRIVER_TL.get();
        if (driver == null) {
            Log.fail("WebDriver is null! Was quit() called too early?");
        }
        return driver;
    }
    /* ---------------------- BROWSER INITIALIZERS ---------------------- */

    private static WebDriver initializeChromeDriver() {
        setDriverType("chrome");
        ChromeOptions options = chromeOptions();
        options.addArguments(config.isHeadless() ? "--headless" : "--start-maximized");
        return new ChromeDriver(options);
    }

    private static WebDriver initializeFirefoxDriver() {
        setDriverType("firefox");
        System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver");
        FirefoxOptions options = firefoxOptions();
        options.addArguments(config.isHeadless() ? "--headless" : "--start-fullscreen");
        return new FirefoxDriver(options);
    }

    private static WebDriver initializeMobileDriver(String deviceName) {
        setDriverType(deviceName);
        ChromeOptions options = chromeOptions();
        Map<String, Object> mobile = new HashMap<>();
        mobile.put("deviceName", deviceName);
        options.setExperimentalOption("mobileEmulation", mobile);
        return new ChromeDriver(options);
    }

    /* ---------------------- OPTIONS ---------------------- */

    private static ChromeOptions chromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments(
                "--incognito",
                "--window-size=1920,1080",
                "--ignore-certificate-errors",
                "--disable-infobars",
                "--disable-notifications",
                "--disable-extensions",
                "--disable-popup-blocking",
                "--disable-dev-shm-usage",
                "--no-sandbox",
                "--disable-gpu",
                "--lang=en_US"
        );
        return options;
    }

    private static FirefoxOptions firefoxOptions() {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments(
                "--private",
                "--lang=en-US",
                "--disable-notifications",
                "--disable-extensions",
                "--disable-gpu",
                "--ignore-certificate-errors"
        );
        return options;
    }

    /* ---------------------- SCREENSHOT ---------------------- */

    public static void takeScreenshot(String name) {
        try {
            if (getDriver() == null)
                return;

            byte[] screenshotBytes = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment("Screenshot - " + name, new ByteArrayInputStream(screenshotBytes));

        } catch (Exception e) {
            Log.error("Screenshot failed: " + e.getMessage());
        }
    }
}
