import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

public class OrdersPostTest extends OrdersPostHelper {

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site/api/";
    }

    @Test
    public void orderPostWithTokenTest() {
        Response response = loginSuccess();
        orderPostWithToken(response);
    }

    @Test
    public void orderPostWithoutTokenTest() {
        orderPostWithoutToken();
    }

    @Test
    public void orderPostWithoutIngredientsTest() {
        Response response = loginSuccess();
        orderPostWithoutIngredients(response);
    }

    @Test
    public void orderPostWithIncorrectHashTest() {
        Response response = loginSuccess();
        orderPostWithIncorrectHash(response);
    }
}
