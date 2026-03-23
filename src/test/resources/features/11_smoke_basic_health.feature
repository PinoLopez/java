@smoke @smoke-basic
Feature: Smoke – Basic HTTP health check
  As a system operator
  I want to verify the application responds at the HTTP level
  So that I confirm the platform is reachable before any browser testing

  Scenario: Login endpoint returns HTTP 200
    Given the mock server base URL is configured
    When a GET request is sent to "/index.html"
    Then the HTTP response status is 200

  Scenario: Dashboard endpoint returns HTTP 200
    Given the mock server base URL is configured
    When a GET request is sent to "/dashboard.html"
    Then the HTTP response status is 200
