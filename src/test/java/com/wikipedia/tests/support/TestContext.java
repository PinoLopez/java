package com.wikipedia.tests.support;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class TestContext {

    private WebDriver driver;
    public final String baseUrl;

    public TestContext() {
        String port = System.getProperty("mock.server.port", "8090");
        this.baseUrl = "http://127.0.0.1:" + port;
    }

    public WebDriver getDriver() {
        if (driver == null) {
            driver = createDriver();
        }
        return driver;
    }

    private WebDriver createDriver() {
        WebDriverManager.chromiumdriver().setup();

        ChromeOptions options = new ChromeOptions();

        // Resolve Chromium binary — snap installs don't appear in PATH via 'which'
        String binaryProp = System.getProperty("chromium.binary", "chromium-browser");
        String[] candidates = {
            binaryProp,
            "/snap/bin/chromium",
            "/usr/bin/chromium-browser",
            "/usr/bin/chromium"
        };
        for (String candidate : candidates) {
            java.io.File f = new java.io.File(candidate);
            if (f.exists() && f.canExecute()) {
                options.setBinary(candidate);
                break;
            }
        }

        boolean headless = Boolean.parseBoolean(
            System.getProperty("headless", "true")
        );
        if (headless) {
            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");
            options.addArguments("--window-size=1920,1080");
        } else {
            options.addArguments("--window-size=1400,900");
        }

        options.addArguments("--disable-extensions");
        options.addArguments("--remote-allow-origins=*");

        return new ChromeDriver(options);
    }

    public void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}