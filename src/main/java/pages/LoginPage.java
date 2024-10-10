package pages;

import base.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    private static WebDriver driver = DriverManager.getDriver();

    private static By usernameField = By.name("username");
    private static By passwordField = By.name("password");
    private static By loginButton = By.cssSelector(".orangehrm-login-button");

    public static void login(String username, String password) {
        driver.findElement(usernameField).sendKeys(username);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(loginButton).click();
    }
}
