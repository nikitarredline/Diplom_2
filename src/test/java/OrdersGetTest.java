import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class OrdersGetTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site/api/";
    }

    @Test
    public void orderGetWithTokenTest() {
        String email = "test-data" + AuthHelper.EMAIL_POSTFIX;
        String password = "password";

        AuthHelper authHelper = new AuthHelper(email, password);
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(authHelper)
                        .when()
                        .post("auth/login");
        response.then().assertThat().statusCode(200);

        String token = response.getBody().path("accessToken");

        String json = "{\n" + "\"ingredients\": [\"61c0c5a71d1f82001bdaaa6c\",\"61c0c5a71d1f82001bdaaa6c\"]\n" + "}";

        response = given()
                .header("Content-type", "application/json")
                .header("authorization", token)
                .and()
                .body(json)
                .when()
                .get("orders");
        response.then().assertThat().statusCode(200)
                .and().body("success", equalTo(true))
                .and().body("total", notNullValue())
                .and().body("totalToday", notNullValue());
    }

    @Test
    public void orderGetWithoutTokenTest() {
        String json = "{\n" + "\"ingredients\": [\"61c0c5a71d1f82001bdaaa6c\",\"61c0c5a71d1f82001bdaaa6c\"]\n" + "}";

        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .get("orders");
        response.then().assertThat().statusCode(401)
                .and().body("success", equalTo(false))
                .and().body("message", equalTo("You should be authorised"));
    }
}
