@nfr @accessibility @wcag
Feature: NFR – Accessibility check with axe-core (WCAG 2.1 / BITV)
  As a QA engineer
  I want to run automated accessibility checks on each page
  So that I confirm the platform meets WCAG 2.1 and BITV accessibility standards

  Scenario: Login page has no critical accessibility violations
    Given the CoreSuite login page is open
    Then the page has no critical axe-core accessibility violations

  Scenario: Dashboard page has no critical accessibility violations
    Given the inspector is logged in to CoreSuite
    When the dashboard page loads
    Then the page has no critical axe-core accessibility violations

  Scenario: Vehicle Inspection page has no critical accessibility violations
    Given the inspector is logged in to CoreSuite
    And the inspector navigates to the Vehicle Inspection module
    Then the page has no critical axe-core accessibility violations
