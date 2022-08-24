package contacts;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import dto.AuthRequestDto;
import dto.AuthResponseDto;
import dto.ErrorDto;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

public class LoginTestsRest {
    @BeforeMethod
    public void preCondition(){
        RestAssured.baseURI="https://contacts-telran.herokuapp.com";
        RestAssured.basePath="api";
    }

    @Test
    public void loginSuccess(){
        AuthRequestDto auth = AuthRequestDto.builder().email("noa@gmail.com").password("Nnoa12345$").build();

        AuthResponseDto responseDto =given()
                .body(auth)
                .contentType("application/json")
                .when()
                .post("login")
                .then()
                .assertThat().statusCode(200)
                .extract()
                .as(AuthResponseDto.class);
        System.out.println(responseDto.getToken());
    }
    @Test
    public void loginWrongEmail() {
        AuthRequestDto auth = AuthRequestDto.builder().email("noagmail.com").password("Nnoa12345$").build();

        ErrorDto errorDto = given()
                .body(auth)
                .contentType(ContentType.JSON)
                .when()
                .post("login")
                .then()
                .assertThat().statusCode(400)
                .extract()
                .as(ErrorDto.class);
        Assert.assertEquals(errorDto.getMessage(),"Wrong email format! Example: name@mail.com");
        Assert.assertTrue(errorDto.getMessage().contains("Wrong email format!"));


    }

    @Test
    public void loginWrongEmailCheck() {
        AuthRequestDto auth = AuthRequestDto.builder().email("noagmail.com").password("Nnoa12345$").build();

        given()
                .body(auth)
                .contentType(ContentType.JSON)
                .when()
                .post("login")
                .then()
                .assertThat().statusCode(400)
                .assertThat().body("message",containsString("Wrong email format! Example: name@mail.com"))
                .assertThat().body("details",containsString("uri=/api/login"));


    }

}
