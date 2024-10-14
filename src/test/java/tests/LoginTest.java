package tests;

import org.testng.annotations.Test;
import pages.LoginPage;
import base.ConfigReader;

public class LoginTest
    extends BaseTest {

    @Test
    public void testValidLogin() {
        LoginPage.login(ConfigReader.getProperty("username"), ConfigReader.getProperty("password"));
    }

}
