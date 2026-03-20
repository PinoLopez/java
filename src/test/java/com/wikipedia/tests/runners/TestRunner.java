package com.wikipedia.tests.runners;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.*;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(
    key = PLUGIN_PROPERTY_NAME,
    value = "pretty," +
            "json:target/cucumber-json/cucumber.json," +
            "html:target/cucumber-html/report.html"
)
@ConfigurationParameter(key = GLUE_PROPERTY_NAME,
    value = "com.wikipedia.tests.stepdefinitions," +
            "com.wikipedia.tests.support")
@ConfigurationParameter(key = FEATURES_PROPERTY_NAME,
    value = "classpath:features")
public class TestRunner {
}