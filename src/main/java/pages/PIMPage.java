package pages;

import base.Base;
import base.Navigate;
import base.ConfigReader;
import pages.components.Dropdown;
import pages.components.Button;
import pages.components.Table;

import java.util.Arrays;
import java.util.List;

public class PIMPage extends Base {

    public static void navigateToPIM() {
        Navigate.to(ConfigReader.getProperty("pimPage"));
    }

    public static void searchByEmploymentStatus(String status) {
        Dropdown.selectDropdownValue("Employment Status", status);
    }

    public static void clickSearchButton() {
        Button.clickSubmitButton();
    }

    public static void assertEmployeeDetails(String id, String firstName, String lastName, String jobTitle, String employmentStatus, String subUnit) {
        List<String> expectedValues = Arrays.asList(id, firstName, lastName, jobTitle, employmentStatus, subUnit);
        Table.assertFirstRowValue(expectedValues);
    }
}
