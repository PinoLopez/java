Feature: Audit reports archive
  As a lead auditor
  I want to review the audit report archive with statuses
  So that I can track which reports are final, in review or draft

  Background:
    Given the inspector is logged in to CoreSuite

  Scenario: Audit report table is displayed with correct statuses
    Given the inspector navigates to the Audit Reports module
    Then the audit reports table is visible
    And at least one report shows status "FINAL"
    And at least one report shows status "IN REVIEW"
    And at least one report shows status "DRAFT"

  Scenario: New audit report button is accessible
    Given the inspector navigates to the Audit Reports module
    Then the new audit report button is visible and highlighted