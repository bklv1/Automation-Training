package tests;

import org.testng.annotations.Test;
import pages.AdminPage;
import pages.DirectoryPage;
import pages.LoginPage;

public class AdminTest extends BaseTest {

    @Test
    public void searchForAdmin() {
        LoginPage.loginWithCookie(cookie);
        AdminPage.searchForAdmin();
    }

    @Test
    public void searchForAdminAndReset() {
        LoginPage.loginWithCookie(cookie);
        AdminPage.searchForAdminESS();
        AdminPage.clickResetButton();
    }

    @Test
    public void searchDirectoryForEmployee() {
        LoginPage.loginWithCookie(cookie);
        DirectoryPage.searchDirectory("Amelia Brown", "QA Lead");
    }
}

