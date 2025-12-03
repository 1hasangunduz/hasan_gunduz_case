package com.insider.utilities;

import com.insider.base.Driver;
import io.qameta.allure.Step;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public interface WaitConditions {

    int DEFAULT_WAIT = 7;

    /********************* WAIT FOR ELEMENT CLICKABLE *********************/

    @Step("Wait until element is clickable")
    default WebElement waitClickAbleByOfElement(WebElement element) {
        return waitClickAbleByOfElement(element, DEFAULT_WAIT);
    }

    @Step("Wait up to {time} seconds for element to become clickable")
    default WebElement waitClickAbleByOfElement(WebElement element, int time) {
        try {
            WebElement clickableElement =
                    new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(time))
                            .until(ExpectedConditions.elementToBeClickable(element));
            return clickableElement;

        } catch (Exception e) {
            Log.fail("Element failed to become clickable within " + time + " seconds.", e);
            return element;
        }
    }

    /********************* WAIT FOR ELEMENT VISIBLE *********************/

    @Step("Wait until element is visible")
    default WebElement waitVisibleByLocator(WebElement element) {
        return waitVisibleByLocator(element, DEFAULT_WAIT);
    }

    @Step("Wait up to {time} seconds for element to become visible")
    default WebElement waitVisibleByLocator(WebElement element, int time) {
        try {
            WebElement visibleElement =
                    new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(time))
                            .until(ExpectedConditions.visibilityOf(element));
            return visibleElement;

        } catch (Exception e) {
            Log.fail("Element failed to become visible within " + time + " seconds.", e);
            return element;
        }
    }

    /********************* WAIT FOR PAGE LOAD *********************/

    @Step("Wait for page to fully load")
    default void waitForPageToLoad() {
        waitForPageToLoad(DEFAULT_WAIT);
    }

    @Step("Wait up to {timeOutInSeconds} seconds for page to fully load")
    default void waitForPageToLoad(long timeOutInSeconds) {
        ExpectedCondition<Boolean> expectation =
                driver -> ((JavascriptExecutor) driver)
                        .executeScript("return document.readyState")
                        .equals("complete");

        try {
            Log.pass("Waiting for page to load...");
            var wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(timeOutInSeconds));
            wait.until(expectation);
            Log.pass("Page successfully loaded.");
        } catch (TimeoutException e) {
            Log.warning("Page did not finish loading within " + timeOutInSeconds + " seconds.");
        }
    }

    /********************* WAITING UTILITIES *********************/

    @Step("Wait for {millisecond} milliseconds")
    default void waitMs(int millisecond) {
        try {
            Thread.sleep(millisecond);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Thread was interrupted during sleep", e);
        }
    }

    @Step("Wait until URL contains keyword: {keyword}")
    default void waitForUrlContains(String keyword, int timeoutSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(timeoutSeconds));
            wait.until(ExpectedConditions.urlContains(keyword));
            Log.pass("URL contains the expected keyword: " + keyword);
        } catch (Exception e) {
            Log.fail("URL did not contain the expected keyword within " + timeoutSeconds + " seconds.", e);
        }
    }
}
