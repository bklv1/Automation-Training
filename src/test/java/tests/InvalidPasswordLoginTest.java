import org.junit.Test;
import pages.components.InputComponent;
import pages.LoginPage;

public class InvalidPasswordLoginTest {

    @Test
    public void testInvalidPasswordLogin() {
        LoginPage.login("username", "invalidpassword");
        // Add assertion to verify that login failed
    }
}
