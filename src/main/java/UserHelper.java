import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class UserHelper extends LoginHelper {

    @Step("Изменение данных пользователя")
    public void userWithToken(Response response) {
        String email = "test-data" + AuthHelper.EMAIL_POSTFIX;
        String password = "password";
        String name = "Username";
        String newEmail = RandomStringUtils.randomAlphabetic(10) + AuthHelper.EMAIL_POSTFIX;
        String newName = RandomStringUtils.randomAlphabetic(10);

        String token = response.getBody().path("accessToken");

        AuthHelper authHelper = new AuthHelper(newEmail, password, newName);
        response = given()
                .header("Content-type", "application/json")
                .header("authorization", token)
                .and()
                .body(authHelper)
                .when()
                .patch("auth/user");
        response.then().assertThat().statusCode(200)
                .and().body("success", equalTo(true))
                .and().body("user.email", equalTo(newEmail.toLowerCase()))
                .and().body("user.name", equalTo(newName));

        authHelper = new AuthHelper(email, password, name);
        response = given()
                .header("Content-type", "application/json")
                .header("authorization", token)
                .and()
                .body(authHelper)
                .when()
                .patch("auth/user");
        response.then().assertThat().statusCode(200)
                .and().body("success", equalTo(true))
                .and().body("user.email", equalTo(email.toLowerCase()))
                .and().body("user.name", equalTo(name));
    }

    @Step("Изменение данных пользователя без токена")
    public void userWithoutToken() {
        String email = "test-data" + AuthHelper.EMAIL_POSTFIX;
        String name = "Username";

        AuthHelper authHelper = new AuthHelper(email, name);
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(authHelper)
                        .when()
                        .patch("auth/user");
        response.then().assertThat().statusCode(401)
                .and().body("success", equalTo(false))
                .and().body("message", equalTo("You should be authorised"));
    }
}
