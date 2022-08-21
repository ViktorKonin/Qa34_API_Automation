package contacts;

import com.google.gson.Gson;
import dto.ContactDto;
import dto.ResponseDeleteById;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

//id=3835 ,3731
public class DeleteByIdOkHttp {
    Gson gson = new Gson();
    public static final MediaType JSON = MediaType.get("application/json;charset=utf-8");
    OkHttpClient client = new OkHttpClient();
    String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Im5vYUBnbWFpbC5jb20ifQ.G_wfK7FRQLRTPu9bs2iDi2fcs69FHmW-0dTY4v8o5Eo";
    int id;

    @BeforeMethod
    public void preCondition() throws IOException {
        int i = (int) (System.currentTimeMillis() / 1000) % 3600;

        ContactDto contactDto = ContactDto.builder()
                .name("John")
                .lastName("Wick")
                .email("wick" + i + "@mail.com")
                .address("NY")
                .description("BF")
                .phone("121212" + i).build();
        RequestBody body = RequestBody.create(gson.toJson(contactDto), JSON);
        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/contact")
                .post(body)
                .addHeader("Authorization", token)
                .build();
        Response response = client.newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());
        Assert.assertEquals(response.code(), 200);

        ContactDto contact = gson.fromJson(response.body().string(), ContactDto.class);
        id = contact.getId();
    }
@Test
    public void deleteByIdSuccess() throws IOException {

        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/contact/3731")
                .delete()
                .addHeader("Authorization",token)
                .build();

        Response response = client.newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());
        Assert.assertEquals(response.code(),200);

    ResponseDeleteById responseDeleteById = gson.fromJson(response.body().string(),ResponseDeleteById.class);
    Assert.assertEquals(responseDeleteById.getStatus(),"Contact was deleted!");
    }
}
