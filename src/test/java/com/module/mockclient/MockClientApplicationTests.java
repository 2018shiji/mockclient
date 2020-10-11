package com.module.mockclient;

import org.junit.jupiter.api.Test;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
            String argument = invocationOnMock.getArgument(0, String.class);
            LocalHttpResponse localHttpResponse = new LocalHttpResponse();
            String result = localHttpResponse.getResponse(argument);
            return "success---->" + result;
        };

        when(mock.dispatch(any(String.class))).thenAnswer(answer);
        System.out.println(navigator.dispatch("vesselStruct"));
    }

}
