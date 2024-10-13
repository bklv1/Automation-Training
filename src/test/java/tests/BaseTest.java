package tests;

import base.DriverManager;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import pages.LoginPage;

public class BaseTest {

    protected String cookie = "";

    @BeforeClass
    public void setUp() {
        cookie = LoginPage.retrieveCookie();
    }

    @AfterClass
    public void tearDown() {
        DriverManager.quitDriver();
    }
}
