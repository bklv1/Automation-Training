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
package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    @FindBy(name = "username")
    private WebElement usernameInput;

    @FindBy(name = "password")
    private WebElement passwordInput;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement loginButton;

    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void login(String username, String password) {
        usernameInput.sendKeys(username);
        passwordInput.sendKeys(password);
        loginButton.click();
    }
}
