package tests;

import base.DriverManager;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.DashboardPage;
import utils.ConfigReader;

public class LoginTest {

    @BeforeMethod
    public void setUp() {
        DriverManager.getDriver().get(ConfigReader.getProperty("loginPage"));
    }

    @AfterMethod
    public void tearDown() {
        DriverManager.quitDriver();
    }

    @Test
    public void testValidLogin() {
        LoginPage.login(ConfigReader.getProperty("username"), ConfigReader.getProperty("password"));
        assert DashboardPage.isDashboardDisplayed();
    }

}
