package com.insider.tests.careers;

import com.insider.base.BaseTest;
import com.insider.pages.homepage.InsiderHomePage;
import com.insider.utilities.StepInit;
import io.qameta.allure.*;
import org.testng.annotations.Test;

@Epic("Careers Page Navigation & UI Tests")
@Feature("Navigation from Home to Careers")
@Story("Verify navigation to Careers page and sections visibility")
public class CareersPageTests extends BaseTest {

    @Description("Verify that the user can navigate from the 'Company' tab to the 'Careers' page and that all required Career Page sections are displayed correctly.")
    @Test(testName = "Verify Navigation to Careers Page and Validate Page Sections")
    public void verifyNavigationTabAndSubItem_Case_2() {

        var navBarTab = "Company";
        var navBarSubTab = "Careers";

        navigateToUrl();

        StepInit.get(InsiderHomePage.class)
                .selectNavBarTabItem(navBarTab)
                .selectNavBarSubItem(navBarSubTab)
                .redirectUrlControl(navBarSubTab.toLowerCase())
                .verifyCareerPageSections();
    }
}
