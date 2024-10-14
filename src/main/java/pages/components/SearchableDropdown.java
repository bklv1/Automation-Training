package pages.components;

import base.Base;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class SearchableDropdown extends Base{

    public static void selectFirstSearchValue(String labelName, String searchValue) {
        getComponent(labelName).sendKeys(searchValue);
        getComponent(labelName).findElements(getSearchValue(searchValue)).get(0).click();
    }

    private static By getSearchValue(String searchValue) {
        return By.xpath(String.format("//*[text()='%s']", searchValue));
    }

    private static WebElement getComponent(String labelName) {
        return driver.findElement(By.xpath(String.format("//label[text()='%s']/following::input[1]", labelName)));
    }
}
