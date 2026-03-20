package com.wikipedia.tests.pages;

import com.wikipedia.tests.support.HighlightHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

public class IndustrialSafetyPage {

    private final WebDriver driver;
    private final HighlightHelper highlight;

    private final By riskCards   = By.cssSelector(".risk-card");
    private final By tableRows   = By.cssSelector("#safety-table tbody tr");
    private final By riskLevels  = By.cssSelector(".risk-level");

    public IndustrialSafetyPage(WebDriver driver) {
        this.driver    = driver;
        this.highlight = new HighlightHelper(driver);
    }

    public void open(String baseUrl) {
        driver.get(baseUrl + "/industrial-safety.html");
    }

    public int getRiskCardCount() {
        List<WebElement> cards = driver.findElements(riskCards);
        cards.forEach(highlight::scrollAndHighlight);
        return cards.size();
    }

    public boolean hasRiskLevelLabel(String label) {
        List<WebElement> levels = driver.findElements(riskLevels);
        return levels.stream().anyMatch(el -> el.getText().contains(label));
    }

    public boolean isSafetyTableVisible() {
        WebElement table = driver.findElement(By.id("safety-table"));
        highlight.scrollAndHighlight(table);
        return table.isDisplayed();
    }

    public int getSafetyTableRowCount() {
        return driver.findElements(tableRows).size();
    }
}