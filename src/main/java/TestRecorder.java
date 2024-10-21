import base.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestRecorder {
    public static void main(String[] args) {
        // Initialize WebDriver
        WebDriver driver = DriverManager.getDriver();

        // Open the initial page
        driver.get("https://opensource-demo.orangehrmlive.com/");

        // Inject JavaScript to listen for click and hover events
        injectListeners(driver);

        // Maps to store the list of clicked and hovered elements for each URL
        Map<String, List<String>> pageClicksMap = new HashMap<>();
        Map<String, List<String>> pageHoversMap = new HashMap<>();

        // Monitor URL changes and re-inject JavaScript
        String currentUrl = driver.getCurrentUrl();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Initialize the lists for the initial page
        pageClicksMap.put(currentUrl, new ArrayList<>());
        pageHoversMap.put(currentUrl, new ArrayList<>());

        // Add a shutdown hook to handle graceful shutdown
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutdown hook triggered. Printing recorded elements...");

            // Print all recorded elements grouped by page URL
            System.out.println("All Clicked Elements by Page:");
            for (Map.Entry<String, List<String>> entry : pageClicksMap.entrySet()) {
                if (!entry.getValue().isEmpty()) {  // Check if there are any clicked elements
                    System.out.println("Page URL: " + entry.getKey());
                    for (String elementHtml : entry.getValue()) {
                        System.out.println(" - " + elementHtml);
                    }
                }
            }

            System.out.println("All Hovered Elements by Page:");
            for (Map.Entry<String, List<String>> entry : pageHoversMap.entrySet()) {
                if (!entry.getValue().isEmpty()) {  // Check if there are any hovered elements
                    System.out.println("Page URL: " + entry.getKey());
                    for (String elementHtml : entry.getValue()) {
                        System.out.println(" - " + elementHtml);
                    }
                }
            }

            // Clean up
            driver.quit();
        }));

        while (true) {
            // Check if URL has changed
            if (!currentUrl.equals(driver.getCurrentUrl())) {
                currentUrl = driver.getCurrentUrl();
                // Wait for the new page to load
                wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
                // Re-inject JavaScript on the new page
                injectListeners(driver);

                // Initialize the lists for the new page if not already present
                pageClicksMap.putIfAbsent(currentUrl, new ArrayList<>());
                pageHoversMap.putIfAbsent(currentUrl, new ArrayList<>());
            }

            try {
                // Retrieve the HTML of the last clicked element
                String clickedElementHtml = (String) ((JavascriptExecutor) driver).executeScript("return window.clickedElementHtml;");
                if (clickedElementHtml != null && !pageClicksMap.get(currentUrl).contains(clickedElementHtml)) {
                    pageClicksMap.get(currentUrl).add(clickedElementHtml);
                    System.out.println("Clicked Element: " + clickedElementHtml);
                }

                // Retrieve the HTML of the last hovered element
                String hoveredElementHtml = (String) ((JavascriptExecutor) driver).executeScript("return window.hoveredElementHtml;");
                if (hoveredElementHtml != null && !pageHoversMap.get(currentUrl).contains(hoveredElementHtml)) {
                    pageHoversMap.get(currentUrl).add(hoveredElementHtml);
                    System.out.println("Hovered Element: " + hoveredElementHtml);
                }

                // Sleep for a short duration to prevent excessive CPU usage
                Thread.sleep(1000);

                // Break the loop after 10 unique clicks and 10 unique hovers
                if (pageClicksMap.values().stream().mapToInt(List::size).sum() >= 10 &&
                    pageHoversMap.values().stream().mapToInt(List::size).sum() >= 10) {
                    break;
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Clean up
        driver.quit();
    }

    private static void injectListeners(WebDriver driver) {
        String script = "var hoverTimeout;" +
            "document.addEventListener('click', function(event) {" +
            "    var element = event.target;" +
            "    window.clickedElementHtml = element.outerHTML;" +
            "    console.log('Element clicked: ' + window.clickedElementHtml);" +
            "}, true);" +
            "document.addEventListener('mouseover', function(event) {" +
            "    var element = event.target;" +
            "    hoverTimeout = setTimeout(function() {" +
            "        window.hoveredElementHtml = element.outerHTML;" +
            "        console.log('Element hovered for 5 seconds: ' + window.hoveredElementHtml);" +
            "    }, 5000);" +  // 5 seconds
            "}, true);" +
            "document.addEventListener('mouseout', function(event) {" +
            "    clearTimeout(hoverTimeout);" +
            "}, true);";
        ((JavascriptExecutor) driver).executeScript(script);
    }
}
