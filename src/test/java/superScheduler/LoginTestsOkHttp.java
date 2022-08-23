package superScheduler;

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
    public static final MediaType JSON = MediaType.get("application/json;charset=utf-8");
    OkHttpClient client = new OkHttpClient();

    @Test
    public void loginSuccess() throws IOException {
        AuthRequestDto auth = AuthRequestDto.builder().email("noa@gmail.com").password("Nnoa12345$").build();

        RequestBody requestBody = RequestBody.create(gson.toJson(auth), JSON);

        Request request = new Request.Builder()
                .url("https://super-scheduler-app.herokuapp.com/api/login")
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());
        Assert.assertEquals(response.code(), 200);
        AuthResponseDto responseDto = gson.fromJson(response.body().string(), AuthResponseDto.class);
        System.out.println(responseDto.getToken());
    }

    @Test(enabled = false)
    public void unSuccessLoginWrongEmailWithoutAt() throws IOException {
        AuthRequestDto auth = AuthRequestDto.builder().email("noagmail.com").password("Nnoa12345$").build();

        RequestBody requestBody = RequestBody.create(gson.toJson(auth), JSON);

        Request request = new Request.Builder()
                .url("https://super-scheduler-app.herokuapp.com/api/login")
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        ErrorDto errorDto = gson.fromJson(response.body().string(), ErrorDto.class);
        Assert.assertEquals(response.code(), 401);//bug
        Assert.assertTrue(errorDto.getMessage().contains("Wrong email or password")); //bug
    }

    @Test
    public void unSuccessLoginWrongPasswordWithoutSpecialSymbol() throws IOException {
        AuthRequestDto auth = AuthRequestDto.builder().email("noa@gmail.com").password("Nnoa12345").build();

        RequestBody requestBody = RequestBody.create(gson.toJson(auth), JSON);

        Request request = new Request.Builder()
                .url("https://super-scheduler-app.herokuapp.com/api/login")
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        ErrorDto errorDto = gson.fromJson(response.body().string(), ErrorDto.class);
        Assert.assertEquals(response.code(), 401);
        System.out.println(response.code());
        Assert.assertTrue(errorDto.getMessage().contains("Wrong email or password"));
        System.out.println(errorDto.getMessage());
    }
}
