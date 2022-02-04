import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class OrdersGetHelper extends LoginHelper {

    @Step("Получение заказов")
    public void orderGetWithToken(Response response) {
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

    @Step("Получение заказов без токена")
    public void orderGetWithoutToken() {
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
