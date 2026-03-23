@docker @onoff
Feature: On/Off – Docker environment lifecycle
  As a DevOps engineer
  I want to start and stop the full application stack using Docker Compose
  So that I confirm the environment starts cleanly, passes health checks
  and stops without leaving orphan containers or volumes

  Scenario: Docker Compose starts the mock app and health check passes
    Given the docker-compose.yml is present in the project root
    When Docker Compose brings up the stack
    Then the mock app container reaches healthy status
    And the login page responds with HTTP 200 on the Docker port

  Scenario: Docker Compose stops cleanly with no orphan containers
    Given the Docker stack is running
    When Docker Compose brings down the stack with volume cleanup
    Then no CoreSuite containers remain running
    And no CoreSuite services remain in compose

  Scenario: Full Docker Compose cycle repeated three times succeeds every time
    Given the docker-compose.yml is present in the project root
    When the Docker Compose full cycle is repeated 3 times
    Then every cycle reports a clean start and clean stop
