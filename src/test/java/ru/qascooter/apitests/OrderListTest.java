package ru.qascooter.apitests;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import ru.qascooter.apirequests.OrderRequests;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.*;

public class OrderListTest {
    OrderRequests orderRequest = new OrderRequests();

    @Before
    public void setUp() {
        orderRequest.setUp();
    }

    @Test
    @DisplayName("Получение списка заказов")
    public void getOrderList() {
        orderRequest.getOrders()
                .then()
                .assertThat().body("orders", notNullValue())
                .and()
                .statusCode(SC_OK);
    }
}
