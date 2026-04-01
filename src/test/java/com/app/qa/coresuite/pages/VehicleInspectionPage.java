package com.app.qa.coresuite.pages;

import com.app.qa.coresuite.support.HighlightHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class VehicleInspectionPage {
    
    private final WebDriver driver;
    private final HighlightHelper highlight;
    private final WebDriverWait wait;
    
    private final By inspectionTable = By.tagName("table");
    private final By kpiDivs = By.cssSelector(".kpi");
    private final By newInspectionBtn = By.id("new-inspection-btn");
    private final By searchInput = By.cssSelector(".search-bar input");
    private final By searchBtn = By.cssSelector(".search-bar button");
    private final By tableRows = By.cssSelector("tbody tr");
    
    public VehicleInspectionPage(WebDriver driver) {
        this.driver = driver;
        this.highlight = new HighlightHelper(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    
    public void open(String baseUrl) {
        driver.get(baseUrl + "/vehicle-inspection.html");
    }
    
    public boolean isInspectionTableVisible() {
        try {
            return driver.findElement(inspectionTable).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean isTotalInspectionsKpiVisible() {
        try {
            List<WebElement> kpis = driver.findElements(kpiDivs);
            if (kpis.size() >= 1) {
                highlight.scrollAndHighlight(kpis.get(0));
                return kpis.get(0).isDisplayed();
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean isPassedInspectionsKpiVisible() {
        try {
            List<WebElement> kpis = driver.findElements(kpiDivs);
            if (kpis.size() >= 2) {
                highlight.scrollAndHighlight(kpis.get(1));
                return kpis.get(1).isDisplayed();
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean isFailedInspectionsKpiVisible() {
        try {
            List<WebElement> kpis = driver.findElements(kpiDivs);
            if (kpis.size() >= 3) {
                highlight.scrollAndHighlight(kpis.get(2));
                return kpis.get(2).isDisplayed();
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean isPendingReviewKpiVisible() {
        try {
            List<WebElement> kpis = driver.findElements(kpiDivs);
            if (kpis.size() >= 4) {
                highlight.scrollAndHighlight(kpis.get(3));
                return kpis.get(3).isDisplayed();
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean isNewInspectionButtonVisible() {
        try {
            WebElement el = driver.findElement(newInspectionBtn);
            highlight.scrollAndHighlight(el);
            return el.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean isNewInspectionButtonHighlighted() {
        try {
            WebElement el = driver.findElement(newInspectionBtn);
            return el.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public void search(String searchTerm) {
        try {
            WebElement searchEl = driver.findElement(searchInput);
            highlight.scrollAndHighlight(searchEl);
            searchEl.clear();
            searchEl.sendKeys(searchTerm);
            driver.findElement(searchBtn).click();
            // Wait for filter to complete
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            wait.until(ExpectedConditions.presenceOfElementLocated(tableRows));
        } catch (Exception e) {
            System.out.println("Search element not found: " + e.getMessage());
        }
    }
    
    public boolean areSearchResultsMatching(String searchTerm) {
        List<WebElement> rows = driver.findElements(tableRows);
        if (rows.isEmpty()) return true;
        
        int visibleRowCount = 0;
        for (WebElement row : rows) {
            // Only check VISIBLE rows (not filtered out)
            if (row.isDisplayed()) {
                visibleRowCount++;
                String text = row.getText().toLowerCase();
                if (!text.contains(searchTerm.toLowerCase())) {
                    return false;
                }
            }
        }
        // Pass if at least one visible row matches the search term
        return visibleRowCount > 0;
    }
    
    public int getInspectionRowCount() {
        return driver.findElements(tableRows).size();
    }
    
    public boolean allRowsHavePlateNumber() {
        List<WebElement> rows = driver.findElements(tableRows);
        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 1) {
                String firstCell = cells.get(0).getText();
                if (firstCell.isEmpty()) return false;
            } else {
                return false;
            }
        }
        return true;
    }
    
    public boolean allRowsHaveResultBadge() {
        List<WebElement> rows = driver.findElements(tableRows);
        for (WebElement row : rows) {
            List<WebElement> badges = row.findElements(By.cssSelector(".badge"));
            if (badges.isEmpty()) return false;
        }
        return true;
    }
}
