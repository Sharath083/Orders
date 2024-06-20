package com.task.orders.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.orders.constants.Helpers;
import com.task.orders.constants.TestConstants;
import com.task.orders.dto.BaseResponse;
import com.task.orders.dto.OrderRequest;
import com.task.orders.redis.RedisHelper;
import com.task.orders.redis.RedisSessionAuthenticationFilter;
import com.task.orders.repository.OrderDataRepo;
import com.task.orders.repository.ProductsRepo;
import com.task.orders.service.dao.OrderDataInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class OrderControllerTest {
    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();
    @InjectMocks
    private OrderController orderController;
    @Mock
    private OrderDataRepo orderDataRepo;
    @Mock
    private ProductsRepo productsRepo;
    @Mock
    private OrderDataInterface orderDataInterface;
    @Mock
    private RedisHelper redisHelper;
    @Mock
    private RedisSessionAuthenticationFilter redis;
    private final UUID PRODUCT_ID = UUID.fromString(TestConstants.PRODUCT_ID);
    private final UUID ORDER_ID = UUID.fromString(TestConstants.ORDER_ID);
    private final UUID ORDER_ID_INVALID = UUID.fromString(TestConstants.ORDER_ID_INVALID);
    private final UUID USER_ID = UUID.fromString(TestConstants.USER_ID);
    private final UUID USER_ID_INVALID = UUID.fromString(TestConstants.USER_ID_INVALID);
    OrderRequest orderRequest = Helpers.orderInputSuccess();

    BaseResponse baseResponse = new BaseResponse(HttpStatus.OK.toString(), "message");


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
        when(orderDataInterface.createOrder(orderRequest, USER_ID)).thenReturn(baseResponse);
        when(orderDataInterface.updateOrders(orderRequest, "add", USER_ID)).thenReturn(baseResponse);
        when(orderDataInterface.getOrder(ORDER_ID)).thenReturn(Helpers.orderResponse());
        when(redis.getUserData()).thenReturn(Helpers.sessionData());
    }

    @Test
    void createOrder() throws Exception {
        String request = objectMapper.writeValueAsString(orderRequest);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/order")
                        .header("session-id", TestConstants.SESSION_ID)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    void updateOrder() throws Exception {
        String request = objectMapper.writeValueAsString(orderRequest);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/order/update?type=add")
                        .header("session-id", TestConstants.SESSION_ID)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    void getOrder() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/order/"+TestConstants.ORDER_ID)
                        .header("session-id", TestConstants.SESSION_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
