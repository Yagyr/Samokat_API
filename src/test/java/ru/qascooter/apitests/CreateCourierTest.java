package ru.qascooter.apitests;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.qascooter.Courier;
import ru.qascooter.apirequests.CourierRequests;
import static org.hamcrest.Matchers.*;
import static org.apache.http.HttpStatus.*;

public class CreateCourierTest {
    Courier courier = new Courier("Doc", "1234", "Ivan");
    CourierRequests courierRequest = new CourierRequests();

    @Before
    public void setUp() {
        courierRequest.setUp();
    }

    @Test
    @DisplayName("Создание курьера")
    public void checkCreateCourier() {
        courierRequest.setCourier(courier);
        courierRequest.createCourier()
                .then()
                .assertThat().body("ok", is(true))
                .and()
                .statusCode(SC_CREATED);
    }


    @Test
    @DisplayName("Создание курьера с повторяющимся логином")
    public void checkCreateCourierWithRepeatLogin() {
        courierRequest.setCourier(courier);
        courierRequest.createCourier();
        courierRequest.createCourier()
                .then()
                .assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."))
                .and()
                .statusCode(SC_CONFLICT);
    }

    @Test
    @DisplayName("Создание курьера без логина")
    public void checkCreateCourierWithoutLogin() {
        courierRequest.setCourier(new Courier("", "1234", "Ivan"));
        courierRequest.createCourier()
                .then()
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Создание курьера без пароля")
    public void checkCreateCourierWithoutPassword() {
        courierRequest.setCourier(new Courier("Doc", "", "Ivan"));
        courierRequest.createCourier()
                .then()
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(SC_BAD_REQUEST);
    }

    @After
    public void cleanData() {
        courierRequest.deleteCourier();
    }
}
