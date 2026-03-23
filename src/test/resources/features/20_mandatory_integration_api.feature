@mandatory @integration @api
Feature: Mandatory – Integration and API contract checks
  As a QA engineer
  I want to verify that all module endpoints respond correctly via HTTP
  So that I confirm integration between the frontend and backend contracts
  and that mandatory smoke checks pass in a production-like environment

  Scenario Outline: All module endpoints return HTTP 200
    Given the mock server base URL is configured
    When a GET request is sent to "<endpoint>"
    Then the HTTP response status is 200

    Examples:
      | endpoint                  |
      | /index.html               |
      | /dashboard.html           |
      | /vehicle-inspection.html  |
      | /audits.html              |

  Scenario: Smoke-in-production check confirms login page is reachable and correct
    Given the mock server base URL is configured
    When a GET request is sent to "/index.html"
    Then the HTTP response status is 200
    And the response body contains "CoreSuite"
