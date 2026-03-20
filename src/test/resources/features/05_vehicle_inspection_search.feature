Feature: Vehicle inspection search and filter
  As an inspector
  I want to search the inspection records by plate or inspector name
  So that I can locate a specific record quickly

  Background:
    Given the inspector is logged in to CoreSuite
    And the inspector navigates to the Vehicle Inspection module

  Scenario: Search by plate number returns matching records
    When the inspector searches for "4782 KLP"
    Then the inspection table shows records matching "4782 KLP"

  Scenario: New inspection button is present and highlighted
    Then the new inspection button is visible
    And the new inspection button is highlighted by the test