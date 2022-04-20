package ru.yandex.praktikum;

import io.restassured.response.ValidatableResponse;
import model.Courier;

import static io.restassured.RestAssured.given;

public class CreateCourier extends ScooterRestClient {

    public ValidatableResponse postEmptyData() {
        Courier courier = new Courier();
        return given().spec(baseSpec())
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier")
                .then();
    }

    public ValidatableResponse postLoginOnly(String login) {
        Courier courier = new Courier(login);
        return given().spec(baseSpec())
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier")
                .then();
    }

    public ValidatableResponse postDataWithoutFirstName(String login, String password) {
        Courier courier = new Courier(login, password);
        return given().spec(baseSpec())
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier")
                .then();
    }

    public ValidatableResponse postFullData(String login, String password, String firstName) {
        Courier courier = new Courier(login, password, firstName);
        return given().spec(baseSpec())
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier")
                .then();
    }
}
