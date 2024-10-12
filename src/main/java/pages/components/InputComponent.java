package pages.components;

import base.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class InputComponent {
    //    public static WebElement scrollElementToCenter(By by) {
    //        WebElement element = element(by);
    //        return scrollElementToCenter(element);
    //    }
    //    public static WebElement scrollToLabel(String label) {
    //        By labelBy = By.xpath("//label[normalize-space() ='"+label+"'] | //label//*[text() ='"+label+"']");
    //        return scrollElementToCenter(labelBy);
    //    }
    //    public static WebElement scrollElementToCenter(WebElement webElement) {
    //        javaScriptExecutor().executeScript("arguments[0].scrollIntoView({behavior: 'auto', block: 'center', inline: 'center'});", webElement);
    //        return webElement;
    //    }

    private static WebDriver driver = DriverManager.getDriver();

    public static void sendKeys(String labelName, String value) {
        getInputComponent(labelName).sendKeys(value);
    }

    private static WebElement getInputComponent(String labelName) {
        return driver.findElement(By.xpath(String.format("//label[text()='%s']/following::input[1]", labelName)));
    }

}
