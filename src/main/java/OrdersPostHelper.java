import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class OrdersPostHelper extends LoginHelper {

    @Step("Создание заказа")
    public void orderPostWithToken(Response response) {
        String token = response.getBody().path("accessToken");

        String json = "{\n" + "\"ingredients\": [\"61c0c5a71d1f82001bdaaa6c\",\"61c0c5a71d1f82001bdaaa6c\"]\n" + "}";

        response = given()
                .header("Content-type", "application/json")
                .header("authorization", token)
                .and()
                .body(json)
                .when()
                .post("orders");
        response.then().assertThat().statusCode(200)
                .and().body("name", equalTo("Краторный бургер"))
                .and().body("order.number", notNullValue())
                .and().body("success", equalTo(true));
    }

    @Step("Создание заказа без токена")
    public void orderPostWithoutToken() {
        String json = "{\n" + "\"ingredients\": [\"61c0c5a71d1f82001bdaaa6c\",\"61c0c5a71d1f82001bdaaa6c\"]\n" + "}";

        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(json)
                        .when()
                        .post("orders");
        response.then().assertThat().statusCode(200)
                .and().body("name", equalTo("Краторный бургер"))
                .and().body("order.number", notNullValue())
                .and().body("success", equalTo(true));
    }

    @Step("Создание заказа без ингредиентов")
    public void orderPostWithoutIngredients(Response response) {
        String token = response.getBody().path("accessToken");

        String json = "{\n" + "\"ingredients\": []\n" + "}";

        response = given()
                .header("Content-type", "application/json")
                .header("authorization", token)
                .and()
                .body(json)
                .when()
                .post("orders");
        response.then().assertThat().statusCode(400)
                .and().body("success", equalTo(false))
                .and().body("message", equalTo("Ingredient ids must be provided"));
    }

    @Step("Создание заказа с неверным хешем ингредиентов")
    public void orderPostWithIncorrectHash(Response response) {
        String token = response.getBody().path("accessToken");

        String json = "{\n" + "\"ingredients\": [\"qwerty\",\"qwerty\"]\n" + "}";

        response = given()
                .header("Content-type", "application/json")
                .header("authorization", token)
                .and()
                .body(json)
                .when()
                .post("orders");
        response.then().assertThat().statusCode(500);
    }
}
