package base;

import org.openqa.selenium.By;

public class Button
    extends Base {

    public static void clickSubmitButton() {
        driver.findElement(By.xpath(("//button[@type='submit']")))
            .click();
    }

    public static void clickButtonByText(String buttonText) {
        driver.findElement(By.xpath(String.format("//button[normalize-space()='%s']", buttonText)))
            .click();
    }
}
