package tests;

import org.testng.annotations.Test;
import pages.LoginPage;
import pages.PIMPage;

public class PIMTest
    extends BaseTest {

    @Test
    public void searchEmployeeByEmploymentStatus() {
        LoginPage.loginWithCookie(cookie);
        PIMPage.navigateToPIM();
        PIMPage.searchByEmploymentStatus("Full-Time Contract");
        PIMPage.clickSearchButton();
        PIMPage.assertEmployeeDetails("0042", "Rebecca", "Harmony", "QA Engineer", "Full-Time Contract",
            "Quality Assurance");
    }
}
