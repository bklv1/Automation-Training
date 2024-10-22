package pages;

import base.Base;
import pages.components.Input;
import pages.components.Button;
import org.openqa.selenium.By;

public class TimePage extends Base {

    private static final By employeeNameInput = By.xpath("//label[text()='Employee Name']/following::input[1]");
    private static final By viewButton = By.xpath("//button[text()='View']");

    public static void navigateToTime() {
        Navigate.to(ConfigReader.getProperty("timePage"));
    }

    public static void searchForEmployee(String employeeName) {
        waitForComponent("Employee Name", driver.findElement(employeeNameInput));
        Input.sendKeys("Employee Name", employeeName);
    }

    public static void clickViewButton() {
        waitForComponent("View Button", driver.findElement(viewButton));
        Button.clickButtonByText("View");
    }
}
