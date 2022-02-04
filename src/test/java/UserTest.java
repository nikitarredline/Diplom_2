import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

public class UserTest extends UserHelper {

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site/api/";
    }

    @Test
    public void userWithTokenTest() {
        Response response = loginSuccess();
        userWithToken(response);
    }

    @Test
    public void userWithoutTokenTest() {
        userWithoutToken();
    }

}
