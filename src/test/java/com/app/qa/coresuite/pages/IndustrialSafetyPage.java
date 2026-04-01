package com.app.qa.coresuite.pages;

import com.app.qa.coresuite.support.HighlightHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

public class IndustrialSafetyPage {
    
    private final WebDriver driver;
    private final HighlightHelper highlight;
    
    private final By assessmentsTable = By.cssSelector("table");
    private final By tableRows = By.cssSelector("tbody tr");
    private final By riskCards = By.cssSelector(".risk-card");
    
    public IndustrialSafetyPage(WebDriver driver) {
        this.driver = driver;
        this.highlight = new HighlightHelper(driver);
    }
    
    public void open(String baseUrl) {
        driver.get(baseUrl + "/industrial-safety.html");
    }
    
    public boolean isAssessmentsTableVisible() {
        try {
            return driver.findElement(assessmentsTable).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public int getAssessmentRowCount() {
        return driver.findElements(tableRows).size();
    }
    
    public int getRiskCardCount() {
        return driver.findElements(riskCards).size();
    }
    
    public boolean hasRiskCardWithLabel(String label) {
        List<WebElement> cards = driver.findElements(riskCards);
        for (WebElement card : cards) {
            if (card.getText().contains(label)) {
                highlight.scrollAndHighlight(card);
                return true;
            }
        }
        return false;
    }
}
