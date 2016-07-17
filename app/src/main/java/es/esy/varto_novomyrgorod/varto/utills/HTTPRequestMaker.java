package es.esy.varto_novomyrgorod.varto.utills;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HTTPRequestMaker {

    private static final String TAG = "HTTPRequestMaker";

    public static String sendGet(String url) {

        URL obj = null;
        try {
            obj = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        StringBuilder response = new StringBuilder();
        try {
            if (obj != null) {
                HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
                connection.setRequestMethod("GET");
                int responseCode = connection.getResponseCode();


                Log.d(TAG, "sendGet: Sending 'GET' request to URL : "  +url);
                Log.d(TAG, "sendGet: Response code - " + responseCode);

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));
                String inputLine;

                response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response.toString();
    }
}