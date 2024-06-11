package com.task.orders.thirdparty;

import com.task.orders.client.ResponseHandler;
import com.task.orders.client.RestTemplateClient;
import com.task.orders.client.ThirdPartyClient;
import com.task.orders.config.ConfigParam;
import com.task.orders.thirdparty.request.ApiRequest;
import com.task.orders.thirdparty.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.HashMap;

@Service
public class ThirdPartyService {
    public static final String KEY = "KEY";
    public static final String REQUEST_CODE = "requestCode";
    public static final String USER_ID = "UserId";
    public static final String PASSWORD = "password";
    @Autowired
    private ThirdPartyClient thirdPartyClient;
    @Autowired
    private ConfigParam configParam;

    @Autowired
    private ResponseHandler responseHandler;

    @Autowired
    private RestTemplateClient client;
    public ApiResponse thirdPartyMethod() throws IOException {
        HashMap<String,String> headers = new HashMap<>();
        headers.put(KEY, configParam.getKey());
        headers.put(REQUEST_CODE, configParam.getRequestCode());
        headers.put(USER_ID, configParam.getUserId());
        headers.put(PASSWORD, configParam.getPassword());
        String url= configParam.getBaseUrl()+configParam.getApiUrl();
        var response=client.sendRequest(new ApiRequest(HttpMethod.GET,headers,url));
        return responseHandler.mapResponse(response, ApiResponse.class);
//        return responseHandler.mapResponse(s.toString(),ApiResponse.class);
    }
}
