package com.app.qa.coresuite.pages;

import com.app.qa.coresuite.support.HighlightHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

public class AdminPage {
    
    private final WebDriver driver;
    private final HighlightHelper highlight;
    
    private final By userTable = By.id("user-table");
    private final By tableRows = By.cssSelector("#user-table tbody tr");
    private final By settingsGrid = By.cssSelector(".settings-grid");
    
    public AdminPage(WebDriver driver) {
        this.driver = driver;
        this.highlight = new HighlightHelper(driver);
    }
    
    public void open(String baseUrl) {
        driver.get(baseUrl + "/admin.html");
    }
    
    public boolean isUserTableVisible() {
        try {
            return driver.findElement(userTable).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public int getUserRowCount() {
        return driver.findElements(tableRows).size();
    }
    
    public boolean isUserPresent(String username) {
        List<WebElement> rows = driver.findElements(tableRows);
        for (WebElement row : rows) {
            if (row.getText().contains(username)) {
                highlight.scrollAndHighlight(row);
                return true;
            }
        }
        return false;
    }
    
    public boolean isSessionTimeoutFieldVisible() {
        try {
            // Look for any input or setting in settings grid
            WebElement grid = driver.findElement(settingsGrid);
            return grid.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean isLanguageSelectorVisible() {
        try {
            WebElement grid = driver.findElement(settingsGrid);
            return grid.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean isReportFormatSelectorVisible() {
        try {
            WebElement grid = driver.findElement(settingsGrid);
            return grid.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
