package ru.yandex.praktikum;

import io.restassured.response.ValidatableResponse;
import model.Order;

import static io.restassured.RestAssured.given;

public class CreateOrder extends ScooterRestClient {
    public ValidatableResponse postOrderData(String firstName, String lastName, String address, String metroStation, String phone, int rentTime,
                                             String deliveryDate, String comment, String[] color) {
        Order order = new Order(firstName, lastName, address, metroStation, phone, rentTime,
                deliveryDate, comment, color);
        return given().spec(baseSpec())
                .and()
                .body(order)
                .when()
                .post("/api/v1/orders")
                .then();
    }
}
