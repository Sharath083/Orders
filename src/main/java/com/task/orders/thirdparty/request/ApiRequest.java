package com.task.orders.thirdparty.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpMethod;

import java.util.HashMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiRequest {
    private HttpMethod httpMethod;
    private HashMap<String,String> headers;
    private String url;
    // Add other fields as necessary
}
