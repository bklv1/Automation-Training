package pages.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import base.Base;

public class Input
    extends Base {

    public static void sendKeys(String labelName, String value) {
        getComponent(labelName).sendKeys(value);
    }

    private static WebElement getComponent(String labelName) {
        return driver.findElement(By.xpath(String.format("//label[text()='%s']/following::input[1]", labelName)));
    }



}
