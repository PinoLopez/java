package com.wikipedia.tests.pages;

import com.wikipedia.tests.support.HighlightHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

public class EnvironmentalPage {

    private final WebDriver driver;
    private final HighlightHelper highlight;

    private final By envTable      = By.id("env-table");
    private final By tableRows     = By.cssSelector("#env-table tbody tr");
    private final By typeFilter    = By.id("type-filter");
    private final By newSampleBtn  = By.id("new-sample-btn");
    private final By badges        = By.cssSelector("#env-table .badge");

    public EnvironmentalPage(WebDriver driver) {
        this.driver    = driver;
        this.highlight = new HighlightHelper(driver);
    }

    public void open(String baseUrl) {
        driver.get(baseUrl + "/environmental.html");
    }

    public boolean isTableVisible() {
        WebElement table = driver.findElement(envTable);
        highlight.scrollAndHighlight(table);
        return table.isDisplayed();
    }

    public boolean hasStatusBadge(String status) {
        List<WebElement> badgeEls = driver.findElements(badges);
        return badgeEls.stream().anyMatch(b -> b.getText().contains(status));
    }

    public boolean isTypeFilterVisible() {
        WebElement filter = driver.findElement(typeFilter);
        highlight.scrollAndHighlight(filter);
        return filter.isDisplayed();
    }

    public boolean isNewSampleButtonVisible() {
        WebElement btn = driver.findElement(newSampleBtn);
        highlight.scrollAndHighlight(btn);
        return btn.isDisplayed();
    }
}