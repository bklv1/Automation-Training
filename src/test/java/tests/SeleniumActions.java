package tests;

import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.Set;

public class SeleniumActions {

    private WebDriver driver;

//    @Test
//    public void uploadImage() {
//        // There are far better ways to give a file location. Absolute path is not a good practice.
//        // Please bear in mind this is just to show the example of the file upload and not make things more complicated.
//        driver.findElement(By.id("imagesrc")).sendKeys("C:/Users/tsvetomir.banovski/Desktop/MCO Training/example-image.png");
//    }
//
//    @Test
//    public void alertHandling() {
//        driver.navigate()
//            .to("https://demo.automationtesting.in/Alerts.html");
//        driver.findElement(By.cssSelector("a[href=\"#Textbox\"]")).click();
//        driver.findElement(By.cssSelector(".btn-info")).click();
//
//        //ALERT SWITCHING
//        Alert alertFirst = driver.switchTo()
//            .alert();
//        alertFirst.sendKeys("MCO");
//        alertFirst.accept();
//
//        // SO YOU HAVE TO ASSERT THE MESSAGE THAT COMES FROM THE PAGE
//        String actualResult = driver.findElement(By.id("demo1"))
//            .getText();
//        Assert.assertEquals(actualResult, "Hello MCO How are you today");
//    }
//
//    @Test
//    public void windowsHandles(){
//        driver.navigate()
//            .to("https://demo.automationtesting.in/Windows.html");
//
//        //working with a tab in the same chrome window
//        driver.findElement(By.cssSelector("a[href=\"#Tabbed\"]")).click();
//        driver.findElement(By.cssSelector(".btn-info")).click();
//
//        String mainWindowHandle = driver.getWindowHandle();
//        Set<String> allWindowHandles = driver.getWindowHandles();
//
//        driver.switchTo().window(allWindowHandles.stream().toList().getFirst());
//        driver.switchTo().window(allWindowHandles.stream().toList().getLast());
//
//        driver.switchTo().window(mainWindowHandle);
//        //working with a tab in a separate chrome window
//        driver.findElement(By.cssSelector("a[href=\"#Seperate\"]")).click();
//        driver.findElement(By.cssSelector(".btn-primary")).click();
//
//        allWindowHandles = driver.getWindowHandles();
//        driver.switchTo().window(mainWindowHandle);
//        driver.switchTo().window(allWindowHandles.stream().toList().getLast());
//
//        //If you want to switch to the second window (the added tab inside the 1st window), you can loop through them
//        //and stop to the second and many other approaches. Also when opening it, you can get the returned value and store it
//
//
//
//    }
//
//    @Test
//    public void frameHandling(){
//        driver.navigate().to("https://demo.automationtesting.in/Frames.html");
//
////        driver.switchTo().frame("singleframe");
//
//        driver.findElements(By.cssSelector("input[type=text]")).getFirst().sendKeys("Ceco loves Microsoft");
//    }
//
//    @Test
//    public void mouseHover(){
////        List<WebElement> allDropdownOptions = driver.findElements(By.cssSelector("li.dropdown"));
//        Actions actions = new Actions(driver);
//
//        WebElement a = driver.findElement(By.xpath("//*[@id=\"header\"]/nav/div/div[2]/ul/li[4]/a"));
//        actions.moveToElement(a).perform();
//
//        driver.switchTo().defaultContent();
//    }

}
