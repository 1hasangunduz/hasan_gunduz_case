# Insider UI Automation
This document summarizes the complete UI automation coverage implemented for the Insider website, focusing on Home Page components, navigation menu functionality, Careers → Quality Assurance job filtering, negative filtering scenarios, and View Role redirection to Lever Application Form pages.

---
#### For Parallel Run
- Use XML suite : suites/SuiteNavigationTab.xml
---
#### Allure Report on Terminal
- allure generate
- allure serve
---
## 📌 1. Test Case Summary

## ✅ **Test Case 1 — Verify Home Page Sections**
**Test Method:** `verifyNavigationHomePage_Case_1`  
**Purpose:** Ensures that Insider Home Page loads properly.

### Steps:
1. Navigate to https://useinsider.com/
2. Validate presence of all main Home Page UI blocks.

### Expected Result:
All homepage sections are displayed correctly.

---

## ✅ **Test Case 2 — Verify Company → Careers Navigation**
**Test Method:** `verifyNavigationTabAndSubItem_Case_2`  
**Purpose:** Verify navigation to Careers Page.

### Steps:
1. Open home page
2. Click **Company** tab
3. Select **Careers**
4. Verify Careers Page sections

### Expected Result:
Careers page loads and all UI blocks are visible.

---

## ✅ **Test Case 3 — QA Job Filtering (Positive Scenario)**
**Test Method:** `verifyQaJobFiltering_Case_3`  
**Purpose:** Filter QA positions by Location and Department.

### Steps:
1. Navigate to QA Careers Page
2. Click **See all QA jobs**
3. Apply:
    - Location → Istanbul, Turkiye
    - Department → Quality Assurance
4. Validate:
    - Job list appears
    - Each job card matches:
        - Department: Quality Assurance
        - Location: Istanbul, Turkey

### Expected Result:
Only QA jobs in Istanbul appear.

---

## ✅ **Test Case 3.1 — QA Job Filtering (Negative Scenario)**
**Test Method:** `verifyQaJobFilteringNoPosition_Case_3_1`  
**Purpose:** Filtering for no results.

### Steps:
1. Navigate to QA page
2. Click **See all QA jobs**
3. Apply:
    - Location → Chile, Chile
    - Department → Quality Assurance
4. Validate:
    - “No positions available.” message is shown

### Expected Result:
No job card appears and correct empty result message is displayed.

---

## ✅ **Test Case 4 — Verify View Role Redirection**
**Test Method:** `verifyQaJobFilteringViewRoleActions_Case_4`  
**Purpose:** Validate that each job’s **View Role** leads to Lever Application Form.

### Steps:
1. Filter jobs (same filters as Case 3)
2. For each job card:
    - Click “View Role”
    - Check new tab opens
    - Check URL contains: `jobs.lever.co/useinsider`
    - Close tab and continue

### Expected Result:
All View Role buttons redirect to valid Lever application pages.

---

## 📌 2. Coverage Confirmation Against Requirements

| Requirement | Covered? | Details |
|------------|----------|---------|
| Visit Home Page & verify it opens | ✅ | Case 1 |
| Navigate Company → Careers | ✅ | Case 2 |
| Verify Careers page blocks | ✅ | Case 2 |
| Filter QA jobs | ✅ | Case 3 |
| Validate job list | ✅ | Case 3 |
| Position/Department/Location match | ✅ | Case 3 & extended method |
| No-position message visible | ✅ | Case 3.1 |
| View Role redirects to Lever | ✅ | Case 4 |

All requirements are fully automated.

---

## 📌 3. Technology Stack and Architecture

- **Language:** Java 17
- **UI Testing:** Selenium WebDriver
- **Test Runner:** TestNG (methods-driver seviyesinde paralel koşum)
- **Reporting:** Allure Report
- **Configuration Management:** Owner (`Configs` + `config.properties`)
- **Architecture:** Page Object Model (POM)
- **Logging:** Log4j2 + custom `Log` utility
- **Driver Management:** `ThreadLocal<WebDriver>` + `Driver` sınıfı

---
## 📌 4. Project Scope

```text
src
 ├─ main/java/com/insider
 │   ├─ base/         
 │   │    • BasePage       → Tüm page'lerin miras aldığı ana sınıf. 
 │   │                       İçerik: navigation, cookies, verify, waits, scroll, URL control.
 │   │    • BaseTest       → TestNG @BeforeMethod/@AfterMethod + driver lifecycle.
 │   │    • Driver         → ThreadLocal driver, browser initializer, headless support, screenshot.
 │   │
 │   ├─ pages/
 │   │    • HomePage       → Home UI element locators + homepage validation methods.
 │   │    • CareersPage    → Navigation bar actions + Careers page sections.
 │   │    • JobsPage       → Filters, job list validation, View Role actions.
 │   │
 │   ├─ utilities/
 │   │    • Log            → Custom pass(), fail(), warning() + Allure step logging.
 │   │    • ReusableMethods→ scroll, click, hover, wait, tab switching, JS actions.
 │   │    • WaitConditions → Visible/clickable waits, page load waits, URL wait methods.
 │   │    • PageInit       → Reflection-based Page Object factory.
 │   │    • ConfigReader   → Property reader for config.properties.
 │   │
 │   └─ listener/
 │        • Listener       → Test lifecycle events, screenshot on failure, logging.
 │
 └─ test/java/com/insider/tests
      ├─ homepage/
      │     • HomePageTests         → Home page section validation.
      │
      ├─ careers/
      │     • CareersPageTests      → Company → Careers navigation + section validation.
      │
      └─ jobs/
            • QaJobFilterTests      → Positive + negative filtering scenarios.
            • QaViewRoleTests       → View Role redirection validation.

```
---
### Validates:
- Header Navigation
- Desktop Hero
- Logo Reel
- Case Studies
- One Platform Infinite CX
- Journey Section
- Sirius AI Section
- Tabbed Content
- Testimonials
- Integrations
- CTA Banner
- Footer

##  Careers Page Validation
- `verifyCareerPageSections()`

### Validates:
- Find Our Calling
- Why Become One of Us
- Our Locations
- Find Job Widget
- Life at Insider
- Footer

##  QA Jobs Filtering
- `selectLocation(String location)`
- `selectDepartment(String department)`
- `selectFilterOption(...)`
- `verifyJobsListAppears()`
- `verifyJobCardsMatchFilters(...)`

##  Negative Filtering Scenario
- `verifyNoPositionsAvailableMessage()`

##  View Role Button Redirection
- `clickViewRoleButtonsInLoop()`
    - Iterates over all job cards
    - Clicks **View Role**
    - Switches to new tab
    - Verifies redirect to Lever (`jobs.lever.co/useinsider/...`)
    - Closes new tab
    - Returns to original tab

---

### 📌 5. Additional Notes & Improvements

 Framework successfully supports multi-tab handling.
- Dropdown selection is fully dynamic.
- Page Object locators are stable and scalable.
- All test flows include descriptive Allure @Steps for rich reporting.
- Logging uses `Log.pass()` and `Log.fail()` consistently.
- Thread-safe driver design ensures predictable behavior during concurrent executions.
- Allure reporting includes full step logs and screenshots for maximum traceability.
- Custom utility methods reduce code duplication and increase framework consistency.
- Environment-driven configuration enables flexible test execution across multiple platforms.
- Element locators and interactions are optimized to reduce test flakiness.
- Framework is structured for easy expansion to new Insider modules or job domains.
---
### 📌 6. Conclusion

This full automation suite provides reliable coverage for Insider’s Home Page, Careers Page, and QA Hiring workflow, validating:

- UI component availability
- Navigation accuracy
- Dynamic filtering
- Multi-tab external job redirects
- Negative filtering behavior

All scenarios are POM-structured, scalable, and aligned with best QA automation practices.

---