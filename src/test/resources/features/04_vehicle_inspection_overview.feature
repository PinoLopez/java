Feature: Vehicle inspection overview
  As an inspector
  I want to see the inspection KPIs and recent records
  So that I can monitor the station workload at a glance

  Background:
    Given the inspector is logged in to CoreSuite

  Scenario: Vehicle inspection KPIs are displayed
    Given the inspector navigates to the Vehicle Inspection module
    Then the total inspections KPI is visible
    And the passed inspections KPI is visible
    And the failed inspections KPI is visible
    And the pending review KPI is visible

  Scenario: Inspection records table is populated
    Given the inspector navigates to the Vehicle Inspection module
    Then the inspection records table contains at least 1 row
    And each row displays a plate number
    And each row displays an inspection result badge