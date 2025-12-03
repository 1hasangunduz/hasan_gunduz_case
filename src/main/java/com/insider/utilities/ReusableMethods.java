package com.insider.utilities;

import com.insider.base.Driver;
import com.insider.configs.Configs;
import io.qameta.allure.Step;
import io.qameta.allure.testng.AllureTestNg;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Listeners;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;


@Listeners(AllureTestNg.class)
public class ReusableMethods implements WaitConditions {

    public final Configs config = Configs.getConfigs();
    public static final int DEFAULT_WAIT_TIME = 3;

    // ----------------------------
    // ACTIONS
    // ----------------------------

    @Step("Hover over element for {second} seconds")
    public void hoverElement(WebElement webElement, int second) {
        try {
            Actions action = new Actions(Driver.getDriver());
            action.moveToElement(webElement)
                    .pause(Duration.ofSeconds(second))
                    .perform();
            Log.pass("Hovered over element.");
        } catch (Exception e) {
            Log.fail("Error while hovering over element.", e);
        }
    }

    @Step("Click on element: {message}")
    public void clickElement(WebElement webElement, String message) {
        try {
            waitVisibleByLocator(webElement);
            scrollToElementBlockCenter(webElement);
            waitClickAbleByOfElement(webElement).click();
            Log.pass(message);
        } catch (Exception e) {
            Log.fail("Failed to click on element: " + message, e);
        }
    }

    @Step("Click on element using JavaScript: {message}")
    public void clickWithJS(WebElement element, String message) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
            js.executeScript("arguments[0].scrollIntoView(true);", element);
            js.executeScript("arguments[0].click();", element);
            Log.pass(message);
        } catch (Exception e) {
            Log.fail("Failed to click with JS: " + message, e);
        }
    }

    @Step("Scroll element into block center")
    public void scrollToElementBlockCenter(WebElement element) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
        } catch (Exception e) {
           Log.fail("Error while scrolling to the element.", e);
        }
    }

    // ----------------------------
    // TEXT & DISPLAY
    // ----------------------------

    @Step("Get text of element")
    public String getTextOfElement(WebElement elem) {
        String text = null;
        try {
            text = elem.getText();
            Log.pass("Text retrieved: " + text);
        } catch (Exception exception) {
            Log.fail("Failed to get text from element.", exception);
        }
        return text;
    }

    @Step("Get visible element text")
    public String getTextElement(WebElement element) {
        return waitVisibleByLocator(element).getText();
    }

    @Step("Check if element is displayed")
    public boolean isDisplayElement(WebElement webElement) {
        try {
            return waitVisibleByLocator(webElement).isDisplayed();
        } catch (Exception e) {
            Log.fail("Element is not displayed.", e);
            return false;
        }
    }

    // ----------------------------
    // WINDOW HANDLING
    // ----------------------------

    @Step("Switch to newly opened browser window")
    public void switchToNewWindow() {
        String origin = Driver.getDriver().getWindowHandle();
        for (String handle : Driver.getDriver().getWindowHandles()) {
            if (!handle.equals(origin)) {
                Driver.getDriver().switchTo().window(handle);
                Log.pass("Switched to new window.");
            }
        }
    }

    @Step("Close all browser tabs except main tab and return to main tab")
    public static void closeAllTabsExceptMain() {
        List<String> allTabs = new ArrayList<>(Driver.getDriver().getWindowHandles());
        String mainTab = allTabs.get(0);

        for (int i = 1; i < allTabs.size(); i++) {
            Driver.getDriver().switchTo().window(allTabs.get(i));
            Driver.getDriver().close();
        }

        Driver.getDriver().switchTo().window(mainTab);
        Log.pass("Closed all tabs except main tab.");
    }
}
