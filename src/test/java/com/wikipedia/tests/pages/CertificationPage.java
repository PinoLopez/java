package com.wikipedia.tests.pages;

import com.wikipedia.tests.support.HighlightHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

public class CertificationPage {

    private final WebDriver driver;
    private final HighlightHelper highlight;

    private final By alertBox   = By.cssSelector(".alert-box");
    private final By certTable  = By.id("cert-table");
    private final By tableRows  = By.cssSelector("#cert-table tbody tr");
    private final By badges     = By.cssSelector("#cert-table .badge");

    public CertificationPage(WebDriver driver) {
        this.driver    = driver;
        this.highlight = new HighlightHelper(driver);
    }

    public void open(String baseUrl) {
        driver.get(baseUrl + "/certification.html");
    }

    public boolean isExpiryAlertVisible() {
        WebElement alert = driver.findElement(alertBox);
        highlight.scrollAndHighlight(alert);
        return alert.isDisplayed();
    }

    public int getTableRowCount() {
        return driver.findElements(tableRows).size();
    }

    public boolean hasStatusBadge(String status) {
        List<WebElement> badgeEls = driver.findElements(badges);
        return badgeEls.stream().anyMatch(b -> b.getText().contains(status));
    }

    public boolean isTableVisible() {
        WebElement table = driver.findElement(certTable);
        highlight.scrollAndHighlight(table);
        return table.isDisplayed();
    }
}