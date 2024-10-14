package base;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public abstract class Base {

    protected static WebDriver driver = DriverManager.getDriver();

    protected static JavascriptExecutor javascriptExecutor;
}
