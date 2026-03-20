package com.wikipedia.tests.stepdefinitions;

import com.wikipedia.tests.pages.*;
import com.wikipedia.tests.support.TestContext;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.*;

public class StepDefinitions {

    private final TestContext context;
    private final WebDriver   driver;

    // Page objects — instantiated once per scenario via TestContext
    private final LoginPage            loginPage;
    private final DashboardPage        dashboardPage;
    private final VehicleInspectionPage vehiclePage;
    private final IndustrialSafetyPage  industrialPage;
    private final EnvironmentalPage     environmentalPage;
    private final CertificationPage     certificationPage;
    private final AuditsPage            auditsPage;
    private final AdminPage             adminPage;

    public StepDefinitions(TestContext context) {
        this.context           = context;
        this.driver            = context.getDriver();
        this.loginPage         = new LoginPage(driver);
        this.dashboardPage     = new DashboardPage(driver);
        this.vehiclePage       = new VehicleInspectionPage(driver);
        this.industrialPage    = new IndustrialSafetyPage(driver);
        this.environmentalPage = new EnvironmentalPage(driver);
        this.certificationPage = new CertificationPage(driver);
        this.auditsPage        = new AuditsPage(driver);
        this.adminPage         = new AdminPage(driver);
    }

    // ─────────────────────────────────────────────────────────────
    // Login steps  (features 01 + 02)
    // ─────────────────────────────────────────────────────────────

    @Given("the CoreSuite login page is open")
    public void theCoresuiteLoginPageIsOpen() {
        loginPage.open(context.baseUrl);
        assertTrue(loginPage.isLoginFormVisible(),
            "Login form should be visible on the login page");
    }

    @When("the inspector enters username {string} and password {string}")
    public void theInspectorEntersCredentials(String username, String password) {
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
    }

    @When("clicks the Sign In button")
    public void clicksTheSignInButton() {
        loginPage.clickSignIn();
        // Small wait for JS redirect
        try { Thread.sleep(600); } catch (InterruptedException ignored) {}
    }

    @Then("the dashboard page is displayed")
    public void theDashboardPageIsDisplayed() {
        String title = driver.getTitle();
        assertTrue(title.contains("Dashboard"),
            "Expected Dashboard page title, got: " + title);
    }

    @Then("the main navigation is visible")
    public void theMainNavigationIsVisible() {
        assertTrue(dashboardPage.isNavigationVisible(),
            "Main navigation should be visible after login");
    }

    @Then("an error message is displayed on the login page")
    public void anErrorMessageIsDisplayedOnTheLoginPage() {
        assertTrue(loginPage.isErrorDisplayed(),
            "Error message should be visible after invalid login");
    }

    @Then("the login form is still visible")
    public void theLoginFormIsStillVisible() {
        assertTrue(loginPage.isLoginFormVisible(),
            "Login form should still be present after failed attempt");
    }

    // ─────────────────────────────────────────────────────────────
    // Shared background step  (features 03-10)
    // ─────────────────────────────────────────────────────────────

    @Given("the inspector is logged in to CoreSuite")
    public void theInspectorIsLoggedIn() {
        loginPage.open(context.baseUrl);
        loginPage.enterUsername("inspector");
        loginPage.enterPassword("suite2024");
        loginPage.clickSignIn();
        try { Thread.sleep(600); } catch (InterruptedException ignored) {}
        assertTrue(driver.getTitle().contains("Dashboard"),
            "Background login step failed — dashboard not reached");
    }

    // ─────────────────────────────────────────────────────────────
    // Dashboard steps  (feature 03)
    // ─────────────────────────────────────────────────────────────

    @When("the dashboard page loads")
    public void theDashboardPageLoads() {
        // Already on dashboard after background login — nothing to do
        assertTrue(driver.getTitle().contains("Dashboard"));
    }

    @Then("the {string} module card is visible")
    public void theModuleCardIsVisible(String moduleName) {
        assertTrue(dashboardPage.isModuleCardVisible(moduleName),
            "Module card not visible: " + moduleName);
    }

    @When("the inspector clicks the {string} module card")
    public void theInspectorClicksModuleCard(String moduleName) {
        dashboardPage.clickModuleCard(moduleName);
        try { Thread.sleep(400); } catch (InterruptedException ignored) {}
    }

    @Then("the vehicle inspection module is open")
    public void theVehicleInspectionModuleIsOpen() {
        assertTrue(driver.getTitle().contains("Vehicle"),
            "Expected Vehicle Inspection page title, got: " + driver.getTitle());
    }

    // ─────────────────────────────────────────────────────────────
    // Vehicle Inspection steps  (features 04 + 05)
    // ─────────────────────────────────────────────────────────────

    @Given("the inspector navigates to the Vehicle Inspection module")
    public void navigatesToVehicleInspection() {
        vehiclePage.open(context.baseUrl);
    }

    @Then("the total inspections KPI is visible")
    public void totalInspectionsKpiVisible() {
        assertTrue(vehiclePage.isKpiVisible("total"));
    }

    @Then("the passed inspections KPI is visible")
    public void passedInspectionsKpiVisible() {
        assertTrue(vehiclePage.isKpiVisible("passed"));
    }

    @Then("the failed inspections KPI is visible")
    public void failedInspectionsKpiVisible() {
        assertTrue(vehiclePage.isKpiVisible("failed"));
    }

    @Then("the pending review KPI is visible")
    public void pendingReviewKpiVisible() {
        assertTrue(vehiclePage.isKpiVisible("pending"));
    }

    @Then("the inspection records table contains at least {int} row")
    public void inspectionTableHasRows(int min) {
        assertTrue(vehiclePage.getTableRowCount() >= min,
            "Expected at least " + min + " row(s) in the inspection table");
    }

    @Then("each row displays a plate number")
    public void eachRowDisplaysPlateNumber() {
        assertTrue(vehiclePage.rowsContainPlates(),
            "Inspection rows should contain plate numbers");
    }

    @Then("each row displays an inspection result badge")
    public void eachRowDisplaysResultBadge() {
        assertTrue(vehiclePage.rowsContainBadges(),
            "Inspection rows should contain result badges");
    }

    @When("the inspector searches for {string}")
    public void theInspectorSearchesFor(String query) {
        vehiclePage.searchFor(query);
    }

    @Then("the inspection table shows records matching {string}")
    public void inspectionTableShowsMatching(String text) {
        assertTrue(vehiclePage.tableContainsText(text),
            "No visible row matches search term: " + text);
    }

    @Then("the new inspection button is visible")
    public void newInspectionButtonIsVisible() {
        assertTrue(vehiclePage.isNewInspectionButtonVisible());
    }

    @Then("the new inspection button is highlighted by the test")
    public void newInspectionButtonIsHighlighted() {
        vehiclePage.highlightNewInspectionButton();
    }

    // ─────────────────────────────────────────────────────────────
    // Industrial Safety steps  (feature 06)
    // ─────────────────────────────────────────────────────────────

    @Given("the inspector navigates to the Industrial Safety module")
    public void navigatesToIndustrialSafety() {
        industrialPage.open(context.baseUrl);
    }

    @Then("at least {int} risk assessment cards are visible")
    public void atLeastNRiskCardsVisible(int min) {
        assertTrue(industrialPage.getRiskCardCount() >= min,
            "Expected at least " + min + " risk card(s)");
    }

    @Then("at least one card shows a {string} label")
    public void atLeastOneCardShowsLabel(String label) {
        assertTrue(industrialPage.hasRiskLevelLabel(label),
            "No risk card with label: " + label);
    }

    @Then("the safety assessments table is visible")
    public void safetyAssessmentsTableVisible() {
        assertTrue(industrialPage.isSafetyTableVisible());
    }

    @Then("the table contains at least {int} row")
    public void tableContainsAtLeastRow(int min) {
        int count = industrialPage.getSafetyTableRowCount();
        assertTrue(count >= min,
            "Expected at least " + min + " row(s), found " + count);
    }

    // ─────────────────────────────────────────────────────────────
    // Environmental steps  (feature 07)
    // ─────────────────────────────────────────────────────────────

    @Given("the inspector navigates to the Environmental Testing module")
    public void navigatesToEnvironmental() {
        environmentalPage.open(context.baseUrl);
    }

    @Then("the sample results table is visible")
    public void sampleResultsTableVisible() {
        assertTrue(environmentalPage.isTableVisible());
    }

    @Then("at least one result shows status {string}")
    public void atLeastOneResultShowsStatus(String status) {
        assertTrue(environmentalPage.hasStatusBadge(status),
            "No result with status: " + status);
    }

    @Then("the type filter dropdown is visible")
    public void typeFilterDropdownVisible() {
        assertTrue(environmentalPage.isTypeFilterVisible());
    }

    @Then("the new sample button is visible and highlighted")
    public void newSampleButtonVisibleAndHighlighted() {
        assertTrue(environmentalPage.isNewSampleButtonVisible());
    }

    // ─────────────────────────────────────────────────────────────
    // Certification steps  (feature 08)
    // ─────────────────────────────────────────────────────────────

    @Given("the inspector navigates to the Certification module")
    public void navigatesToCertification() {
        certificationPage.open(context.baseUrl);
    }

    @Then("the expiry alert banner is visible")
    public void expiryAlertBannerVisible() {
        assertTrue(certificationPage.isExpiryAlertVisible());
    }

    @Then("the certification table contains at least {int} rows")
    public void certificationTableContainsRows(int min) {
        assertTrue(certificationPage.getTableRowCount() >= min,
            "Expected at least " + min + " certification row(s)");
    }

    @Then("at least one certification shows status {string}")
    public void atLeastOneCertificationShowsStatus(String status) {
        assertTrue(certificationPage.hasStatusBadge(status),
            "No certification with status: " + status);
    }

    // ─────────────────────────────────────────────────────────────
    // Audit Report steps  (feature 09)
    // ─────────────────────────────────────────────────────────────

    @Given("the inspector navigates to the Audit Reports module")
    public void navigatesToAuditReports() {
        auditsPage.open(context.baseUrl);
    }

    @Then("the audit reports table is visible")
    public void auditReportsTableVisible() {
        assertTrue(auditsPage.isTableVisible());
    }

    @Then("at least one report shows status {string}")
    public void atLeastOneReportShowsStatus(String status) {
        assertTrue(auditsPage.hasStatusBadge(status),
            "No audit report with status: " + status);
    }

    @Then("the new audit report button is visible and highlighted")
    public void newAuditReportButtonVisibleAndHighlighted() {
        assertTrue(auditsPage.isNewAuditButtonVisible());
    }

    // ─────────────────────────────────────────────────────────────
    // Administration steps  (feature 10)
    // ─────────────────────────────────────────────────────────────

    @Given("the inspector navigates to the Administration module")
    public void navigatesToAdministration() {
        adminPage.open(context.baseUrl);
    }

    @Then("the user management table is visible")
    public void userManagementTableVisible() {
        assertTrue(adminPage.isUserTableVisible());
    }

    @Then("the table contains at least {int} user rows")
    public void tableContainsAtLeastUserRows(int min) {
        assertTrue(adminPage.getUserRowCount() >= min,
            "Expected at least " + min + " user row(s)");
    }

    @Then("the user {string} is listed in the table")
    public void theUserIsListedInTheTable(String username) {
        assertTrue(adminPage.isUserListed(username),
            "User not found in table: " + username);
    }

    @Then("the session timeout field is visible")
    public void sessionTimeoutFieldVisible() {
        assertTrue(adminPage.isSessionTimeoutVisible());
    }

    @Then("the default language selector is visible")
    public void defaultLanguageSelectorVisible() {
        assertTrue(adminPage.isLanguageSelectorVisible());
    }

    @Then("the report format selector is visible")
    public void reportFormatSelectorVisible() {
        assertTrue(adminPage.isReportFormatSelectorVisible());
    }
}