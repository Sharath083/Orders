package com.task.orders.service.impl.thirdparty.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Body {
    @JsonProperty("dates_info")
    private DatesInfo datesInfo;
    @JsonProperty("tableData")
    private List<TableData> tableData;
}
