package com.insider.pages.homepage;

import com.insider.base.BasePage;
import com.insider.base.Driver;
import com.insider.utilities.Log;
import com.insider.utilities.ReusableMethods;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.NoSuchElementException;

public class InsiderHomePage extends BasePage {

    @FindBy(css = "#navbarNavDropdown li")
    List<WebElement> navBarItems;

    @FindBy(css = "#navbarNavDropdown .dropdown-sub")
    List<WebElement> navBarSubItems;

    @FindBy(css = "nav[id=navigation]")
    WebElement navBarHead;

    @FindBy(css = "*[id=desktop_hero_24]")
    WebElement desktopHero;

    @FindBy(css = "*[id=home_logo_reel]")
    WebElement homeLogoReel;

    @FindBy(css = "*[id='case-studies-home-dots']")
    WebElement caseStudiesHomeDots;

    @FindBy(css = "*[id='one_platform_infinite_cx']")
    WebElement onePlatformInfiniteCx;

    @FindBy(css = "*[id='journey-container']")
    WebElement journeyContainer;

    @FindBy(css = "*[id='sirius_ai']")
    WebElement siriusAi;

    @FindBy(css = "*[id='tabbed-content-home']")
    WebElement tabbedContentHome;

    @FindBy(css = "*[id='testimonials']")
    WebElement testimonials;

    @FindBy(css = "*[id='home-integrations']")
    WebElement homeIntegrations;

    @FindBy(css = "*[id='home-cta-banner']")
    WebElement homeCtaBanner;

    @FindBy(id = "career-find-our-calling")
    WebElement careerFindOurCalling;

    @FindBy(css = "section[id=career-why-become-one-us]")
    WebElement careerWhyBecomeOneUs;

    @FindBy(css = "section[id=career-our-location]")
    WebElement careerOurLocation;

    @FindBy(css = "section[id='find-job-widget']")
    WebElement findJobWidget;

    @FindBy(xpath = "//h2[normalize-space()='Life at Insider']/ancestor::section")
    WebElement lifeAtInsider;

    @FindBy(id = "footer")
    WebElement footer;
    /*********/
    @FindBy(xpath = "//a[normalize-space()='See all QA jobs']")
    WebElement seeAllQAJobsButton;

    @FindBy(id = "select2-filter-by-location-container")
    WebElement filterByLocationContainer;

    @FindBy(id = "select2-filter-by-department-container")
    WebElement filterByDepartmentContainer;

    @FindBy(css = ".select2-results .select2-results__option")
    List<WebElement> filterByContainerOption;

    @FindBy(css = "*[class*='position-department']")
    List<WebElement> jobDepartmentList;

    @FindBy(css = "*[class*='position-location']")
    List<WebElement> jobLocationList;

    @FindBy(id = "jobs-list")
    WebElement jobsListContainer;

    @FindBy(css = "#jobs-list p")
    WebElement noJobsFoundMessage;

    @FindBy(css = "#jobs-list a")
    List<WebElement> viewRoleButton;



    /*===============================================================================================================================*/

    public InsiderHomePage() {
        PageFactory.initElements(Driver.getDriver(), this);
    }

    /*===============================================================================================================================*/
    // Constants
    public static final String LOGIN_URL_PARAM = "giris";
    public static final String REGISTER_URL_PARAM = "uyelik";
    public static final String LOGIN_PAGE_NOT_OPENED = "The login page is not opened.";
    /* ==================================================================================================================== */

    @Step("Select navigation bar tab: {navBarTab}")
    public InsiderHomePage selectNavBarTabItem(String navBarTab) {
        clickItemByText(navBarItems, navBarTab, "Click on nav bar: ");
        return this;
    }

    @Step("Select navigation bar sub-item: {navBarSubItem}")
    public InsiderHomePage selectNavBarSubItem(String navBarSubItem) {
        clickItemByText(navBarSubItems, navBarSubItem, "Click on nav bar, sub item: ");
        return this;
    }

/*    private void clickItemByText(List<WebElement> elements, String text, String logMessage) {
        for (WebElement element : elements) {
            if (getTextOfElement(element).equals(text)) {
                clickElement(element, logMessage + text);
                return;
            }
        }
        Log.fail("Element with text '" + text + "' not found.");
    }*/

    private void clickItemByText(List<WebElement> elements, String text, String logMessage) {
        for (WebElement element : elements) {
            String elementText = getTextOfElement(element);
            if (elementText != null && elementText.trim().equals(text)) {
                clickElement(element, logMessage + text);
                return;
            }
        }
        throw new NoSuchElementException("Navigation item not found: '" + text + "'. Available items: " +
                elements.stream()
                        .map(this::getTextOfElement)
                        .filter(t -> t != null && !t.isBlank())
                        .toList());
    }

    @Step("Verify redirected URL contains: {url}")
    public InsiderHomePage redirectUrlControl(String url) {
        waitForUrlContains(url, 3);
        Log.pass("Redirected URL: " + url);
        return this;
    }

    @Step("Verify all required sections are displayed on the Home Page")
    public InsiderHomePage verifyHomePageSections() {

        verifyElementDisplayed(navBarHead, "Navigation Bar Head");
        verifyElementDisplayed(desktopHero, "Desktop Hero Section");
        verifyElementDisplayed(homeLogoReel, "Home Logo Reel Section");
        verifyElementDisplayed(caseStudiesHomeDots, "Case Studies Home Dots Section");
        verifyElementDisplayed(onePlatformInfiniteCx, "One Platform Infinite CX Section");
        verifyElementDisplayed(journeyContainer, "Journey Container Section");
        verifyElementDisplayed(siriusAi, "Sirius AI Section");
        verifyElementDisplayed(tabbedContentHome, "Tabbed Content Home Section");
        verifyElementDisplayed(testimonials, "Testimonials Section");
        verifyElementDisplayed(homeIntegrations, "Home Integrations Section");
        verifyElementDisplayed(homeCtaBanner, "Home CTA Banner Section");
        verifyElementDisplayed(footer, "Footer Section");

        return this;
    }

    @Step("Verify all required sections are displayed on the page")
    public InsiderHomePage verifyCareerPageSections() {

        verifyElementDisplayed(careerFindOurCalling, "Career - Find Our Calling section");
        verifyElementDisplayed(careerWhyBecomeOneUs, "Career - Why Become One of Us section");
        verifyElementDisplayed(careerOurLocation, "Career - Our Location section");
        verifyElementDisplayed(findJobWidget, "Find Job Widget section");
        verifyElementDisplayed(lifeAtInsider, "Life at Insider section");
        verifyElementDisplayed(footer, "Footer section");
        return this;
    }

    private void verifyElementDisplayed(WebElement element, String elementName) {
        try {
            scrollToElementBlockCenter(element);
            waitMs(200);
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

    public InsiderHomePage goToAllJobs() {
        clickElement(seeAllQAJobsButton, "All Jobs button clicked.");
        return this;
    }

    public InsiderHomePage selectLocation(String location) {
        return selectFilterOption(filterByLocationContainer, filterByContainerOption, location, "Location");
    }

    public InsiderHomePage selectDepartment(String department) {
        return selectFilterOption(filterByDepartmentContainer, filterByContainerOption, department, "Department");
    }

    @Step("Select filter option '{value}' from '{filterName}' dropdown")
    public InsiderHomePage selectFilterOption(WebElement filterDropdown, List<WebElement> options, String value, String filterName) {
        waitMs(3000);
        clickElement(filterDropdown, "Click on " + filterName + " filter dropdown");
        for (WebElement option : options) {
            scrollToElementBlockCenter(option);
            waitMs(100);
            Log.pass("Select filter option: " + getTextOfElement(option).trim());
            if (getTextOfElement(option).trim().equals(value)) {
                clickElement(option, "Select " + filterName + ": " + value);
                return this;
            }
        }

        Log.fail(filterName + " '" + value + "' not found in the dropdown list!");
        return this;
    }

    @Step("Verify that the jobs list is displayed")
    public InsiderHomePage verifyJobsListAppears() {

        try {
            scrollToElementBlockCenter(jobsListContainer);
            waitMs(300);
            if (jobsListContainer.isDisplayed()) {
                Log.pass("Jobs list is displayed.");
            } else {
                Log.fail("Jobs list is NOT displayed!");
            }
        } catch (Exception e) {
            Log.fail("Jobs list container not found!");
        }

        return this;
    }

    @Step("Verify that all job cards match location '{location}' and department '{department}'")
    public InsiderHomePage verifyJobCardsMatchFilters(String location, String department) {

        if (jobDepartmentList.isEmpty() || jobLocationList.isEmpty()) {
            Log.fail("No job cards found to verify!");
            return this;
        }

        // Check_department
        for (WebElement dep : jobDepartmentList) {
            String departmentText = getTextOfElement(dep).trim();
            if (departmentText.equals(department)) {
                Log.pass("Job department matches: " + departmentText);
            } else {
                Log.fail("Job department mismatch! Expected: " + department + ", Found: " + departmentText);
            }
        }

        // Check_location
        for (WebElement loc : jobLocationList) {
            String locationText = getTextOfElement(loc).trim();
            if (locationText.equals(location)) {
                Log.pass("Job location matches: " + locationText);
            } else {
                Log.fail("Job location mismatch! Expected: " + location + ", Found: " + locationText);
            }
        }

        return this;
    }

    @Step("Verify 'No positions available.' message visibility")
    public InsiderHomePage verifyNoPositionsAvailableMessage() {
        try {
            if (noJobsFoundMessage.isDisplayed()) {
                String msg = getTextOfElement(noJobsFoundMessage).trim();
                if (msg.equals("No positions available.")) {
                    Log.pass("'No positions available.' message is displayed correctly.");
                } else {
                    Log.fail("Message is displayed but text is incorrect! Found: " + msg);
                }
            } else {
                Log.fail("'No positions available.' element is NOT displayed!");
            }
        } catch (Exception e) {
            Log.fail("'No positions available.' message element NOT FOUND!");
        }
        return this;
    }

    @Step("Click all 'View Role' buttons and open each job in a new tab")
    public InsiderHomePage clickViewRoleButtonsInLoop() {

        for (int i = 0; i < viewRoleButton.size(); i++) {

            var btn = viewRoleButton.get(i);

            hoverElement(btn, 1);
            clickElement(btn, "Click on view role button: index " + i);
            waitMs(500);

            switchToNewWindow();

            waitForUrlContains("jobs.lever.co/useinsider", 3);

            closeAllTabsExceptMain();
        }

        return this;
    }


}
