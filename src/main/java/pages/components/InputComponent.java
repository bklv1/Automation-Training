package pages.components;

import base.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class InputComponent {

    static JavascriptExecutor javascriptExecutor;

    private static WebDriver driver = DriverManager.getDriver();

    public static void sendKeys(String labelName, String value) {
        getComponent(labelName).sendKeys(value);
    }

    private static WebElement getComponent(String labelName) {
        return driver.findElement(By.xpath(String.format("//label[text()='%s']/following::input[1]", labelName)));
    }

    private static void scrollTo(String labelName) {
        javascriptExecutor.executeScript(
            "arguments[0].scrollIntoView({behavior: 'auto', block: 'center', inline: 'center'});",
            getComponent(labelName));
    }


    public static void waitForComponent(String labelName) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOf(getComponent(labelName)));
    }
}
