import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TestRecorder {
    private static WebDriver driver;
    private static List<WebElement> interactedElements = new ArrayList<>();
    private static long lastInteractionTime = 0;
    private static WebElement lastHoveredElement = null;

    private static boolean isRecording = true;

    public static void main(String[] args) {
        initializeDriver();
        startRecording();
        endRecording();
    }

    private static void initializeDriver() {
        System.setProperty("webdriver.chrome.driver", "C:/Program Files/chromedriver/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
    }

    private static void startRecording() {
        EventFiringWebDriver eventDriver = new EventFiringWebDriver(driver);
        CustomWebDriverEventListener listener = new CustomWebDriverEventListener();
        eventDriver.register(listener);

        System.out.println("Enter the URL you want to open:");
        Scanner scanner = new Scanner(System.in);
        String url = scanner.nextLine();
        eventDriver.get(url);

        Actions actions = new Actions(eventDriver);

        System.out.println("Recording started. Press 'q' and Enter to stop recording.");

        Thread inputThread = new Thread(() -> {
            while (isRecording) {
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("q")) {
                    isRecording = false;
                    System.out.println("Stopping recording...");
                }
            }
        });
        inputThread.start();

        while (isRecording) {
            try {
                WebElement hoveredElement = eventDriver.findElement(By.xpath("//*[contains(@style,'cursor: pointer')]"));
                listener.onMouseHover(hoveredElement);
            } catch (NoSuchElementException e) {
                // No element with cursor: pointer found, continue
            }
            actions.moveByOffset(0, 0).perform(); // This will trigger mouseMoved event
            try {
                Thread.sleep(100); // Check every 100ms
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            inputThread.join(5000); // Wait for input thread to finish, but no longer than 5 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void endRecording() {
        System.out.println("Recording ended. Here's a summary of interacted elements:");
        for (WebElement element : interactedElements) {
            System.out.println(getElementInfo(element));
        }
        driver.quit();
    }

    private static class CustomWebDriverEventListener extends AbstractWebDriverEventListener {
        @Override
        public void beforeClickOn(WebElement element, WebDriver driver) {
            addInteractedElement(element);
        }

        @Override
        public void beforeChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
            addInteractedElement(element);
        }

        @Override
        public void afterNavigateTo(String url, WebDriver driver) {
            System.out.println("Navigated to: " + url);
        }

        @Override
        public void onException(Throwable throwable, WebDriver driver) {
            System.out.println("Exception occurred: " + throwable.getMessage());
        }

        @Override
        public void afterClickOn(WebElement element, WebDriver driver) {
            System.out.println("Clicked on: " + getElementInfo(element));
        }

        @Override
        public void afterChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
            System.out.println("Entered text in: " + getElementInfo(element));
        }

        public void onMouseHover(WebElement element) {
            long currentTime = System.currentTimeMillis();
            if (element != lastHoveredElement) {
                lastHoveredElement = element;
                lastInteractionTime = currentTime;
            } else if (currentTime - lastInteractionTime > 3000) { // 3 seconds
                addInteractedElement(element);
                System.out.println("Hovered over: " + getElementInfo(element));
                lastInteractionTime = currentTime;
            }
        }
    }

    private static void addInteractedElement(WebElement element) {
        if (!interactedElements.contains(element)) {
            interactedElements.add(element);
            System.out.println("New interacted element: " + getElementInfo(element));
        }
    }

    private static String getElementInfo(WebElement element) {
        return element.getTagName() + 
               (element.getAttribute("id").isEmpty() ? "" : " id='" + element.getAttribute("id") + "'") +
               (element.getAttribute("class").isEmpty() ? "" : " class='" + element.getAttribute("class") + "'");
    }
}
