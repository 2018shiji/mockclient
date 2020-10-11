package com.module.mockclient;

import com.google.common.io.CharStreams;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class LocalHttpResponse {
    public String getResponse(HttpPost httpPost){
        String result = "something error";
        try {
            HttpEntity entity = httpPost.getEntity();
            result = CharStreams.toString(new InputStreamReader(entity.getContent(), StandardCharsets.UTF_8));
        } catch (Exception e){e.printStackTrace();}
        return result;
    }
}
