package com.module.mockclient;

import com.google.common.io.CharStreams;
import lombok.Setter;
import org.apache.http.HttpVersion;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;
import org.springframework.stereotype.Component;

import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.invoke.StringConcatException;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

@Component
public class HttpClientUtil {
    private String name = "HttpClientUtil";
    @Setter
    private CloseableHttpClient httpClient = HttpClients.createDefault();

    public String getResult(){
        return "true result";
    }

    public String getLocalResult(){
        return "local result";
    }

    public String getNestedCall() {
        return getResult();
    }

    public String dispatch(String url) {
        String result = null;

        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new StringEntity("request String"));
            CloseableHttpResponse execute = httpClient.execute(httpPost);
            result = CharStreams.toString(new InputStreamReader(execute.getEntity().getContent(), StandardCharsets.UTF_8));
        } catch (Exception e) { e.printStackTrace(); }

        return result;
    }
}
