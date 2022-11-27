package restassured;

import com.jayway.restassured.RestAssured;
import dto.AuthReqDto;
import dto.AuthRespDto;
import dto.ErrorDto;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class LoginTestsRA {

    @BeforeMethod
    public void setUp() {
        RestAssured.baseURI = "https://contactapp-telran-backend.herokuapp.com";
        RestAssured.basePath = "v1";
    }

    @Test
    public void loginSuccess() {
        AuthReqDto auth = AuthReqDto.builder()
                .username("sonya@gmail.com")
                .password("Ssonya12345$")
                .build();


        AuthRespDto respDto = given()
                .body(auth)
                .contentType("application/json")
                .when()
                .post("user/login/usernamepassword")
                .then()
                .assertThat().statusCode(200)
                .extract()
                .response().as(AuthRespDto.class);
        System.out.println(respDto.getToken());
    }

    @Test
    public void loginWrongEmail() {
        AuthReqDto auth = AuthReqDto.builder()
                .username("sonygmail.com")
                .password("Ssonya12345$")
                .build();


        ErrorDto respDto = given()
                .body(auth)
                .contentType("application/json")
                .when()
                .post("user/login/usernamepassword")
                .then()
                .assertThat().statusCode(401)
                .extract()
                .response().as(ErrorDto.class);

        Object message = respDto.getMessage();
        Assert.assertEquals(message, "Login or Password incorrect");
        Assert.assertEquals(respDto.getStatus(), 401);
    }

    @Test
    public void loginWrongEmail2() {
        AuthReqDto auth = AuthReqDto.builder()
                .username("sonygmail.com")
                .password("Ssonya12345$")
                .build();


        given()
                .body(auth)
                .contentType("application/json")
                .when()
                .post("user/login/usernamepassword")
                .then()
                .assertThat().statusCode(401)
                .assertThat().body("message",containsString("Login or Password incorrect"))
                .assertThat().body("status",equalTo(401));



    }
}


