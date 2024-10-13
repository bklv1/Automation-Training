package tests;

import base.Navigate;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.ConfigReader;

public class AdminTest {

    @BeforeClass
    public void setUp() {
        LoginPage.loginWithCookie();
    }

    @Test
    public void searchForAdmin() {
//        LoginPage.loginWithCookie();
        Navigate.to(ConfigReader.getProperty("adminPage"));
    }
}

