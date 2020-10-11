package com.module.mockclient;

import com.google.common.io.CharStreams;
import org.apache.http.HttpEntity;
import org.apache.http.HttpVersion;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.AdditionalAnswers.*;

@SpringBootTest
class MockClientApplicationTests {
    @Autowired
    Navigator navigator;

    @Test
    void contextLoads() {
        HttpClientUtil mock = mock(HttpClientUtil.class);
        String trueResult = navigator.callMethod();
        assertEquals(trueResult, "true result");
        navigator.setHttpClient(mock);
        when(mock.getResult()).thenReturn(new HttpClientUtil().getLocalResult());
        assertEquals(navigator.callMethod(), "local result");
    }

    @Test
    void testMock() {
        HttpClientUtil mock = mock(HttpClientUtil.class);
        navigator.setHttpClient(mock);

        Answer<String> answer = invocationOnMock -> {
            String result = invocationOnMock.getArgument(0, String.class);

            return "success---->" + result;
        };

        when(mock.dispatch(any(String.class))).thenAnswer(answer);
        System.out.println(navigator.dispatch("localhost:8080"));
    }

}
