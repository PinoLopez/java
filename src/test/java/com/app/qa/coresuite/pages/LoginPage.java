package com.app.qa.coresuite.pages;

import com.app.qa.coresuite.support.HighlightHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage {

    private final WebDriver driver;
    private final HighlightHelper highlight;

    private final By usernameInput = By.id("username");
    private final By passwordInput = By.id("password");
    private final By loginButton   = By.id("login-btn");
    private final By errorMessage  = By.id("login-error");

    public LoginPage(WebDriver driver) {
        this.driver    = driver;
        this.highlight = new HighlightHelper(driver);
    }

    public void open(String baseUrl) {
        driver.get(baseUrl + "/index.html");
    }

    public void enterUsername(String username) {
        WebElement el = driver.findElement(usernameInput);
        highlight.scrollAndHighlight(el);
        el.clear();
        el.sendKeys(username);
    }

    public void enterPassword(String password) {
        WebElement el = driver.findElement(passwordInput);
        highlight.scrollAndHighlight(el);
        el.clear();
        el.sendKeys(password);
    }

    public void clickSignIn() {
        WebElement el = driver.findElement(loginButton);
        highlight.scrollAndHighlight(el);
        el.click();
    }

    public boolean isErrorDisplayed() {
        WebElement err = driver.findElement(errorMessage);
        return err.isDisplayed();
    }

    public boolean isLoginFormVisible() {
        return driver.findElement(usernameInput).isDisplayed();
    }

    public String getTitle() {
        return driver.getTitle();
    }
}