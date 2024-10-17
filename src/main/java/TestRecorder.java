import base.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class TestRecorder {
    public static void main(String[] args) {

        WebDriver driver = DriverManager.getDriver();

        // Open the initial page
        driver.get("https://opensource-demo.orangehrmlive.com/");

        // Inject JavaScript to listen for click events
        injectClickListener(driver);

        // List to store HTML of clicked elements
        List<String> clickedElementsHtml = new ArrayList<>();

        // Monitor URL changes and re-inject JavaScript
        String currentUrl = driver.getCurrentUrl();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        while (true) {
            // Check if URL has changed
            if (!currentUrl.equals(driver.getCurrentUrl())) {
                currentUrl = driver.getCurrentUrl();
                // Wait for the new page to load
                wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
                // Re-inject JavaScript on the new page
                injectClickListener(driver);
            }

            try {
                // Retrieve the HTML of the last clicked element
                String clickedElementHtml = (String) ((JavascriptExecutor) driver).executeScript("return window.clickedElementHtml;");

                if (clickedElementHtml != null && !clickedElementsHtml.contains(clickedElementHtml)) {
                    clickedElementsHtml.add(clickedElementHtml);
                    System.out.println("Clicked Element: " + clickedElementHtml);
                }

                // Sleep for a short duration to prevent excessive CPU usage
                Thread.sleep(1000);

                // Break the loop after 10 unique clicks
                if (clickedElementsHtml.size() >= 10) {
                    break;
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Print all recorded elements
        System.out.println("All Clicked Elements:");
        for (String elementHtml : clickedElementsHtml) {
            System.out.println(elementHtml);
        }

        // Clean up
        driver.quit();
    }

    private static void injectClickListener(WebDriver driver) {
        String script = "document.addEventListener('click', function(event) {" +
            "    var element = event.target;" +
            "    window.clickedElementHtml = element.outerHTML;" +
            "    console.log('Element clicked: ' + window.clickedElementHtml);" +
            "}, true);";
        ((JavascriptExecutor) driver).executeScript(script);
    }
}
