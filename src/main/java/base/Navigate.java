package base;

import org.openqa.selenium.WebDriver;

public class Navigate {
    private static WebDriver driver = DriverManager.getDriver();

    public static void to(String url){
        driver.navigate().to(url);
    }
}
