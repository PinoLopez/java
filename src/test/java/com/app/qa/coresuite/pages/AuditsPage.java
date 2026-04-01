package com.app.qa.coresuite.pages;

import com.app.qa.coresuite.support.HighlightHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

public class AuditsPage {
    
    private final WebDriver driver;
    private final HighlightHelper highlight;
    
    private final By reportsTable = By.id("audit-table");
    private final By tableRows = By.cssSelector("#audit-table tbody tr");
    private final By newReportBtn = By.id("new-audit-btn");
    
    public AuditsPage(WebDriver driver) {
        this.driver = driver;
        this.highlight = new HighlightHelper(driver);
    }
    
    public void open(String baseUrl) {
        driver.get(baseUrl + "/audits.html");
    }
    
    public boolean isReportsTableVisible() {
        try {
            return driver.findElement(reportsTable).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean hasReportWithStatus(String status) {
        List<WebElement> rows = driver.findElements(tableRows);
        for (WebElement row : rows) {
            if (row.getText().contains(status)) {
                highlight.scrollAndHighlight(row);
                return true;
            }
        }
        return false;
    }
    
    public boolean isNewReportButtonVisible() {
        try {
            WebElement el = driver.findElement(newReportBtn);
            highlight.scrollAndHighlight(el);
            return el.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
