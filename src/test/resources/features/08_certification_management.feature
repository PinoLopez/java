Feature: Certification management
  As a certification officer
  I want to see the full certification register with statuses
  So that I can manage renewals and flag expiring certificates

  Background:
    Given the inspector is logged in to CoreSuite

  Scenario: Expiry alert banner is shown
    Given the inspector navigates to the Certification module
    Then the expiry alert banner is visible

  Scenario: Certification table shows multiple statuses
    Given the inspector navigates to the Certification module
    Then the certification table contains at least 3 rows
    And at least one certification shows status "ACTIVE"
    And at least one certification shows status "EXPIRING"
    And at least one certification shows status "EXPIRED"