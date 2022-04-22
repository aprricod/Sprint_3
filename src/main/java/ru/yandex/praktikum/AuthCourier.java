package ru.yandex.praktikum;

import io.restassured.response.ValidatableResponse;
import model.Courier;

import static io.restassured.RestAssured.given;

public class AuthCourier extends ScooterRestClient{

    public ValidatableResponse postLoginOnly(String login) {
        Courier courier = new Courier(login);
        return given().spec(baseSpec())
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier/login")
                .then();
    }

    public ValidatableResponse postFullAuthData(String login, String password) {
        Courier courier = new Courier(login, password);
        return given().spec(baseSpec())
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier/login")
                .then();
    }
}
