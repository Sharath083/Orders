package com.task.orders.service.impl.thirdparty.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TableData {
    @JsonProperty("client_type")
    private String clientType;

    @JsonProperty("retail_clients")
    private double retailClients;

    @JsonProperty("retail_change_percent")
    private double retailChangePercent;

    @JsonProperty("dii")
    private double dii;

    @JsonProperty("dii_change_percent")
    private double diiChangePercent;

    @JsonProperty("fii")
    private double fii;

    @JsonProperty("fii_change_percent")
    private double fiiChangePercent;

    @JsonProperty("pro")
    private double pro;

    @JsonProperty("pro_change_percent")
    private double proChangePercent;

    @JsonProperty("total")
    private double total;

    @JsonProperty("total_change_percent")
    private double totalChangePercent;
}
