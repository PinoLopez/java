Feature: Environmental sample results
  As an environmental inspector
  I want to view sample results with compliance status
  So that I can identify non-compliant readings immediately

  Background:
    Given the inspector is logged in to CoreSuite

  Scenario: Environmental results table is displayed
    Given the inspector navigates to the Environmental Testing module
    Then the sample results table is visible
    And at least one result shows status "COMPLIANT"
    And at least one result shows status "NON-COMPLIANT"

  Scenario: Type filter dropdown is available
    Given the inspector navigates to the Environmental Testing module
    Then the type filter dropdown is visible
    And the new sample button is visible and highlighted