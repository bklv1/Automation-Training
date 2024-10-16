package pages;

import base.Base;
import base.Button;
import base.ConfigReader;
import base.Navigate;
import pages.components.Dropdown;
import pages.components.SearchableDropdown;

public class DirectoryPage extends Base {

    public static void searchDirectory(String employeeName, String jobTitle) {
        Navigate.to(ConfigReader.getProperty("directoryPage"));
        SearchableDropdown.selectFirstSearchValue("Employee Name", employeeName);
        Dropdown.selectDropdownValue("Job Title", jobTitle);
        Button.clickButtonByText("Search");
    }

    public static void verifySearchResults(String employeeName, String jobTitle) {
        // TODO: Implement verification logic
        // This method should check if the search results contain the expected employee name and job title
    }
}
