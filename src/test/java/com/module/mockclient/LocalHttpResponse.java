package com.module.mockclient;

public class LocalHttpResponse {

    public String getResponse(String httpPost){
        String result;
        switch (httpPost) {
            case "vesselStruct":
                result =  "vesselStruct response";
                break;
            default:
                result = "default response";
                break;
        }
        return result;
    }
}
