Feature: Valid login to CoreSuite
  As a registered inspector
  I want to sign in with correct credentials
  So that I can access the CoreSuite platform

  Scenario: Inspector logs in with valid credentials
    Given the CoreSuite login page is open
    When the inspector enters username "inspector" and password "suite2026"
    And clicks the Sign In button
    Then the dashboard page is displayed
    And the main navigation is visible