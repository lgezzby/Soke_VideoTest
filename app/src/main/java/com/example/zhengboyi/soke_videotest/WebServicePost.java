package com.example.zhengboyi.soke_videotest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Project Name: Soke_VideoTest
 * Author: ZhengBoyi
 * Create Time: 2017/3/28 9:46
 */

public class WebServicePost {
    private static String IP = "";

    /**
     * 通过POST方式获取HTTP服务器数据
     *
     * @param username
     * @param password
     * @return
     */
    public static String executeHttpPost(String username, String password) {
        try {
            String path = "http://" + IP + "";

            Map<String, String> params = new HashMap<String, String>();
            params.put("username", username);
            params.put("password", password);

            return sendPOSTRequest(path, params, "UTF-8");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        return null;
    }

    /**
     * 处理发送数据请求
     *
     * @param path     URL
     * @param params   参数
     * @param encoding 编码方式
     * @return
     */
    private static String sendPOSTRequest(String path, Map<String, String> params, String encoding) throws IOException {
        // 定义了一个list，该list的数据类型是NameValuePair（简单名称值对节点类型），
        // 这个代码多处用于Java像url发送Post请求。在发送post请求时用该list来存放参数。
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        if (params != null && !params.isEmpty()) {
            // Map类提供了一个称为entrySet()的方法，这个方法返回一个Map.Entry实例化后的对象集。
            // 接着，Map.Entry类提供了一个getKey()方法和一个getValue()方法。
            for (Map.Entry<String, String> entry : params.entrySet()) {
                pairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }

        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(pairs, encoding);

        HttpPost post = new HttpPost(path);
        post.setEntity(entity);
        DefaultHttpClient client = new DefaultHttpClient();
        // 请求超时
        client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);
        // 读取超时
        client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 5000);
        HttpResponse response = client.execute(post);

        // 判断是否成功收取信息
        if (response.getStatusLine().getStatusCode() == 200) {
            return getInfo(response);
        }

        // 为成功收取信息，返回空指针
        return null;
    }

    /**
     * 收取数据
     *
     * @param response
     * @return
     * @throws IOException
     */
    private static String getInfo(HttpResponse response) throws IOException {
        HttpEntity entity = response.getEntity();
        InputStream is = entity.getContent();

        // 将输入流转化为byte型
        byte[] data = WebServiceGet.read(is);

        // 转化为字符串
        return new String(data,"UTF-8");
    }

}
