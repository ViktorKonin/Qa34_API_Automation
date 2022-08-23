package superScheduler;

import com.google.gson.Gson;
import dto.ErrorDto;
import dtoSuper.DateDto;
import dtoSuper.RecordDto;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class AddRecordTests {
    Gson gson = new Gson();
    public static final MediaType JSON = MediaType.get("application/json;charset=utf-8");
    OkHttpClient client = new OkHttpClient();
    String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Im5vYUBnbWFpbC5jb20ifQ.G_wfK7FRQLRTPu9bs2iDi2fcs69FHmW-0dTY4v8o5Eo";

    @Test
    public void addRecordSuccess() throws IOException {

        RecordDto recordDto = RecordDto.builder()
                .breaks(2)
                .currency("Currency")
                .date(DateDto.builder().dayOfMonth(1).dayOfWeek("2").month(1).year(2020).build())
                .hours(4)
                .timeFrom("18:00")
                .timeTo("21:00")
                .title("Title")
                .type("Type")
                .totalSalary(500)
                .wage(50)
                .build();
        RequestBody body = RequestBody.create(gson.toJson(recordDto), JSON);
        Request request = new Request.Builder()
                .url("https://super-scheduler-app.herokuapp.com/api/record")
                .post(body)
                .addHeader("Authorization", token)
                .build();
        Response response = client.newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());
        Assert.assertEquals(response.code(), 200);

        RecordDto record = gson.fromJson(response.body().string(), RecordDto.class);
        System.out.println(record.getId());
    }

    @Test
    public void addRecordUnSuccessWrongToken() throws IOException {
        RecordDto recordDto = RecordDto.builder()
                .breaks(2)
                .currency("Currency")
                .date(DateDto.builder().dayOfMonth(31).dayOfWeek("First").month(9).year(2019).build())
                .hours(2)
                .timeFrom("18:00")
                .timeTo("21:00")
                .title("Title")
                .type("Type")
                .totalSalary(500)
                .wage(50)
                .build();

        RequestBody body = RequestBody.create(gson.toJson(recordDto), JSON);
        Request request = new Request.Builder()
                .url("https://super-scheduler-app.herokuapp.com/api/record")
                .post(body)
                .addHeader("Authorization", "dfsdf")
                .build();
        Response response = client.newCall(request).execute();
        ErrorDto errorDto = gson.fromJson(response.body().string(), ErrorDto.class);
        Assert.assertFalse(response.isSuccessful());
        Assert.assertEquals(response.code(), 401);
        Assert.assertTrue(errorDto.getMessage().contains("Wrong authorization format"));

    }
}
