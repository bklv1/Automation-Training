import org.testng.annotations.Test;
import pages.LoginPage;
import pages.TimePage;
import tests.BaseTest;

public class TimeTest extends BaseTest {

    @Test
    public void searchAndViewEmployeeTimesheet() {
        LoginPage.loginWithCookie(cookie);
        TimePage.navigateToTime();
        TimePage.searchForEmployee("Jean Krishna User");
        TimePage.clickViewButton();
        TimePage.clickViewButton();
    }
}
