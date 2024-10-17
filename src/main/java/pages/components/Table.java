package pages.components;

import base.Base;

import java.util.Arrays;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class Table
    extends Base {

    public static void assertFirstRowValue(List<String> expectedValues) {
        String rowText = getRowByIndex(1).getText();
        List<String> actualValues = Arrays.asList(rowText.split("\n"));
        Assert.assertEquals(actualValues,expectedValues, "Incorrect table row values!");
    }

    private static WebElement getComponent() {
        return driver.findElement(By.cssSelector(".oxd-table"));
    }

    private static List<WebElement> getHeaders() {
        return getComponent().findElements(By.cssSelector(".oxd-table-header-cell"));
    }

    private static List<WebElement> getRows() {
        return getComponent().findElements(By.cssSelector(".oxd-table-row"));
    }

    private static List<WebElement> getCells() {
        return getComponent().findElements(By.cssSelector(".oxd-table-cell"));
    }

    private static WebElement getRowByIndex(int index) {
        return getRows().get(index);
    }
}
