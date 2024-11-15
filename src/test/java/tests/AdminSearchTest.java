package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import base.TestBase;
import pages.LoginPage;
import pages.AdminPage;

public class AdminSearchTest extends TestBase {

    @Test
    public void testAdminSearch() {
        // 1. Login into the application
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("Admin", "admin123");

        // 2. Navigate to Admin section
        AdminPage adminPage = new AdminPage(driver);
        adminPage.navigateToAdminSection();

        // 3. Search for Username = 'Admin'
        adminPage.setUsername("Admin");

        // 4. Click on 'Search'
        adminPage.clickSearch();

        // 5. Assert the values of the first row
        WebElement firstRow = driver.findElement(By.xpath("//div[@class='oxd-table-body']/div[1]/div"));
        String username = firstRow.findElement(By.xpath(".//div[2]/div")).getText();
        String userRole = firstRow.findElement(By.xpath(".//div[3]/div")).getText();
        String employeeName = firstRow.findElement(By.xpath(".//div[4]/div")).getText();
        String status = firstRow.findElement(By.xpath(".//div[5]/div")).getText();

        Assert.assertEquals(username, "Admin", "Username does not match");
        Assert.assertEquals(userRole, "Admin", "User Role does not match");
        Assert.assertFalse(employeeName.isEmpty(), "Employee Name is empty");
        Assert.assertEquals(status, "Enabled", "Status does not match");
    }
}
