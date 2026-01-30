package pages.components;

import base.Base;

import java.util.Arrays;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class Table
    extends Base {

    public static void assertFirstRowValue(List<String> expectedValues) {
        String rowText = getRowByIndex(1).getText();
        List<String> actualValues = Arrays.asList(rowText.split("\n"));
        if (!actualValues.equals(expectedValues)) {
            throw new AssertionError("Incorrect table row values!\nExpected: " + expectedValues + "\nActual: " + actualValues);
        }
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
