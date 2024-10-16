package pages;

import base.Base;
import base.Button;
import base.ConfigReader;
import base.Navigate;
import pages.components.Dropdown;
import pages.components.Input;
import pages.components.SearchableDropdown;

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
