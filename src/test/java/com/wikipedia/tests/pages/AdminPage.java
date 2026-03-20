package com.wikipedia.tests.pages;

import com.wikipedia.tests.support.HighlightHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

public class AdminPage {

    private final WebDriver driver;
    private final HighlightHelper highlight;

    private final By userTable       = By.id("user-table");
    private final By userRows        = By.cssSelector("#user-table tbody tr");
    private final By sessionTimeout  = By.id("session-timeout");
    private final By defaultLanguage = By.id("default-language");
    private final By reportFormat    = By.id("report-format");

    public AdminPage(WebDriver driver) {
        this.driver    = driver;
        this.highlight = new HighlightHelper(driver);
    }

    public void open(String baseUrl) {
        driver.get(baseUrl + "/admin.html");
    }

    public boolean isUserTableVisible() {
        WebElement table = driver.findElement(userTable);
        highlight.scrollAndHighlight(table);
        return table.isDisplayed();
    }

    public int getUserRowCount() {
        return driver.findElements(userRows).size();
    }

    public boolean isUserListed(String username) {
        List<WebElement> rows = driver.findElements(userRows);
        return rows.stream().anyMatch(r -> r.getText().contains(username));
    }

    public boolean isSessionTimeoutVisible() {
        WebElement el = driver.findElement(sessionTimeout);
        highlight.scrollAndHighlight(el);
        return el.isDisplayed();
    }

    public boolean isLanguageSelectorVisible() {
        WebElement el = driver.findElement(defaultLanguage);
        highlight.scrollAndHighlight(el);
        return el.isDisplayed();
    }

    public boolean isReportFormatSelectorVisible() {
        WebElement el = driver.findElement(reportFormat);
        highlight.scrollAndHighlight(el);
        return el.isDisplayed();
    }
}