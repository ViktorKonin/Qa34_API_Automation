package contacts;

import com.google.gson.Gson;
import dto.AuthRequestDto;
import dto.AuthResponseDto;
import dto.ErrorDto;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class LoginTestsOkHttp {

    Gson gson = new Gson();
    public static final MediaType json = MediaType.get("application/json;charset=utf-8");
    OkHttpClient client = new OkHttpClient();


    @Test
    public void loginSuccess() throws IOException {
        AuthRequestDto auth = AuthRequestDto.builder().email("noa@gmail.com").password("Nnoa12345$").build();

        RequestBody requestBody = RequestBody.create(gson.toJson(auth), json);

        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/login")
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());
        Assert.assertEquals(response.code(), 200);

        AuthResponseDto responseDto = gson.fromJson(response.body().string(), AuthResponseDto.class);
        System.out.println(responseDto.getToken());
    }

    @Test
    public void unSuccessLoginWrongEmailWithoutAt() throws IOException {

        AuthRequestDto auth = AuthRequestDto.builder().email("noagmail.com").password("Nnoa12345$").build();
        RequestBody requestBody = RequestBody.create(gson.toJson(auth), json);
        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/login")
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        Assert.assertFalse(response.isSuccessful());
        Assert.assertEquals(response.code(), 400);
        ErrorDto errorDto = gson.fromJson(response.body().string(), ErrorDto.class);

        Assert.assertEquals(errorDto.getCode(), 400);
        Assert.assertEquals(errorDto.getMessage(), "Wrong email format! Example: name@mail.com");
        Assert.assertTrue(errorDto.getMessage().contains("Wrong email format!"));
    }

    @Test
    public void unSuccessLoginWrongPasswordWithoutSpecialSymbol() throws IOException {
        AuthRequestDto auth = AuthRequestDto.builder().email("noa@gmail.com").password("Nnoa12345").build();
        RequestBody requestBody = RequestBody.create(gson.toJson(auth), json);
        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/login")
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        Assert.assertFalse(response.isSuccessful());
        Assert.assertEquals(response.code(), 400);
        ErrorDto errorDto = gson.fromJson(response.body().string(), ErrorDto.class);

        Assert.assertEquals(errorDto.getCode(), 400);
        Assert.assertEquals(errorDto.getMessage(), "Password must contain at least one special symbol from ['$','~','-','_']!");
        Assert.assertTrue(errorDto.getMessage().contains("Password must contain at least one special symbol"));

    }

    @Test
    public void unSuccessLoginUnRegisteredUser() throws IOException {
        AuthRequestDto auth = AuthRequestDto.builder().email("kcp@gmail.com").password("Kcp12345$").build();

        RequestBody requestBody = RequestBody.create(gson.toJson(auth), json);
        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/login")
                .post(requestBody)
                .build();
        Response response = client.newCall(request).execute();
        Assert.assertFalse(response.isSuccessful());

        ErrorDto errorDto = gson.fromJson(response.body().string(), ErrorDto.class);
        Assert.assertEquals(errorDto.getMessage(), "Wrong email or password!");


        Assert.assertEquals(response.code(), 401);

    }

    @Test
    public void loginWithWrongPasswordLength() throws IOException {
        AuthRequestDto auth = AuthRequestDto.builder().email("noa@gmail.com").password("Nnoa").build();

        RequestBody requestBody = RequestBody.create(gson.toJson(auth), json);
        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/login")
                .post(requestBody)
                .build();
        Response response = client.newCall(request).execute();
        Assert.assertFalse(response.isSuccessful());
        Assert.assertEquals(response.code(), 400);
        ErrorDto errorDto = gson.fromJson(response.body().string(), ErrorDto.class);

        Assert.assertEquals(errorDto.getCode(), 400);
        Assert.assertEquals(errorDto.getMessage(), "Password length need be 8 or more symbols");
        Assert.assertTrue(errorDto.getMessage().contains(" length need be 8 or more"));

    }


}
