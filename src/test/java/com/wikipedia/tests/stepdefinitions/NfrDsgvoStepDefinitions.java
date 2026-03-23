package com.wikipedia.tests.stepdefinitions;

import com.wikipedia.tests.support.TestContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class NfrDsgvoStepDefinitions {

    private final TestContext context;

    public NfrDsgvoStepDefinitions(TestContext context) {
        this.context = context;
    }

    @Then("the page source does not contain hardcoded personal data patterns")
    public void noHardcodedPersonalData() {
        String src = context.getDriver().getPageSource().toLowerCase();
        assertFalse(src.contains("password=") || src.contains("passwd="),
            "Page source may contain exposed credentials");
        assertFalse(src.matches("(?s).*\\b\\d{3}-\\d{2}-\\d{4}\\b.*"),
            "Page source may contain SSN-like numeric patterns");
    }

    @Then("no password fields display plaintext values")
    public void noPlaintextPasswords() {
        List<WebElement> fields = context.getDriver()
            .findElements(By.cssSelector("input[type='password']"));
        for (WebElement field : fields) {
            String val = field.getAttribute("value");
            assertTrue(val == null || val.isEmpty(),
                "A password field contains a visible plaintext value");
        }
    }

    @Then("the user table does not expose raw email addresses in the DOM")
    public void noRawEmailInputs() {
        List<WebElement> inputs = context.getDriver()
            .findElements(By.cssSelector("input[type='email']"));
        for (WebElement input : inputs) {
            String val = input.getAttribute("value");
            assertTrue(val == null || val.isEmpty(),
                "An email input field exposes a plaintext address: " + val);
        }
    }
}
