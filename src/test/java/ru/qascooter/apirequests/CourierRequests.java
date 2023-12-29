package ru.qascooter.apirequests;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import ru.qascooter.Courier;

import static io.restassured.RestAssured.given;

public class CourierRequests extends ApiBase {
    private Courier courier;
    private final static String CREATE_ENDPOINT = "/api/v1/courier";
    private final static String LOGIN_ENDPOINT = "/api/v1/courier/login";

    public void setCourier(Courier courier) {
        this.courier = courier;
    }

    @Step("Создать курьера")
    public Response createCourier() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body(courier)
                .when()
                .post(CREATE_ENDPOINT);
        return response;
    }

    @Step("Авторизоваться курьером")
    public Response authCourier() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body(courier)
                .when()
                .post(LOGIN_ENDPOINT);
        return response;
    }

    @Step("Удалить курьера")
    public void deleteCourier() {
        Integer id = given()
                .contentType(ContentType.JSON)
                .body(courier)
                .when()
                .post(LOGIN_ENDPOINT)
                .then().extract().body().path("id");

        if (id != null) {
            given().delete(CREATE_ENDPOINT + String.format("/%d", id));
        }
    }
}
