import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestRecorder {

    private WebDriver driver;
    private WebDriverWait wait;
    private Map<String, List<String>> pageClicksMap;
    private Map<String, List<String>> pageHoversMap;
    private String currentUrl;

    public static void main(String[] args) {
        TestRecorder recorder = new TestRecorder();
        recorder.run();
    }

    public TestRecorder() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        pageClicksMap = new HashMap<>();
        pageHoversMap = new HashMap<>();
    }

    public void run() {
        try {
            setupShutdownHook();
            openInitialPage("https://opensource-demo.orangehrmlive.com/");
            monitorUserInteractions();
        } finally {
            cleanUp();
        }
    }

    private void openInitialPage(String url) {
        driver.get(url);
        currentUrl = driver.getCurrentUrl();
        pageClicksMap.put(currentUrl, new ArrayList<>());
        pageHoversMap.put(currentUrl, new ArrayList<>());
        injectListeners();
    }

    private void monitorUserInteractions() {
        boolean browserOpen = true;

        while (browserOpen) {
            try {
                handleUrlChange();
                recordInteractions();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            } catch (WebDriverException e) {
                browserOpen = false;
            }
        }
    }

    private void handleUrlChange() {
        if (!currentUrl.equals(driver.getCurrentUrl())) {
            currentUrl = driver.getCurrentUrl();
            wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
            injectListeners();
            pageClicksMap.putIfAbsent(currentUrl, new ArrayList<>());
            pageHoversMap.putIfAbsent(currentUrl, new ArrayList<>());
        }
    }

    private void recordInteractions() {
        recordClickedElement();
        recordHoveredElement();
    }

    private void recordClickedElement() {
        String clickedElementHtml = (String) ((JavascriptExecutor) driver).executeScript("return window.clickedElementHtml;");
        if (clickedElementHtml != null && !pageClicksMap.get(currentUrl).contains(clickedElementHtml)) {
            pageClicksMap.get(currentUrl).add(clickedElementHtml);
            System.out.println("Clicked Element: " + clickedElementHtml);
        }
    }

    private void recordHoveredElement() {
        String hoveredElementHtml = (String) ((JavascriptExecutor) driver).executeScript("return window.hoveredElementHtml;");
        if (hoveredElementHtml != null && !pageHoversMap.get(currentUrl).contains(hoveredElementHtml)) {
            pageHoversMap.get(currentUrl).add(hoveredElementHtml);
            System.out.println("Hovered Element: " + hoveredElementHtml);
        }
    }

    private void injectListeners() {
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
            "    }, 5000);" +
            "}, true);" +
            "document.addEventListener('mouseout', function(event) {" +
            "    clearTimeout(hoverTimeout);" +
            "}, true);";
        ((JavascriptExecutor) driver).executeScript(script);
    }

    private void setupShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::printRecordedElements));
    }

    private void printRecordedElements() {
        System.out.println("Shutdown hook triggered. Printing recorded elements...");
        printElements("All Clicked Elements by Page:", pageClicksMap);
        printElements("All Hovered Elements by Page:", pageHoversMap);
    }

    private void printElements(String header, Map<String, List<String>> elementsMap) {
        System.out.println(header);
        for (Map.Entry<String, List<String>> entry : elementsMap.entrySet()) {
            if (!entry.getValue().isEmpty()) {
                System.out.println("Page URL: " + entry.getKey());
                for (String elementHtml : entry.getValue()) {
                    System.out.println(" - " + elementHtml);
                }
            }
        }
    }

    private void cleanUp() {
        try {
            driver.quit();
        } catch (WebDriverException e) {
            // Ignore if the driver is already closed
        }
    }
}
