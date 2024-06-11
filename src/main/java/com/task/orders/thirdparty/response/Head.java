package com.task.orders.thirdparty.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Head {
    @JsonProperty("status")
    private String status;
    @JsonProperty("statusDescription")
    private String statusDescription;
    @JsonProperty("responseCode")
    private String responseCode;
}
