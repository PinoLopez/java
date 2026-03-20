Feature: Industrial safety risk assessment cards
  As a safety inspector
  I want to see active risk assessment cards with their risk level
  So that I can prioritise high-risk items

  Background:
    Given the inspector is logged in to CoreSuite

  Scenario: Risk assessment cards are displayed
    Given the inspector navigates to the Industrial Safety module
    Then at least 2 risk assessment cards are visible
    And at least one card shows a "HIGH RISK" label
    And at least one card shows a "MEDIUM RISK" label

  Scenario: Recent assessments table is present
    Given the inspector navigates to the Industrial Safety module
    Then the safety assessments table is visible
    And the table contains at least 1 row