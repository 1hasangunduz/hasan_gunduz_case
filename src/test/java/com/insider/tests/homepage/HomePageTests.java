package com.insider.tests.homepage;

import com.insider.base.BaseTest;
import com.insider.pages.homepage.InsiderHomePage;
import com.insider.utilities.StepInit;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;


@Epic("Verify Navigation Tab Item Tests")
@Feature("Navigation Tab Item Functionality")
@Story("Checking Navigation And Sub Items")
public class HomePageTests extends BaseTest {

    @Description("Verify that all required sections on the Insider Home Page are displayed correctly.")
    @Test(testName = "Verify Home Page Sections are Visible")
    public void verifyNavigationHomePage_Case_1() {
        navigateToUrl();

        StepInit.get(InsiderHomePage.class)
                .verifyHomePageSections();
    }


/*

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
    @Description("Verify that the user can navigate to the QA jobs page, apply filters by Location 'Istanbul, Turkiye' and Department 'Quality Assurance', and confirm that the job listings match the selected criteria.")
    @Test(testName = "Verify QA Jobs Filtering by Location and Department")
    public void verifyQaJobFiltering_Case_3() {

        var departmentUri = "department=qualityassurance";
        var location = "Istanbul, Turkiye";
        var department = "Quality Assurance";

        navigateToUrl("prod","careers/quality-assurance/");

       StepInit.get(InsiderHomePage.class)
                .goToAllJobs()
                .redirectUrlControl(departmentUri)
                .selectLocation(location)
                .selectDepartment(department)
                .verifyJobsListAppears()
                .verifyJobCardsMatchFilters(location, department);
    }

    @Description("Verify that when filtering QA jobs by an unavailable location (Chile, Chile) and Department 'Quality Assurance', the 'No positions available.' message is displayed correctly.")
    @Test(testName = "Verify QA Job Filtering Displays 'No Positions Available' Message")
    public void verifyQaJobFilteringNoPosition_Case_3_1() {

        var departmentUri = "department=qualityassurance";
        var location = "Chile, Chile";
        var department = "Quality Assurance";

        navigateToUrl("prod","careers/quality-assurance/");

       StepInit.get(InsiderHomePage.class)
                .goToAllJobs()
                .redirectUrlControl(departmentUri)
                .selectLocation(location)
                .selectDepartment(department)
                .verifyNoPositionsAvailableMessage();
    }

    @Description("Verify that after filtering QA jobs by Location 'Istanbul, Turkiye' and Department 'Quality Assurance', clicking each 'View Role' button opens the corresponding Lever Application Form page in a new tab.")
    @Test(testName = "Verify View Role Redirection for All QA Job Results")
    public void verifyQaJobFilteringViewRoleActions_Case_4() {


        var departmentUri = "department=qualityassurance";
        var location = "Istanbul, Turkiye";
        var department = "Quality Assurance";

        navigateToUrl("prod","careers/quality-assurance/");

       StepInit.get(InsiderHomePage.class)
                .goToAllJobs()
                .redirectUrlControl(departmentUri)
                .selectLocation(location)
                .selectDepartment(department)
                .clickViewRoleButtonsInLoop();
    }
*/


}
