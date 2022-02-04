import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

public class OrdersGetTest extends OrdersGetHelper {

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site/api/";
    }

    @Test
    public void orderGetWithTokenTest() {
        Response response = loginSuccess();
        orderGetWithToken(response);
    }

    @Test
    public void orderGetWithoutTokenTest() {
        orderGetWithoutToken();
    }
}
