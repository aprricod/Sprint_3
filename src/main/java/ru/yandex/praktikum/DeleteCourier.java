package ru.yandex.praktikum;

import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class DeleteCourier extends ScooterRestClient {
    public ValidatableResponse deleteCourierByID(int id) {
        return given().spec(baseSpec())
                .and()
                .when()
                .delete("/api/v1/courier/" + id)
                .then();
    }
}
