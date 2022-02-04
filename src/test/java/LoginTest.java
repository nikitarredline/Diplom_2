import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;

public class LoginTest extends LoginHelper {

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site/api/";
    }

    @Test
    public void loginSuccessTest() {
        loginSuccess();
    }

    @Test
    public void loginNotCorrectLoginPasswordTest() {
        loginNotCorrectLoginPassword();
    }

}
