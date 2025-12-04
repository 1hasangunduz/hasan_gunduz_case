package com.insider.base;

import com.insider.utilities.Log;
import com.insider.utilities.ReusableMethods;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.time.Duration;

import static org.testng.Assert.assertEquals;


public class BasePage extends ReusableMethods {


    /**
     * @param env : choose environment
     * @param url : choose url for environment (example: NoAdditionalQueues) or full url (example: /empty)
     */
    public void navigateToUrl(String env, String url) {

        var baseUrl = Environment.getBaseUrl(env);
        Assert.assertNotNull(baseUrl, "Base URL is null! Check environment value: " + env);

        if (url == null) {
            url = "";
        }

        if (url.startsWith("/")) {
            url = url.substring(1);
        }

        var fullUrl = baseUrl + "/" + url;

        try {
            Driver.getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(40));
            Driver.getDriver().get(fullUrl);
            Log.pass("Application launched! URL: " + fullUrl);
            waitMs(2000);
            acceptCookies();
        } catch (Exception e) {
            Log.fail("Navigation failed: " + e.getMessage());
        }
    }

    @Step("Navigate to URL")
    public void navigateToUrl() {
        var env = config.env();
        String baseUrl;
        try {
            baseUrl = config.baseUrl();
            if (baseUrl == null || baseUrl.isEmpty()) {
                Log.warning("Invalid or missing base URL for environment: " + env);
                return;
            }
            Driver.getDriver().get(baseUrl);
            Driver.getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(40));
            Log.pass("Application launched! URL: " + baseUrl);
            waitMs(3000);
            acceptCookies();
        } catch (Exception e) {
            Log.error("Error navigating to URL: " + e.getMessage());
        }
    }

    public class Environment {

        public static String getBaseUrl(String env) {
            if (env == null || env.isEmpty()) {
                Log.warning("Environment cannot be null or empty!");
                return null;
            }

            return switch (env.toLowerCase()) {
                case "prod" -> "https://useinsider.com";
                case "preprod" -> "https://preprod.useinsider.com";
                case "cloud" -> "https://cloud.useinsider.com";
                case "test" -> "https://test.useinsider.com";
                default -> {
                    Log.warning("Invalid environment provided: " + env);
                    yield null;
                }
            };
        }
    }


    /**
     * This method is used to accept cookies.
     * If it is not displayed, it will print a message on the console.
     * If it is displayed, it will click on the 'Accept All Cookies' button.
     */
    @Step("Accept browser cookies.")
    public void acceptCookies() {
        var acceptCookiesButton = Driver.getDriver().findElement(By.cssSelector("#cookie-law-info-bar #wt-cli-accept-all-btn"));
        if (isDisplayElement(acceptCookiesButton)) {
            Log.pass("->" + acceptCookiesButton.getSize().getHeight() + "x" + acceptCookiesButton.getSize().getWidth());
            assertEquals(checkWebElementSize(acceptCookiesButton), "29x112", " The 'Accept All Cookies' button size is not correct.");
            Log.pass("Confirmed that the 'Accept All Cookies' button size is correct. Button size --> " + checkWebElementSize(acceptCookiesButton));
            clickWithJS(acceptCookiesButton, "Accept all browser cookies!");
        } else {
            Log.pass("The 'Accept Cookies' button is not displayed.");
        }
    }

    /**
     * @param webElement : get Images size like : 203x203
     */
    public String checkElementSize(WebElement webElement) {
        WebElement element = waitVisibleByLocator(webElement);
        return (element.getSize().getHeight() + "x" + element.getSize().getWidth());

    }

    @Step("Check Web Element Size")
    public String checkWebElementSize(WebElement webElement) {
        return (webElement.getSize().getHeight() + "x" + webElement.getSize().getWidth());
    }

    public void verifyElementDisplayed(WebElement element, String elementName) {
        try {
            scrollToElementBlockCenter(element);
            boolean isDisplayed = isDisplayElement(element);
            if (isDisplayed) {
                Log.pass(elementName + " is displayed.");
            } else {
                Log.fail(elementName + " is NOT displayed.");
            }
        } catch (Exception e) {
            Log.fail(elementName + " is NOT found on the page. Exception: " + e.getMessage());
        }
    }

    @Step("Verify redirected URL contains: {url}")
    public void redirectControl(String url) {
        waitForUrlContains(url, 3);
        Log.pass("Redirected URL: " + url);
    }


}
