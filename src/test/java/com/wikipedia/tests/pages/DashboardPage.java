package com.wikipedia.tests.pages;

import com.wikipedia.tests.support.HighlightHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

public class DashboardPage {

    private final WebDriver driver;
    private final HighlightHelper highlight;

    private final By navigation   = By.tagName("nav");
    private final By moduleCards  = By.cssSelector(".card");

    public DashboardPage(WebDriver driver) {
        this.driver    = driver;
        this.highlight = new HighlightHelper(driver);
    }

    public void open(String baseUrl) {
        driver.get(baseUrl + "/dashboard.html");
    }

    public boolean isNavigationVisible() {
        return driver.findElement(navigation).isDisplayed();
    }

    public boolean isModuleCardVisible(String moduleName) {
        List<WebElement> cards = driver.findElements(moduleCards);
        for (WebElement card : cards) {
            if (card.getText().contains(moduleName)) {
                highlight.scrollAndHighlight(card);
                return true;
            }
        }
        return false;
    }

    public void clickModuleCard(String moduleName) {
        List<WebElement> cards = driver.findElements(moduleCards);
        for (WebElement card : cards) {
            if (card.getText().contains(moduleName)) {
                highlight.scrollAndHighlight(card);
                WebElement link = card.findElement(By.tagName("a"));
                link.click();
                return;
            }
        }
        throw new RuntimeException("Module card not found: " + moduleName);
    }

    public String getTitle() {
        return driver.getTitle();
    }
}