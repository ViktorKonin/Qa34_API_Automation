package superScheduler;

import com.google.gson.Gson;
import dto.ErrorDto;
import dtoSuper.GetAllRecordsDto;
import dtoSuper.PeriodDto;
import dtoSuper.RecordDto;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

public class GetAllRecordsOkHttp {
    Gson gson = new Gson();
    public static final MediaType JSON = MediaType.get("application/json;charset=utf-8");
    OkHttpClient client = new OkHttpClient();
    String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Im5vYUBnbWFpbC5jb20ifQ.G_wfK7FRQLRTPu9bs2iDi2fcs69FHmW-0dTY4v8o5Eo";

    @Test
    public void getAllRecordsSuccess() throws IOException {
        PeriodDto periodDto = PeriodDto.builder()
                .monthFrom(5)
                .monthTo(5)
                .yearFrom(2021)
                .yearTo(2022)
                .build();
        RequestBody body = RequestBody.create(gson.toJson(periodDto), JSON);
        Request request = new Request.Builder()
                .url("https://super-scheduler-app.herokuapp.com/api/records")
                .post(body)
                .addHeader("Authorization", token)
                .build();
        Response response = client.newCall(request).execute();
        Assert.assertEquals(response.code(), 200);

        GetAllRecordsDto records = gson.fromJson(response.body().string(), GetAllRecordsDto.class);
        List<RecordDto> all = records.getRecords();
        for (RecordDto dto : all) {
            System.out.println(dto.toString());
            System.out.println("=====================");
        }
    }

    @Test
    public void getAllRecordsUnSuccessWrongToken() throws IOException {
        PeriodDto periodDto = PeriodDto.builder()
                .monthFrom(1)
                .monthTo(2)
                .yearFrom(2021)
                .yearTo(2022)
                .build();
        RequestBody body = RequestBody.create(gson.toJson(periodDto), JSON);
        Request request = new Request.Builder()
                .url("https://super-scheduler-app.herokuapp.com/api/records")
                .post(body)
                .addHeader("Authorization", "dfsdf")
                .build();
        Response response = client.newCall(request).execute();
        ErrorDto errorDto = gson.fromJson(response.body().string(), ErrorDto.class);
        Assert.assertEquals(response.code(), 401);
        Assert.assertEquals(errorDto.getMessage(), "Wrong authorization format");

    }

    @Test
    public void getAllRecordsUnSuccessWrongMonthPeriod() throws IOException {
        PeriodDto periodDto = PeriodDto.builder()
                .monthFrom(0)
                .monthTo(2)
                .yearFrom(2021)
                .yearTo(2022)
                .build();
        RequestBody body = RequestBody.create(gson.toJson(periodDto), JSON);
        Request request = new Request.Builder()
                .url("https://super-scheduler-app.herokuapp.com/api/records")
                .post(body)
                .addHeader("Authorization", token)
                .build();
        Response response = client.newCall(request).execute();
        ErrorDto errorDto = gson.fromJson(response.body().string(), ErrorDto.class);
        Assert.assertEquals(response.code(), 400);
        Assert.assertEquals(errorDto.getMessage(), "Wrong month period! Month from and to need be in range 1-12");

    }

    @Test
    public void getAllRecordsUnSuccessWrongYearPeriod() throws IOException {
        PeriodDto periodDto = PeriodDto.builder()
                .monthFrom(1)
                .monthTo(2)
                .yearFrom(2010)
                .yearTo(2023)
                .build();
        RequestBody body = RequestBody.create(gson.toJson(periodDto), JSON);
        Request request = new Request.Builder()
                .url("https://super-scheduler-app.herokuapp.com/api/records")
                .post(body)
                .addHeader("Authorization", token)
                .build();
        Response response = client.newCall(request).execute();
        ErrorDto errorDto = gson.fromJson(response.body().string(), ErrorDto.class);
        Assert.assertEquals(response.code(), 400);
        Assert.assertEquals(errorDto.getMessage(), "Wrong year period!Year from and to needs be in range currentYear - 2 years and currentYear + 2 years");

    }

}
