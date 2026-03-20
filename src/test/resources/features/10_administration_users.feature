Feature: Administration – user management
  As a system administrator
  I want to see all registered users and system settings
  So that I can manage access and platform configuration

  Background:
    Given the inspector is logged in to CoreSuite

  Scenario: User table is displayed with correct columns
    Given the inspector navigates to the Administration module
    Then the user management table is visible
    And the table contains at least 3 user rows
    And the user "inspector" is listed in the table

  Scenario: System settings section is visible
    Given the inspector navigates to the Administration module
    Then the session timeout field is visible
    And the default language selector is visible
    And the report format selector is visible