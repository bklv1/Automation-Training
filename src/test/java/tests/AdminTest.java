package tests;

import org.testng.annotations.Test;
import pages.AdminPage;
import pages.LoginPage;
import pages.components.Table;

import java.util.Arrays;

public class AdminTest
    extends BaseTest {

    @Test
    public void searchForAdmin() {
        LoginPage.loginWithCookie(cookie);
        AdminPage.searchForAdmin();
        Table.assertFirstRowValue(Arrays.asList("Admin", "Admin", "manda user", "Enabled"));
    }

    @Test
    public void searchForAdminAndReset() {
        LoginPage.loginWithCookie(cookie);
        AdminPage.searchForAdminESS();
        AdminPage.clickResetButton();
    }

}
