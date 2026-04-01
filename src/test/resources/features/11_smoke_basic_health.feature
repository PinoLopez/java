@smoke @basic
Feature: Basic Smoke Test
  Scenario: Health Check - Login Page Load
    Given the user is on the login page
    Then the page title contains "CoreSuite"