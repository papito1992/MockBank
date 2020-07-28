import okhttp3.*;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class CustomerRegistrationAndQueryTests {
    //could not cover all the tests, ran out of time
    @Test
    public void givenUserDoesNotExists_whenUserIsNotRegistered_then404IsReceived()
            throws IOException {

        HttpUriRequest request = new HttpGet("http://localhost:8081/customer/12");

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        assertThat(
                httpResponse.getStatusLine().getStatusCode(),
                equalTo(HttpStatus.SC_NOT_FOUND));
    }

    @Test
    public void registerUserTrueReceived()
            throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();
        String placeholder = "{\"email\":\"test@test.com\",\"password\":\"123qweqweqweewqeqw\"}";
        RequestBody requestBody = RequestBody.create(placeholder, MediaType.parse("application/json; charset=utf-8"));
        Request request1 = new Request.Builder()
                .url("http://localhost:8081/register")
                .post(requestBody)
                .build();
        Response response = okHttpClient.newCall(request1).execute();
        assertThat(
                response.isSuccessful(),
                equalTo(true));
    }

    @Test
    public void registerUserFalseReceivedBecauseEmailIsInValid()
            throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();
        String placeholder = "{\"email\":\"te..st@test.com\",\"password\":\"123qweqweqweewqeqw\"}";
        RequestBody requestBody = RequestBody.create(placeholder, MediaType.parse("application/json; charset=utf-8"));
        Request request1 = new Request.Builder()
                .url("http://localhost:8081/register")
                .post(requestBody)
                .build();
        Response response = okHttpClient.newCall(request1).execute();
        assertThat(
                response.isSuccessful(),
                equalTo(false));
    }
    @Test
    public void registerUserFalseReceivedBecausePasswordIsInValid()
            throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();
        String placeholder = "{\"email\":\"te..st@test.com\",\"password\":\"123qwe\"}";
        RequestBody requestBody = RequestBody.create(placeholder, MediaType.parse("application/json; charset=utf-8"));
        Request request1 = new Request.Builder()
                .url("http://localhost:8081/register")
                .post(requestBody)
                .build();
        Response response = okHttpClient.newCall(request1).execute();
        assertThat(
                response.isSuccessful(),
                equalTo(false));
    }
}
