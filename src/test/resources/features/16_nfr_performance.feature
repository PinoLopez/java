@nfr @performance
Feature: NFR – Performance load test with JMeter
  As a QA engineer
  I want to send concurrent requests to the mock app
  So that I confirm the platform responds within acceptable thresholds under load

  Scenario: Login page handles 10 concurrent users within acceptable response time
    Given the mock server is available for load testing
    And a JMeter test plan targets the login page with 10 threads
    When JMeter executes the load test
    Then the average response time is below 1000 milliseconds
    And the error rate is below 1 percent
