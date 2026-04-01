package com.app.qa.coresuite.pages;

import com.app.qa.coresuite.support.HighlightHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

public class EnvironmentalPage {
    
    private final WebDriver driver;
    private final HighlightHelper highlight;
    
    private final By resultsTable = By.tagName("table");
    private final By tableRows = By.cssSelector("tbody tr");
    private final By typeFilter = By.id("type-filter");
    private final By newSampleBtn = By.id("new-sample-btn");
    
    public EnvironmentalPage(WebDriver driver) {
        this.driver = driver;
        this.highlight = new HighlightHelper(driver);
    }
    
    public void open(String baseUrl) {
        driver.get(baseUrl + "/environmental.html");
    }
    
    public boolean isResultsTableVisible() {
        try {
            return driver.findElement(resultsTable).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean hasResultWithStatus(String status) {
        List<WebElement> rows = driver.findElements(tableRows);
        for (WebElement row : rows) {
            if (row.getText().contains(status)) {
                highlight.scrollAndHighlight(row);
                return true;
            }
        }
        return false;
    }
    
    public boolean isTypeFilterVisible() {
        try {
            WebElement el = driver.findElement(typeFilter);
            highlight.scrollAndHighlight(el);
            return el.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean isNewSampleButtonVisible() {
        try {
            WebElement el = driver.findElement(newSampleBtn);
            highlight.scrollAndHighlight(el);
            return el.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
