import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class Locators {

    private WebDriver driver;
    private String username = "standard_user";
    private String password = "secret_sauce";

    @BeforeMethod
    private void initiateDriver() {
        System.setProperty("webdriver.chrome.driver", "C:/Program Files/chromedriver/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.get("https://www.saucedemo.com/");
    }

    @AfterMethod
    private void quitDriver() {
        driver.quit();
    }

    //First test will navigate to QA and find and click the "Remember me" checbox in three different ways
    @Test
    public void findLocatorsByID() {

        //Example of the three ways to use ID locator to find elements

        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.cssSelector("#password")).sendKeys("secret_sauce");
        driver.findElement(By.xpath("//*[@id=\"login-button\"]")).click();

        //QUESTION: Which is the fastest and slowest way to find the checkbox?
    }

    @Test
    public void findLocatorByName(){

        driver.findElement(By.name("user-name")).sendKeys(username);
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.name("login-button")).click();

    }

    @Test
    public void findLocatorByLink(){
        login(username, password);

        driver.findElement(By.partialLinkText("Bolt T-Shirt")).click();
    }


    @Test
    public void findLocatorByClass(){
        login(username, password);
        driver.findElement(By.className("bm-burger-button")).click();
//      driver.findElement(By.cssSelector(".bm-burger-button")).click(); //The dot is a sign for class
    }

    @Test
    public void locatorsChaining(){
        login(username,password);

        WebElement parentElement = driver.findElement(By.cssSelector("[data-test=product-sort-container]"));

        List<WebElement> dropdownOptions = parentElement.findElements(By.tagName("option"));
        dropdownOptions.getLast().click();
    }

    @Test
    public void testTest(){
        login(username, password);

        driver.findElement(By.xpath("/html/body/div/div/div/div[1]/div[1]/div[1]/div/div[1]/div")).click();
    }

    private void login(String username, String pasword){
        driver.findElement(By.id("user-name")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(pasword);
        driver.findElement(By.id("login-button")).click();
    }
}
