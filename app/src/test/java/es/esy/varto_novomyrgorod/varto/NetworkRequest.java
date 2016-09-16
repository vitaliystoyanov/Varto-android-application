package es.esy.varto_novomyrgorod.varto;

import android.support.annotation.Nullable;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkRequest {

    private OkHttpClient httpClient;

    public NetworkRequest() {
        httpClient = new OkHttpClient();
    }

    @Nullable
    public String call(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            Response response = httpClient.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.getStackTrace();
        }
        return null;
    }
}
