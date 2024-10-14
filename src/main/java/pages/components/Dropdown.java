package pages.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import base.Base;

public class Dropdown
    extends Base {

    public static void selectDropdownValue(String labelName, String dropdownValue) {
        getComponent(labelName).click();
        getComponent(labelName).findElement(getDropdownValue(dropdownValue)).click();
    }

    private static By getDropdownValue(String dropdownValue) {
        return By.xpath(String.format("//*[contains(text(),'%s')]", dropdownValue));
    }

    private static WebElement getComponent(String labelName) {
        return driver.findElement(By.xpath(String.format("//label[text()='%s']/following::i[1]", labelName)));
    }

    public static void clickResetButton() {
        driver.findElement(By.xpath("//button[text()='Reset']")).click();
    }
}
