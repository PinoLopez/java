package com.wikipedia.tests.pages;

import com.wikipedia.tests.support.HighlightHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

public class AuditsPage {

    private final WebDriver driver;
    private final HighlightHelper highlight;

    private final By auditTable   = By.id("audit-table");
    private final By tableRows    = By.cssSelector("#audit-table tbody tr");
    private final By badges       = By.cssSelector("#audit-table .badge");
    private final By newAuditBtn  = By.id("new-audit-btn");

    public AuditsPage(WebDriver driver) {
        this.driver    = driver;
        this.highlight = new HighlightHelper(driver);
    }

    public void open(String baseUrl) {
        driver.get(baseUrl + "/audits.html");
    }

    public boolean isTableVisible() {
        WebElement table = driver.findElement(auditTable);
        highlight.scrollAndHighlight(table);
        return table.isDisplayed();
    }

    public boolean hasStatusBadge(String status) {
        List<WebElement> badgeEls = driver.findElements(badges);
        return badgeEls.stream().anyMatch(b -> b.getText().contains(status));
    }

    public boolean isNewAuditButtonVisible() {
        WebElement btn = driver.findElement(newAuditBtn);
        highlight.scrollAndHighlight(btn);
        return btn.isDisplayed();
    }
}