package ru.qascooter.apitests;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.qascooter.Order;
import ru.qascooter.apirequests.OrderRequests;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.*;

@RunWith(Parameterized.class)
public class CreateOrderTest {
    public Order order;
    OrderRequests orderRequest = new OrderRequests();
    public CreateOrderTest(Order order) {
        this.order = order;
    }

    @Before
    public void setUp() {
        orderRequest.setUp();
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][] {
                {new Order("Ivan", "Ivanov", "Адресс 0", "Павелецкая", "79099995566", 2, "2023-12-31", "Домофон не работает", new String[]{"Black"})},
                {new Order("Petr", "Petrov", "Адресс0", "Павелецкая", "+79099995566", 3, "2023-12-30", "Домофон не работает", new String[]{"Grey"})},
                {new Order("Alexey", "Alexeev", "Адресс 3", "Павелецкая", "79099990566", 2, "2023-12-31", "Домофон не работает", new String[]{"Black", "Grey"})},
                {new Order("Egor", "Egorov", "Адресс 0", "Павелецкая", "79099995566", 2, "2023-12-31", "Домофон не работает", new String[]{})},

        };
    }

    @Test
    @DisplayName("Создание заказа с разным цветом")
    public void checkCreateOrder() {
        orderRequest.setOrder(order);
        orderRequest.createOrder()
                .then()
                .body("track", notNullValue())
                .and()
                .statusCode(SC_CREATED);
    }
}
