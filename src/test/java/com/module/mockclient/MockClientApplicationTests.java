package com.module.mockclient;

import org.junit.jupiter.api.Test;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.AdditionalAnswers.*;

/**
 * spy和mock的相同点和区别：
 * 1.得到的对象同样可以进行"监管"，即验证和打桩；
 * 2.如果不对spy对象的methodA打桩，那么调用spy对象的methodA时，会调用其自身的真实方法
 * 3.如果不对mock对象的methodA打桩，将doNothing，且返回默认值（null, 0, false)
 */
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
    void nestedCall() {
        HttpClientUtil mock = mock(HttpClientUtil.class);
        String trueResult = navigator.nestedCall();
        assertEquals(trueResult, "true result");
        navigator.setHttpClient(mock);
        when(mock.getNestedCall()).thenCallRealMethod();
        when(mock.getResult()).thenReturn(new HttpClientUtil().getLocalResult());
        assertEquals(navigator.nestedCall(), "local result");
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

    @Test
    void nestedCallSpy(){
        HttpClientUtil spy = spy(HttpClientUtil.class);
        String trueResult = navigator.nestedCall();
        assertEquals(trueResult, "true result");
        navigator.setHttpClient(spy);
        when(spy.getResult()).thenReturn(new HttpClientUtil().getLocalResult());
        assertEquals(navigator.nestedCall(), "local result");
    }

}
