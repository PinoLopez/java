@smoke @smoke-advanced
Feature: Smoke – Advanced module navigation
  As a logged-in inspector
  I want to navigate directly to each module
  So that I confirm all modules are reachable and correctly titled

  Scenario: Industrial Safety module is reachable
    Given the inspector is logged in to CoreSuite
    And the inspector navigates to the Industrial Safety module
    Then the page title contains "Industrial Safety"

  Scenario: Environmental Testing module is reachable
    Given the inspector is logged in to CoreSuite
    And the inspector navigates to the Environmental Testing module
    Then the page title contains "Environmental"

  Scenario: Certification module is reachable
    Given the inspector is logged in to CoreSuite
    And the inspector navigates to the Certification module
    Then the page title contains "Certification"

  Scenario: Audit Reports module is reachable
    Given the inspector is logged in to CoreSuite
    And the inspector navigates to the Audit Reports module
    Then the page title contains "Audit"

  Scenario: Administration module is reachable
    Given the inspector is logged in to CoreSuite
    And the inspector navigates to the Administration module
    Then the page title contains "Administration"
