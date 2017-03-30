package com.example.zhengboyi.soke_videotest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Project Name: Soke_VideoTest
 * Author: ZhengBoyi
 * Create Time: 2017/3/29 17:57
 */

public class LoginWebService {
    private static String IP = "http://leyuan.soke910.com:8080/shangkewang";

    public static JSONObject executeHttp() throws IOException, JSONException {
        String url = IP + "/requestLogin.action";
        HttpPost post = new HttpPost(url);
        DefaultHttpClient client = new DefaultHttpClient();
        client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);
        client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 5000);
        HttpResponse response = client.execute(post);

        if (response.getStatusLine().getStatusCode() == 200) {
            return new JSONObject(getInfo(response));
        }
        return null;
    }

    private static String getInfo(HttpResponse response) throws IOException {
        HttpEntity entity = response.getEntity();
        InputStream is = entity.getContent();

        byte[] data = read(is);
        return new String(data);
    }

    public static byte[] read(InputStream is) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = is.read(buffer)) != -1) {
            outputStream.write(buffer, 0, len);
        }
        is.close();
        return outputStream.toByteArray();
    }
}
