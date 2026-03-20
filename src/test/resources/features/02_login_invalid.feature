Feature: Invalid login to CoreSuite
  As a registered inspector
  I want to see a clear error when credentials are wrong
  So that I know my login attempt failed

  Scenario: Inspector enters wrong password
    Given the CoreSuite login page is open
    When the inspector enters username "inspector" and password "wrongpass"
    And clicks the Sign In button
    Then an error message is displayed on the login page
    And the login form is still visible

  Scenario: Inspector submits empty credentials
    Given the CoreSuite login page is open
    When the inspector enters username "" and password ""
    And clicks the Sign In button
    Then an error message is displayed on the login page