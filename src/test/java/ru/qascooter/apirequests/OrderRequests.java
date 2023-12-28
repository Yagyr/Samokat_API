package ru.qascooter.apirequests;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import ru.qascooter.Order;

import static io.restassured.RestAssured.given;

public class OrderRequests extends ApiBase {
    private Order order;
    private final static String ENDPOINT_ORDERS = "/api/v1/orders";

    public void setOrder(Order order) {
        this.order = order;
    }

    @Step("Создать заказ")
    public Response createOrder() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body(order)
                .when()
                .post(ENDPOINT_ORDERS);
        return response;
    }

    @Step("Получить список заказов")
    public Response getOrders() {
        return given().get(ENDPOINT_ORDERS);
    }
}
