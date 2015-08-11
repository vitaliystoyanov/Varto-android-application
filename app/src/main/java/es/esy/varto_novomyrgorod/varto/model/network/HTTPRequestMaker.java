package es.esy.varto_novomyrgorod.varto.model.network;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class HTTPRequestMaker {
    private InputStream is = null;

    public String makeGETRequest(String url, List<NameValuePair> params) {
        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            if (params != null) {
                String paramString = URLEncodedUtils.format(params, "utf-8");
                url += "?" + paramString;
            }
            HttpGet httpGet = new HttpGet(url);
            HttpResponse httpResponse = httpClient.execute(httpGet);

            Log.i("DBG", "HTTP CODE:" + String.valueOf(httpResponse.getStatusLine().getStatusCode()));
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();
                return getString(is);
            }
        } catch (UnsupportedEncodingException e) {
            Log.i("DBG", "UnsupportedEncodingException!");
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            Log.i("DBG", "ClientProtocolException!");
            e.printStackTrace();
        } catch (IOException e) {
            Log.i("DBG", "IOException!HTTPRequestMaker");
            e.printStackTrace();
        }
        return null;
    }

    public String makePOSTRequest(String url, List<NameValuePair> params) {
        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            HttpResponse httpResponse = httpClient.execute(httpPost);

            Log.i("DBG", "HTTP CODE:" + String.valueOf(httpResponse.getStatusLine().getStatusCode()));
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();
                return getString(is);
            }
        } catch (UnsupportedEncodingException e) {
            Log.i("DBG", "UnsupportedEncodingException!");
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            Log.i("DBG", "ClientProtocolException!");
            e.printStackTrace();
        } catch (IOException e) {
            Log.i("DBG", "IOException!HTTPRequestMaker");
            e.printStackTrace();
        }
        return null;
    }


    private String getString(InputStream input) {
        BufferedReader reader = null;
        String json = null;
        try {
            reader = new BufferedReader(new InputStreamReader(input, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            input.close();

            json = sb.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }
}