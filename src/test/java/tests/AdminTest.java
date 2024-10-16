package tests;

import org.testng.annotations.Test;
import pages.AdminPage;
import pages.LoginPage;

public class AdminTest
    extends BaseTest {

    @Test
    public void searchForAdmin() {
        LoginPage.loginWithCookie(cookie);
        AdminPage.searchForAdmin();
    }

    @Test
    public void testResetButton() {
        LoginPage.loginWithCookie(cookie);
        AdminPage.navigateToAdminTab();
        AdminPage.searchForUsername("Admin");
        AdminPage.searchForUserRole("ESS");
        AdminPage.clickResetButton();
    }

}

