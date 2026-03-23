@nfr @security @owasp
Feature: NFR – Security baseline scan with OWASP ZAP
  As a security engineer
  I want to run a baseline passive scan against the mock app
  So that I confirm no obvious high-risk security vulnerabilities are exposed

  Scenario: OWASP ZAP baseline scan reports no high-risk alerts on the login page
    Given the mock server is available for security scanning
    When OWASP ZAP runs a baseline scan against the login page
    Then the scan completes without high-risk alerts
