package contacts;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import dto.ContactDto;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class AddNewContactRest {
    String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Im5vYUBnbWFpbC5jb20ifQ.G_wfK7FRQLRTPu9bs2iDi2fcs69FHmW-0dTY4v8o5Eo";

    @BeforeMethod
    public void preCondition(){
        RestAssured.baseURI="https://contacts-telran.herokuapp.com";
        RestAssured.basePath="api";
    }
    @Test
    public void addNewContactSuccess()  {
        int i = (int) (System.currentTimeMillis() / 1000) % 3600;

        ContactDto contactDto = ContactDto.builder()
                .name("John")
                .lastName("Wick")
                .email("wick" + i + "@mail.com")
                .address("NY")
                .description("BF")
                .phone("121212" + i).build();

        ContactDto contact = given()
                .header("Authorization",token)
                .body(contactDto)
                .contentType(ContentType.JSON)
                .when()
                .post("contact")
                .then()
                .assertThat().statusCode(200)
                .extract()
                .as(ContactDto.class);

        Assert.assertEquals(contactDto.getName(),contact.getName());
        Assert.assertEquals(contactDto.getEmail(),contact.getEmail());
        Assert.assertNotEquals(contactDto.getId(),contact.getId());
    }
    @Test
    public void addNewContactSuccess2() {
        int i = (int) (System.currentTimeMillis() / 1000) % 3600;

        ContactDto contactDto = ContactDto.builder()
                .name("John")
                .lastName("Wick")
                .email("wick" + i + "@mail.com")
                .address("NY")
                .description("BF")
                .phone("121212" + i).build();

        int id=  given()
                .header("Authorization",token)
                .body(contactDto)
                .contentType(ContentType.JSON)
                .when()
                .post("contact")
                .then()
                .assertThat().statusCode(200)
                .assertThat().body("id",is(not(equalTo(contactDto.getId()))))
                .extract().path("id");

        System.out.println(id);

    }

}
