package ru.qascooter.apitests;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.qascooter.Courier;
import ru.qascooter.apirequests.CourierRequests;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.*;

public class CourierLoginTest {
    Courier courier = new Courier("Doc", "1234");
    CourierRequests courierRequest = new CourierRequests();

    @Before
    public void setUp() {
        courierRequest.setUp();
    }

    @Test
    @DisplayName("Авторизация курьера")
    public void checkLoginCourier() {
        courierRequest.setCourier(courier);
        courierRequest.createCourier();
        courierRequest.authCourier()
                .then()
                .assertThat().body("id", notNullValue())
                .and()
                .statusCode(SC_OK);
    }

    @Test
    @DisplayName("Авторизация без логина")
    public void checkLoginCourierWithoutLogin() {
        courierRequest.setCourier(new Courier("", "1234"));
        courierRequest.authCourier()
                .then()
                .assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Авторизация без пароля")
    public void checkLoginCourierWithoutPassword() {
        courierRequest.setCourier(new Courier("Doc", ""));
        courierRequest.authCourier()
                .then()
                .assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Авторизация несуществующим курьером")
    public void checkLoginNonExisting() {
        courierRequest.setCourier(courier);
        courierRequest.deleteCourier();
        courierRequest.authCourier()
                .then()
                .assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(SC_NOT_FOUND);
    }

    @Test
    @DisplayName("Авторизация с неверным логином")
    public void checkLoginWithIncorrectLogin() {
        courierRequest.setCourier(courier);
        courierRequest.createCourier();
        courierRequest.setCourier(new Courier("D", "1234"));
        courierRequest.authCourier()
                .then()
                .assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(SC_NOT_FOUND);
    }

    @Test
    @DisplayName("Авторизация с неверным паролем")
    public void checkLoginWithIncorrectPassword() {
        courierRequest.setCourier(courier);
        courierRequest.createCourier();
        courierRequest.setCourier(new Courier("Doc", "1"));
        courierRequest.authCourier()
                .then()
                .assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(SC_NOT_FOUND);
    }

    @After
    public void cleanData(){
        courierRequest.deleteCourier();
    }
}
