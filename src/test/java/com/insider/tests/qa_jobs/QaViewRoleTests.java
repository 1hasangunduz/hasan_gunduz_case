package com.insider.tests.qa_jobs;

import com.insider.base.BaseTest;
import com.insider.pages.homepage.InsiderHomePage;
import com.insider.utilities.StepInit;
import io.qameta.allure.*;
import org.testng.annotations.Test;

@Epic("QA Jobs View Role Actions")
@Feature("Lever Application Redirection")
@Story("Verify View Role button opens Lever application in new tab")
public class QaViewRoleTests extends BaseTest {

    @Description("Verify that after filtering QA jobs by Location 'Istanbul, Turkiye' and Department 'Quality Assurance', clicking each 'View Role' button opens the corresponding Lever Application Form page in a new tab.")
    @Test(testName = "Verify View Role Redirection for All QA Job Results")
    public void verifyQaJobFilteringViewRoleActions_Case_4() {

        var departmentUri = "department=qualityassurance";
        var location = "Istanbul, Turkiye";
        var department = "Quality Assurance";

        navigateToUrl("prod", "careers/quality-assurance/");

        StepInit.get(InsiderHomePage.class)
                .goToAllJobs()
                .redirectUrlControl(departmentUri)
                .selectLocation(location)
                .selectDepartment(department)
                .clickViewRoleButtonsInLoop();
    }
}
