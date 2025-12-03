package com.insider.base;

import com.insider.utilities.ConfigReader;
import com.insider.utilities.Log;
import com.insider.utilities.ReusableMethods;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.Duration;

import static org.testng.Assert.assertEquals;


public class BasePage extends ReusableMethods {


    /**
     * @param env : choose environment
     * @param url : choose url for environment (example: NoAdditionalQueues) or full url (example: /empty)
     */
    public void navigateToUrl(String env, String url) {
        String baseUrl;
        if (url == null || url.isEmpty()) {
            url = ConfigReader.getProperty("NoAdditionalQueues");
        }
        switch (env.toLowerCase()) {
            case "prod" -> baseUrl = "https://useinsider.com/";
            case "preprod" -> baseUrl = "https://preprod.useinsider.com";
            case "cloud" -> baseUrl = "https://cloud.useinsider.com";
            case "test" -> baseUrl = "https://test.useinsider.com";
            default -> {
                Log.warning("Invalid environment provided: " + env);
                return;
            }
        }
        String fullUrl = baseUrl + url;
        Driver.getDriver().get(fullUrl);
        Driver.getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(40));
        Log.pass("Application launched! URL: " + fullUrl);
        waitMs(3000);
        acceptCookies();
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


}
