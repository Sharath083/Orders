package com.task.orders.constants;

import com.task.orders.dto.OrderData;
import com.task.orders.dto.OrderRequest;
import com.task.orders.dto.OrderResponse;
import com.task.orders.dto.SessionData;
import com.task.orders.entity.OrderDataEntity;
import com.task.orders.entity.ProductsEntity;
import com.task.orders.entity.UserEntity;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.UUID;

public class Helpers extends MockitoAnnotations {
    UserEntity user = new UserEntity();

    public void dataInsert() {
        user.setId(UUID.randomUUID());
        user.setName("test");

    }
    public static OrderRequest orderInputSuccess(){
        var s=List.of(new OrderData(TestConstants.PRODUCT_ID,"a",1));
        return new OrderRequest(TestConstants.ORDER_ID,s);
    }
    public static OrderRequest orderInputFailure(){
        var s=List.of(new OrderData(TestConstants.PRODUCT_ID_INVALID,"a",1));
        return new OrderRequest(TestConstants.ORDER_ID,s);
    }
    public static ProductsEntity setProducts(){
        var s=new ProductsEntity();
        s.setId(UUID.fromString(TestConstants.PRODUCT_ID));
        s.setName("a");
        s.setPrice(1);
        return s;
    }
    public static OrderDataEntity orderData(){
        var s=new OrderDataEntity();
        s.setId(UUID.fromString(TestConstants.ORDER_ID));
        s.setProductId(setProducts());
        s.setQuantity(1);
        return s;
    }
    public static UserEntity user(){
        var s=new UserEntity();
        s.setId(UUID.fromString(TestConstants.USER_ID));
        s.setName("a");
        s.setPassword("a");
        s.setEmail("a@gmail.com");
        return s;
    }
    public static OrderResponse orderResponse(){
        return new OrderResponse(UUID.fromString(TestConstants.ORDER_ID),
                List.of(new OrderData(TestConstants.PRODUCT_ID,
                        "a", 1)),
                    1);

    }
    public static SessionData sessionData(){
        return new SessionData(TestConstants.USER_ID,"a@gmail.com","a");
    }

}
