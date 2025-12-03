package com.insider.tests.qa_jobs;

import com.insider.base.BaseTest;
import com.insider.pages.homepage.InsiderHomePage;
import com.insider.utilities.StepInit;
import io.qameta.allure.*;
import org.testng.annotations.Test;

@Epic("QA Jobs Filtering Tests")
@Feature("QA Jobs Filter by Location and Department")
@Story("Verify QA jobs can be filtered and match selected criteria")
public class QaJobFilterTests extends BaseTest {

    @Description("Verify that the user can navigate to the QA jobs page, apply filters by Location 'Istanbul, Turkiye' and Department 'Quality Assurance', and confirm that the job listings match the selected criteria.")
    @Test(testName = "Verify QA Jobs Filtering by Location and Department")
    public void verifyQaJobFiltering_Case_3() {

        var departmentUri = "department=qualityassurance";
        var location = "Istanbul, Turkiye";
        var department = "Quality Assurance";

        navigateToUrl("prod", "careers/quality-assurance/");

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

        navigateToUrl("prod", "careers/quality-assurance/");

        StepInit.get(InsiderHomePage.class)
                .goToAllJobs()
                .redirectUrlControl(departmentUri)
                .selectLocation(location)
                .selectDepartment(department)
                .verifyNoPositionsAvailableMessage();
    }
}
