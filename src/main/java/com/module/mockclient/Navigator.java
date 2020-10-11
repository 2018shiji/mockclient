package com.module.mockclient;

import lombok.Data;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Data
@Controller
public class Navigator {
    HttpClientUtil httpClient = new HttpClientUtil();

    @RequestMapping("/callMethod")
    public String callMethod(){
        return httpClient.getResult();
    }

    public String dispatch(String url){
        String result = null;
        try{
            result = httpClient.dispatch(url);
        } catch (Exception e){e.printStackTrace();}
        return result;
    }
}
