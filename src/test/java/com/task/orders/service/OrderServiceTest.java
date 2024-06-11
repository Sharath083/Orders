package com.task.orders.service;

import com.task.orders.constants.Helpers;
import com.task.orders.constants.TestConstants;
import com.task.orders.dto.BaseResponse;
import com.task.orders.dto.OrderRequest;
import com.task.orders.entity.OrderDataEntity;
import com.task.orders.exception.CommonException;
import com.task.orders.repository.OrderDataRepo;
import com.task.orders.repository.ProductsRepo;
import com.task.orders.service.impl.OrderDataService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class OrderServiceTest {
    @Mock
    private OrderDataRepo orderDataRepo;
    @Mock
    private ProductsRepo productsRepo;
    @InjectMocks
    private OrderDataService orderDataService;
    private final UUID PRODUCT_ID = UUID.fromString(TestConstants.PRODUCT_ID);
    private final UUID ORDER_ID = UUID.fromString(TestConstants.ORDER_ID);
    private final UUID ORDER_ID_INVALID = UUID.fromString(TestConstants.ORDER_ID_INVALID);
    private final UUID USER_ID = UUID.fromString(TestConstants.USER_ID);
    private final UUID USER_ID_INVALID = UUID.fromString(TestConstants.USER_ID_INVALID);
    OrderRequest orderRequest = Helpers.orderInputSuccess();


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        when(productsRepo.findById(PRODUCT_ID)).thenReturn(Optional.of(Helpers.setProducts()));
        when(orderDataRepo.deleteByOrderId(ORDER_ID_INVALID)).thenReturn(false);
        when(orderDataRepo.deleteByOrderId(ORDER_ID)).thenReturn(true);
        when(orderDataRepo.findAllByUserId(USER_ID_INVALID)).thenReturn(new ArrayList<>());
        when(orderDataRepo.findAllByUserId(USER_ID)).thenReturn(List.of(Helpers.orderData()));
        when(orderDataRepo.findByOrderData(TestConstants.ORDER_ID + PRODUCT_ID)).thenReturn(Helpers.orderData());
        when(orderDataRepo.findByOrderId(ORDER_ID)).thenReturn(Helpers.orderData());
        when(orderDataRepo.findByOrderId(ORDER_ID_INVALID)).thenReturn(null);
        when(orderDataRepo.findAllByOrderId(ORDER_ID)).thenReturn(List.of(Helpers.orderData()));
        when(orderDataRepo.findAllByOrderId(ORDER_ID_INVALID)).thenReturn(List.of());
    }

    @Test
    void testCreateOrder() {

        UUID userId = UUID.randomUUID();
        List<OrderDataEntity> orderDataEntities = List.of(new OrderDataEntity());
        when(orderDataRepo.saveAll(any())).thenReturn(orderDataEntities);

        BaseResponse response = orderDataService.createOrder(orderRequest, userId);
        Assertions.assertEquals(HttpStatus.OK.toString(), response.getInfoId());
    }

    @Test
    void testCreateOrderInvalid() {

        UUID userId = UUID.randomUUID();
        List<OrderDataEntity> orderDataEntities = List.of();
        when(orderDataRepo.saveAll(any())).thenReturn(orderDataEntities);
        BaseResponse response = orderDataService.createOrder(orderRequest,userId);
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.toString(), response.getInfoId());
    }

    @Test
    void testDeleteOrder() {
        BaseResponse response = orderDataService.deleteOrderDetails(ORDER_ID);
        Assertions.assertEquals(HttpStatus.OK.toString(), response.getInfoId());
    }

    @Test
    void testDeleteOrderInvalid() {
        BaseResponse response = orderDataService.deleteOrderDetails(ORDER_ID_INVALID);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.toString(), response.getInfoId());
    }

    @Test
    void updateOrderSuccess() {
        BaseResponse response = orderDataService.updateOrders(orderRequest, "add", USER_ID);
        Assertions.assertEquals(HttpStatus.OK.toString(), response.getInfoId());
    }

    @Test
    void updateOrderInvalidType() {
        try {
            orderDataService.updateOrders(orderRequest, "ad", USER_ID);
        } catch (CommonException e) {
            Assertions.assertEquals(HttpStatus.BAD_REQUEST.toString(), e.getInfoId());
        }
    }

    @Test
    void getOrderSuccess() {
        var response=orderDataService.getOrder(ORDER_ID);
        System.out.println(response);
        Assertions.assertEquals(response.getTotalPrice(),1);
    }


}
