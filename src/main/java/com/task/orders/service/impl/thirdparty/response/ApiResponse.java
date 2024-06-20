package com.task.orders.service.impl.thirdparty.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.task.orders.dto.BaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiResponse extends BaseResponse {
    @JsonProperty("head")
    private Head head;
    @JsonProperty("body")
    private Body body;
    // Add other fields as necessary
}
