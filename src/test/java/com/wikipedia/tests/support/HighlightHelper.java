package com.wikipedia.tests.support;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HighlightHelper {

    private static final String[] COLORS = {
        "#FFD700",  // gold
        "#90EE90",  // light green
        "#87CEEB",  // sky blue
        "#FFB6C1",  // light pink
        "#FFA500",  // orange
        "#DDA0DD",  // plum
        "#98FB98",  // pale green
        "#F0E68C",  // khaki
        "#ADD8E6",  // light blue
        "#FF6347"   // tomato
    };

    private int colorIndex = 0;
    private final WebDriver driver;

    public HighlightHelper(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Highlights a WebElement with a cycling color and a bold border.
     * Pauses 400 ms so it is visible during a headed run.
     */
    public void highlight(WebElement element) {
        String color = COLORS[colorIndex % COLORS.length];
        colorIndex++;
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(
            "arguments[0].style.backgroundColor = arguments[1];" +
            "arguments[0].style.border = '3px solid #333';" +
            "arguments[0].style.transition = 'all 0.2s ease';",
            element, color
        );
        try { Thread.sleep(400); } catch (InterruptedException ignored) {}
    }

    /**
     * Scrolls element into view, then highlights it.
     */
    public void scrollAndHighlight(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", element);
        try { Thread.sleep(200); } catch (InterruptedException ignored) {}
        highlight(element);
    }

    /**
     * Clears all highlight styles from the page.
     */
    public void clearHighlights() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(
            "document.querySelectorAll('*').forEach(el => {" +
            "  el.style.backgroundColor = '';" +
            "  el.style.border = '';" +
            "});"
        );
    }
}