@regression
Feature: Regression – Critical path verification
  As a QA engineer
  I want to re-run the most critical paths after every code change
  So that I confirm no existing behaviour has been broken by the change

  Scenario: Login still works after changes
    Given the CoreSuite login page is open
    When the inspector enters username "inspector" and password "suite2026"
    And clicks the Sign In button
    Then the dashboard page is displayed

  Scenario: All dashboard module cards still present after changes
    Given the inspector is logged in to CoreSuite
    When the dashboard page loads
    Then the "Vehicle Inspection" module card is visible
    And the "Industrial Safety" module card is visible
    And the "Environmental Testing" module card is visible
    And the "Certification Management" module card is visible
    And the "Audit Reports" module card is visible
    And the "Administration" module card is visible

  Scenario: Certification expiry alert still visible after changes
    Given the inspector is logged in to CoreSuite
    And the inspector navigates to the Certification module
    Then the expiry alert banner is visible

  Scenario: Audit report statuses still visible after changes
    Given the inspector is logged in to CoreSuite
    And the inspector navigates to the Audit Reports module
    Then at least one report shows status "FINAL"
