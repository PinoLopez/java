package com.wikipedia.tests.pages;

import com.wikipedia.tests.support.HighlightHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

public class VehicleInspectionPage {

    private final WebDriver driver;
    private final HighlightHelper highlight;

    private final By kpiTotal      = By.id("kpi-total");
    private final By kpiPass       = By.id("kpi-pass");
    private final By kpiFail       = By.id("kpi-fail");
    private final By kpiPending    = By.id("kpi-pending");
    private final By tableRows     = By.cssSelector("#table-body tr");
    private final By searchInput   = By.id("search-input");
    private final By searchButton  = By.id("search-btn");
    private final By newInspBtn    = By.id("new-inspection-btn");

    public VehicleInspectionPage(WebDriver driver) {
        this.driver    = driver;
        this.highlight = new HighlightHelper(driver);
    }

    public void open(String baseUrl) {
        driver.get(baseUrl + "/vehicle-inspection.html");
    }

    public boolean isKpiVisible(String kpiName) {
        By locator = switch (kpiName.toLowerCase()) {
            case "total"   -> kpiTotal;
            case "passed"  -> kpiPass;
            case "failed"  -> kpiFail;
            case "pending" -> kpiPending;
            default -> throw new IllegalArgumentException("Unknown KPI: " + kpiName);
        };
        WebElement el = driver.findElement(locator);
        highlight.scrollAndHighlight(el);
        return el.isDisplayed();
    }

    public int getTableRowCount() {
        List<WebElement> rows = driver.findElements(tableRows);
        return (int) rows.stream()
            .filter(r -> !r.getCssValue("display").equals("none"))
            .count();
    }

    public boolean rowsContainPlates() {
        List<WebElement> rows = driver.findElements(tableRows);
        return rows.stream().anyMatch(r -> r.getText().matches(".*\\d{4}\\s[A-Z]+.*"));
    }

    public boolean rowsContainBadges() {
        return !driver.findElements(By.cssSelector("#table-body .badge")).isEmpty();
    }

    public void searchFor(String query) {
        WebElement input = driver.findElement(searchInput);
        highlight.scrollAndHighlight(input);
        input.clear();
        input.sendKeys(query);
        driver.findElement(searchButton).click();
        try { Thread.sleep(300); } catch (InterruptedException ignored) {}
    }

    public boolean tableContainsText(String text) {
        List<WebElement> rows = driver.findElements(tableRows);
        return rows.stream().anyMatch(r ->
            !r.getCssValue("display").equals("none") &&
            r.getText().contains(text)
        );
    }

    public boolean isNewInspectionButtonVisible() {
        WebElement btn = driver.findElement(newInspBtn);
        return btn.isDisplayed();
    }

    public void highlightNewInspectionButton() {
        highlight.scrollAndHighlight(driver.findElement(newInspBtn));
    }
}