package pages.components;

import base.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HeaderComponent {
    private static WebDriver driver = DriverManager.getDriver();

    private static By welcomeMessage = By.id("welcome");

    public static String getWelcomeMessage() {
        return driver.findElement(welcomeMessage).getText();
    }
}
