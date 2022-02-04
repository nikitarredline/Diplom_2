import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

public class LoginHelper {

    @Step("Успешная авторизация")
    public Response loginSuccess() {
        String email = "test-data" + AuthHelper.EMAIL_POSTFIX;
        String password = "password";
        String name = "Username";

        AuthHelper authHelper = new AuthHelper(email, password);
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(authHelper)
                        .when()
                        .post("auth/login");
        response.then().assertThat().statusCode(200)
                .and().body("success", equalTo(true))
                .and().body("user.email", equalTo(email.toLowerCase()))
                .and().body("user.name", equalTo(name))
                .and().body("accessToken", startsWith("Bearer "))
                .and().body("refreshToken", notNullValue());
        return response;
    }

    @Step("Авторизация с некорректным логином и паролем")
    public void loginNotCorrectLoginPassword() {
        String email = "qwerty" + AuthHelper.EMAIL_POSTFIX;
        String password = "qwerty";

        AuthHelper authHelper = new AuthHelper(email, password);
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(authHelper)
                        .when()
                        .post("auth/login");
        response.then().assertThat().statusCode(401)
                .and().body("success", equalTo(false))
                .and().body("message", equalTo("email or password are incorrect"));
    }
}
