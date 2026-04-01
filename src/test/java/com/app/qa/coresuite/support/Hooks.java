package com.app.qa.coresuite.support;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.time.Duration;

public class Hooks {
    
    private final TestContext context;
    
    public Hooks(TestContext context) {
        this.context = context;
    }
    
    @Before
    public void setup() {
        // Initialize baseUrl from system property or use default
        String baseUrl = System.getProperty("baseUrl", 
            "https://pinolopez.github.io/java/mock-app");
        context.setBaseUrl(baseUrl);
        
        // Setup ChromeOptions
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--disable-blink-features=AutomationControlled");
        
        // Selenium 4.6+ has built-in Selenium Manager - auto-detects browser version
        ChromeDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        context.setDriver(driver);
        
        System.out.println("ChromeDriver initialized via Selenium Manager. Base URL: " + baseUrl);
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed() && context.getDriver() != null) {
            try {
                byte[] screenshot = ((TakesScreenshot) context.getDriver())
                    .getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", "failure-screenshot");
            } catch (Exception e) {
                System.err.println("Failed to capture screenshot: " + e.getMessage());
            }
        }
        
        if (context.getDriver() != null) {
            context.getDriver().quit();
        }
    }
}
