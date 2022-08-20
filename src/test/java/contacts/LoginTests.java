package contacts;

import com.google.gson.Gson;
import dto.AuthRequestDto;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class LoginTests {

    Gson gson = new Gson();
    public static final MediaType json = MediaType.get("application/json;charset=utf-8");
    OkHttpClient client = new OkHttpClient();

    @Test
    public void loginSuccess() throws IOException {
        AuthRequestDto auth = AuthRequestDto.builder().email("noa@gmail.com").password("Nnoa12345$").build();

        RequestBody requestBody = RequestBody.create(gson.toJson(auth),json);

        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/login")
                .post(requestBody)
                .build();

       Response response = client.newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());
        Assert.assertEquals(response.code(),200);
    }

    @Test
    public void unSuccessLoginWrongEmailWithoutAt() throws IOException {
        AuthRequestDto auth = AuthRequestDto.builder().email("noagmail.com").password("Nnoa12345$").build();
        RequestBody requestBody = RequestBody.create(gson.toJson(auth),json);
        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/login")
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        Assert.assertFalse(response.isSuccessful());
        Assert.assertEquals(response.code(),400);
    }

    @Test
    public void unSuccessLoginWrongPasswordWithoutSpecialSymbol() throws IOException {
        AuthRequestDto auth = AuthRequestDto.builder().email("noa@gmail.com").password("Nnoa12345").build();
        RequestBody requestBody = RequestBody.create(gson.toJson(auth),json);
        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/login")
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        Assert.assertFalse(response.isSuccessful());
        Assert.assertEquals(response.code(),400);
    }

    @Test
    public void unSuccessLoginUnRegisteredUser() throws IOException {
        AuthRequestDto auth = AuthRequestDto.builder().email("troy991@gmail.com").password("Ttroy12345$").build();
        RequestBody requestBody = RequestBody.create(gson.toJson(auth),json);
        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/login")
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        Assert.assertFalse(response.isSuccessful());
       // Assert.assertEquals(response.code(),401);
    }
}
