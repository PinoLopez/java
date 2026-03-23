@nfr @dsgvo @compliance
Feature: NFR – DSGVO (GDPR) compliance checks
  As a compliance officer
  I want to verify that personal data handling meets DSGVO requirements
  So that I confirm the platform complies with EU data protection law

  Scenario: Login page does not expose personal data patterns in page source
    Given the CoreSuite login page is open
    Then the page source does not contain hardcoded personal data patterns

  Scenario: Administration page does not expose plaintext passwords or raw email inputs
    Given the inspector is logged in to CoreSuite
    And the inspector navigates to the Administration module
    Then no password fields display plaintext values
    And the user table does not expose raw email addresses in the DOM
