package tests;

import base.Navigate;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.ConfigReader;

public class AdminTest
    extends BaseTest {

    @Test
    public void searchForAdmin() {
        LoginPage.loginWithCookie(cookie);
        Navigate.to(ConfigReader.getProperty("adminPage"));
    }

}

