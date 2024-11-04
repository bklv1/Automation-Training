package base;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public abstract class Base {

    protected static WebDriver driver = DriverManager.getDriver();

    protected static JavascriptExecutor javascriptExecutor;

    protected static void waitForComponent(String labelName, WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected static void waitForComponents(List<WebElement> elements) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        for (WebElement element : elements) {
            wait.until(ExpectedConditions.visibilityOf(element));
        }
    }

    public static void scrollTo(String labelName, WebElement element) {
        javascriptExecutor.executeScript(
            "arguments[0].scrollIntoView({behavior: 'auto', block: 'center', inline: 'center'});", element);
    }
}
