package utils;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;

public class BaseTest {
    protected WebDriver driver;

    @Before
    public void setup() {
        driver = WebDriverFactory.createWebDriver();
        driver.manage().window().maximize();
        driver.get("https://example.com");
    }

    @After
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
