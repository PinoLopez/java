package com.app.qa.coresuite.stepdefinitions;

import com.app.qa.coresuite.support.TestContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static org.junit.jupiter.api.Assertions.*;

public class StepDefinitions {

    private final TestContext context;
    
    public StepDefinitions(TestContext context) {
        this.context = context;
    }

    // Login steps
    @Given("the CoreSuite login page is open")
    public void theCoreSuiteLoginPageIsOpen() {
        context.getLoginPage().open(context.getBaseUrl());
    }

    @When("the inspector enters username {string} and password {string}")
    public void theInspectorEntersUsernameAndPassword(String username, String password) {
        context.getLoginPage().enterUsername(username);
        context.getLoginPage().enterPassword(password);
    }

    @When("clicks the Sign In button")
    public void clicksTheSignInButton() {
        context.getLoginPage().clickSignIn();
    }

    @Then("the dashboard page is displayed")
    public void theDashboardPageIsDisplayed() {
        assertTrue(context.getDashboardPage().isNavigationVisible());
    }

    @Then("the main navigation is visible")
    public void theMainNavigationIsVisible() {
        assertTrue(context.getDashboardPage().isNavigationVisible());
    }

    @Then("an error message is displayed on the login page")
    public void anErrorMessageIsDisplayedOnTheLoginPage() {
        assertTrue(context.getLoginPage().isErrorDisplayed());
    }

    @Then("the login form is still visible")
    public void theLoginFormIsStillVisible() {
        assertTrue(context.getLoginPage().isLoginFormVisible());
    }

    // Dashboard steps
    @When("the dashboard page loads")
    public void theDashboardPageLoads() {
        context.getDashboardPage().open(context.getBaseUrl());
    }

    @Then("the {string} module card is visible")
    public void theModuleCardIsVisible(String moduleName) {
        assertTrue(context.getDashboardPage().isModuleCardVisible(moduleName));
    }

    @When("the inspector clicks the {string} module card")
    public void theInspectorClicksTheModuleCard(String moduleName) {
        context.getDashboardPage().clickModuleCard(moduleName);
    }

    @Then("the vehicle inspection module is open")
    public void theVehicleInspectionModuleIsOpen() {
        assertTrue(context.getVehicleInspectionPage().isInspectionTableVisible());
    }

    @Then("the page title contains {string}")
    public void thePageTitleContains(String expectedTitle) {
        assertTrue(context.getDriver().getTitle().contains(expectedTitle));
    }

    // Generic steps used across features
    @Given("the inspector is logged in to CoreSuite")
    public void theInspectorIsLoggedInToCoreSuite() {
        context.getLoginPage().open(context.getBaseUrl());
        context.getLoginPage().enterUsername("inspector");
        context.getLoginPage().enterPassword("suite2026");
        context.getLoginPage().clickSignIn();
        assertTrue(context.getDashboardPage().isNavigationVisible());
    }

    @Given("the inspector navigates to the Administration module")
    public void theInspectorNavigatesToTheAdministrationModule() {
        context.getDashboardPage().open(context.getBaseUrl());
        context.getDashboardPage().clickModuleCard("Administration");
    }

    @Given("the inspector navigates to the Audit Reports module")
    public void theInspectorNavigatesToTheAuditReportsModule() {
        context.getDashboardPage().open(context.getBaseUrl());
        context.getDashboardPage().clickModuleCard("Audit Reports");
    }

    @Given("the inspector navigates to the Certification module")
    public void theInspectorNavigatesToTheCertificationModule() {
        context.getDashboardPage().open(context.getBaseUrl());
        context.getDashboardPage().clickModuleCard("Certification");
    }

    @Given("the inspector navigates to the Environmental Testing module")
    public void theInspectorNavigatesToTheEnvironmentalTestingModule() {
        context.getDashboardPage().open(context.getBaseUrl());
        context.getDashboardPage().clickModuleCard("Environmental Testing");
    }

    @Given("the inspector navigates to the Industrial Safety module")
    public void theInspectorNavigatesToTheIndustrialSafetyModule() {
        context.getDashboardPage().open(context.getBaseUrl());
        context.getDashboardPage().clickModuleCard("Industrial Safety");
    }

    @Given("the inspector navigates to the Vehicle Inspection module")
    public void theInspectorNavigatesToTheVehicleInspectionModule() {
        context.getDashboardPage().open(context.getBaseUrl());
        context.getDashboardPage().clickModuleCard("Vehicle Inspection");
    }

    // Page-specific verification steps
    @Then("the user management table is visible")
    public void theUserManagementTableIsVisible() {
        assertTrue(context.getAdminPage().isUserTableVisible());
    }

    @Then("the table contains at least {int} user rows")
    public void theTableContainsAtLeastUserRows(int minRows) {
        assertTrue(context.getAdminPage().getUserRowCount() >= minRows);
    }

    @Then("the user {string} is listed in the table")
    public void theUserIsListedInTheTable(String username) {
        assertTrue(context.getAdminPage().isUserPresent(username));
    }

    @Then("the audit reports table is visible")
    public void theAuditReportsTableIsVisible() {
        assertTrue(context.getAuditsPage().isReportsTableVisible());
    }

    @Then("at least one report shows status {string}")
    public void atLeastOneReportShowsStatus(String status) {
        assertTrue(context.getAuditsPage().hasReportWithStatus(status));
    }

    @Then("the new audit report button is visible and highlighted")
    public void theNewAuditReportButtonIsVisibleAndHighlighted() {
        assertTrue(context.getAuditsPage().isNewReportButtonVisible());
    }

    @Then("the certification table contains at least {int} rows")
    public void theCertificationTableContainsAtLeastRows(int minRows) {
        assertTrue(context.getCertificationPage().getCertificationRowCount() >= minRows);
    }

    @Then("at least one certification shows status {string}")
    public void atLeastOneCertificationShowsStatus(String status) {
        assertTrue(context.getCertificationPage().hasCertificationWithStatus(status));
    }

    @Then("the expiry alert banner is visible")
    public void theExpiryAlertBannerIsVisible() {
        assertTrue(context.getCertificationPage().isExpiryAlertVisible());
    }

    @Then("the sample results table is visible")
    public void theSampleResultsTableIsVisible() {
        assertTrue(context.getEnvironmentalPage().isResultsTableVisible());
    }

    @Then("at least one result shows status {string}")
    public void atLeastOneResultShowsStatus(String status) {
        assertTrue(context.getEnvironmentalPage().hasResultWithStatus(status));
    }

    @Then("the type filter dropdown is visible")
    public void theTypeFilterDropdownIsVisible() {
        assertTrue(context.getEnvironmentalPage().isTypeFilterVisible());
    }

    @Then("the new sample button is visible and highlighted")
    public void theNewSampleButtonIsVisibleAndHighlighted() {
        assertTrue(context.getEnvironmentalPage().isNewSampleButtonVisible());
    }

    @Then("the safety assessments table is visible")
    public void theSafetyAssessmentsTableIsVisible() {
        assertTrue(context.getIndustrialSafetyPage().isAssessmentsTableVisible());
    }

    @Then("the table contains at least {int} row")
    public void theTableContainsAtLeastRow(int minRows) {
        assertTrue(context.getIndustrialSafetyPage().getAssessmentRowCount() >= minRows);
    }

    @Then("at least {int} risk assessment cards are visible")
    public void atLeastRiskAssessmentCardsAreVisible(int minCards) {
        assertTrue(context.getIndustrialSafetyPage().getRiskCardCount() >= minCards);
    }

    @Then("at least one card shows a {string} label")
    public void atLeastOneCardShowsALabel(String label) {
        assertTrue(context.getIndustrialSafetyPage().hasRiskCardWithLabel(label));
    }

    @Then("the total inspections KPI is visible")
    public void theTotalInspectionsKpiIsVisible() {
        assertTrue(context.getVehicleInspectionPage().isTotalInspectionsKpiVisible());
    }

    @Then("the passed inspections KPI is visible")
    public void thePassedInspectionsKpiIsVisible() {
        assertTrue(context.getVehicleInspectionPage().isPassedInspectionsKpiVisible());
    }

    @Then("the failed inspections KPI is visible")
    public void theFailedInspectionsKpiIsVisible() {
        assertTrue(context.getVehicleInspectionPage().isFailedInspectionsKpiVisible());
    }

    @Then("the pending review KPI is visible")
    public void thePendingReviewKpiIsVisible() {
        assertTrue(context.getVehicleInspectionPage().isPendingReviewKpiVisible());
    }

    @Then("the new inspection button is visible")
    public void theNewInspectionButtonIsVisible() {
        assertTrue(context.getVehicleInspectionPage().isNewInspectionButtonVisible());
    }

    @Then("the new inspection button is highlighted by the test")
    public void theNewInspectionButtonIsHighlightedByTheTest() {
        assertTrue(context.getVehicleInspectionPage().isNewInspectionButtonHighlighted());
    }

    @When("the inspector searches for {string}")
    public void theInspectorSearchesFor(String searchTerm) {
        context.getVehicleInspectionPage().search(searchTerm);
    }

    @Then("the inspection table shows records matching {string}")
    public void theInspectionTableShowsRecordsMatching(String searchTerm) {
        assertTrue(context.getVehicleInspectionPage().areSearchResultsMatching(searchTerm));
    }

    @Then("the inspection records table contains at least {int} row")
    public void theInspectionRecordsTableContainsAtLeastRow(int minRows) {
        assertTrue(context.getVehicleInspectionPage().getInspectionRowCount() >= minRows);
    }

    @Then("each row displays a plate number")
    public void eachRowDisplaysAPlateNumber() {
        assertTrue(context.getVehicleInspectionPage().allRowsHavePlateNumber());
    }

    @Then("each row displays an inspection result badge")
    public void eachRowDisplaysAnInspectionResultBadge() {
        assertTrue(context.getVehicleInspectionPage().allRowsHaveResultBadge());
    }

    @Then("the session timeout field is visible")
    public void theSessionTimeoutFieldIsVisible() {
        assertTrue(context.getAdminPage().isSessionTimeoutFieldVisible());
    }

    @Then("the default language selector is visible")
    public void theDefaultLanguageSelectorIsVisible() {
        assertTrue(context.getAdminPage().isLanguageSelectorVisible());
    }

    @Then("the report format selector is visible")
    public void theReportFormatSelectorIsVisible() {
        assertTrue(context.getAdminPage().isReportFormatSelectorVisible());
    }
}
