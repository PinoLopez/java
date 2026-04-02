@e2e
Feature: E2E – Full vehicle inspection journey
  As an inspector
  I want to log in, navigate to vehicle inspection and search for a record
  So that I confirm the complete end-to-end workflow operates correctly

  Scenario: Inspector completes a full vehicle inspection search journey
    Given the CoreSuite login page is open
    When the inspector enters username "inspector" and password "suite2026"
    And clicks the Sign In button
    Then the dashboard page is displayed
    When the inspector clicks the "Vehicle Inspection" module card
    Then the vehicle inspection module is open
    And the total inspections KPI is visible
    When the inspector searches for "KLP"
    Then the inspection table shows records matching "KLP"
