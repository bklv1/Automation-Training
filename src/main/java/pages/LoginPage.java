package pages;

import base.Base;
import base.Navigate;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import base.ConfigReader;

public class LoginPage extends Base {

    private static By usernameField = By.name("username");

    private static By passwordField = By.name("password");

    private static By loginButton = By.cssSelector(".orangehrm-login-button");

    public static void login(String username, String password) {
        Navigate.to(ConfigReader.getProperty("loginPage"));
        driver.findElement(usernameField)
            .sendKeys(username);
        driver.findElement(passwordField)
            .sendKeys(password);
        driver.findElement(loginButton)
            .click();
    }

    public static void login() {
        login(ConfigReader.getProperty("username"), ConfigReader.getProperty("password"));
    }

    public static void loginWithCookie(String cookie) {
        driver.manage()
            .addCookie(new Cookie("orangehrm", cookie));
    }

    public static String retrieveCookie() {

        login(ConfigReader.getProperty("username"), ConfigReader.getProperty("password"));
        return driver.manage()
            .getCookieNamed("orangehrm")
            .getValue();
    }
    
}
