package pages;

import base.Base;
import pages.components.Button;
import base.ConfigReader;
import base.Navigate;
import pages.components.Dropdown;
import pages.components.Input;

public class AdminPage extends Base {

    public static void searchForAdmin(){
        Navigate.to(ConfigReader.getProperty("adminPage"));
        Input.sendKeys("Username", "Admin");
        Dropdown.selectDropdownValue("User Role", "Admin");
        Dropdown.selectDropdownValue("Status", "Enabled");
        Button.clickSubmitButton();
    }

    public static void searchForAdminESS() {
        Navigate.to(ConfigReader.getProperty("adminPage"));
        Input.sendKeys("Username", "Admin");
        Dropdown.selectDropdownValue("User Role", "ESS");
    }

    public static void clickResetButton() {
        Button.clickButtonByText("Reset");
    }
}
package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AdminPage {

    @FindBy(xpath = "//span[text()='Admin']")
    private WebElement adminMenu;

    @FindBy(xpath = "//label[text()='Username']/following::input[1]")
    private WebElement usernameInput;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement searchButton;

    public AdminPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void navigateToAdminSection() {
        adminMenu.click();
    }

    public void setUsername(String username) {
        usernameInput.sendKeys(username);
    }

    public void clickSearch() {
        searchButton.click();
    }
}
