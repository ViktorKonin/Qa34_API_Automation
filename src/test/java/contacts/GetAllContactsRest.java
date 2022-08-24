package contacts;

import com.jayway.restassured.RestAssured;
import dto.ContactDto;
import dto.GetAllContactsDto;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static com.jayway.restassured.RestAssured.given;

public class GetAllContactsRest {
    String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Im5vYUBnbWFpbC5jb20ifQ.G_wfK7FRQLRTPu9bs2iDi2fcs69FHmW-0dTY4v8o5Eo";

    @BeforeMethod
    public void preCondition() {
        RestAssured.baseURI = "https://contacts-telran.herokuapp.com";
        RestAssured.basePath = "api";
    }

    @Test
    public void getAllContactsSuccess(){
       GetAllContactsDto getAllContactsDto =  given()
                .header("Authorization",token)
                .when()
                .get("contact")
                .then()
                .assertThat().statusCode(200)
               .extract().as(GetAllContactsDto.class);
               // .extract().path("contacts");

       for(ContactDto contactDto:getAllContactsDto.getContacts()){
           System.out.println(contactDto.toString());
           System.out.println("================");
       }
    }
}
