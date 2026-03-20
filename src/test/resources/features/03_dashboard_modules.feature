Feature: Dashboard module cards
  As a logged-in inspector
  I want to see all available modules on the dashboard
  So that I can navigate to the area I need

  Background:
    Given the inspector is logged in to CoreSuite

  Scenario: All six module cards are displayed
    When the dashboard page loads
    Then the "Vehicle Inspection" module card is visible
    And the "Industrial Safety" module card is visible
    And the "Environmental Testing" module card is visible
    And the "Certification Management" module card is visible
    And the "Audit Reports" module card is visible
    And the "Administration" module card is visible

  Scenario: Vehicle Inspection card links to the correct module
    When the dashboard page loads
    And the inspector clicks the "Vehicle Inspection" module card
    Then the vehicle inspection module is open