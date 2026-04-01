package com.app.qa.coresuite.pages;

import com.app.qa.coresuite.support.HighlightHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

public class CertificationPage {
    
    private final WebDriver driver;
    private final HighlightHelper highlight;
    
    private final By certificationTable = By.tagName("table");
    private final By tableRows = By.cssSelector("tbody tr");
    private final By expiryAlert = By.cssSelector(".alert-box");
    
    public CertificationPage(WebDriver driver) {
        this.driver = driver;
        this.highlight = new HighlightHelper(driver);
    }
    
    public void open(String baseUrl) {
        driver.get(baseUrl + "/certification.html");
    }
    
    public int getCertificationRowCount() {
        return driver.findElements(tableRows).size();
    }
    
    public boolean hasCertificationWithStatus(String status) {
        List<WebElement> rows = driver.findElements(tableRows);
        for (WebElement row : rows) {
            if (row.getText().contains(status)) {
                highlight.scrollAndHighlight(row);
                return true;
            }
        }
        return false;
    }
    
    public boolean isExpiryAlertVisible() {
        try {
            WebElement el = driver.findElement(expiryAlert);
            highlight.scrollAndHighlight(el);
            return el.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
