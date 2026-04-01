@docker
Feature: Docker Infrastructure Lifecycle
  Scenario: Validate Stack Recovery
    Given the docker-compose.yml is present in the project root
    When Docker Compose brings up the stack
    Then the mock app container reaches healthy status
    And the login page responds with HTTP 200 on the Docker port