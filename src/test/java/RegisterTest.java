import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

public class RegisterTest extends RegisterHelper {

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site/api/";
    }

    @Test
    public void registerNewUserTest() {
        Response response = registerNewUser();
        deleteNewUser(response);
    }

    @Test
    public void registerExistingUserTest() {
        registerExistingUser();
    }

    @Test
    public void registerNoUsernameTest() {
        registerNoUsername();
    }

}
