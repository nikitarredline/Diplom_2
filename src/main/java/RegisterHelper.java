import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

public class RegisterHelper {

    @Step("Регистрация нового пользователя")
    public Response registerNewUser() {
        String email = RandomStringUtils.randomAlphabetic(10) + AuthHelper.EMAIL_POSTFIX;
        String password = RandomStringUtils.randomAlphabetic(10);
        String name = RandomStringUtils.randomAlphabetic(10);

        AuthHelper authHelper = new AuthHelper(email, password, name);
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(authHelper)
                        .when()
                        .post("auth/register");
        response.then().assertThat().statusCode(200)
                .and().body("success", equalTo(true))
                .and().body("user.email", equalTo(email.toLowerCase()))
                .and().body("user.name", equalTo(name))
                .and().body("accessToken", startsWith("Bearer "))
                .and().body("refreshToken", notNullValue());

        return response;
    }

    @Step("Удаление нового пользователя")
    public void deleteNewUser(Response response) {
        String token = response.getBody().path("accessToken");
        response = given()
                .header("Content-type", "application/json")
                .auth().oauth2(token.substring(7))
                .when()
                .delete("auth/user");
        response.then().assertThat().statusCode(202);
    }

    @Step("Регистрация существующего пользователя")
    public void registerExistingUser() {
        String email = "test-data" + AuthHelper.EMAIL_POSTFIX;
        String password = "password";
        String name = "Username";

        AuthHelper authHelper = new AuthHelper(email, password, name);
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(authHelper)
                        .when()
                        .post("auth/register");
        response.then().assertThat().statusCode(403)
                .and().body("success", equalTo(false))
                .and().body("message", equalTo("User already exists"));
    }

    @Step("Регистрация пользователя без имени")
    public void registerNoUsername() {
        String email = "test-data" + AuthHelper.EMAIL_POSTFIX;
        String password = "password";
        String name = "";

        AuthHelper authHelper = new AuthHelper(email, password, name);
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(authHelper)
                        .when()
                        .post("auth/register");
        response.then().assertThat().statusCode(403)
                .and().body("success", equalTo(false))
                .and().body("message", equalTo("Email, password and name are required fields"));
    }
}
