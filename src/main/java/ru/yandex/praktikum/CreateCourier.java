package ru.yandex.praktikum;

import io.restassured.response.ValidatableResponse;
import ru.yandex.praktikum.model.CourierData;

import static io.restassured.RestAssured.given;

public class CreateCourier extends ScooterRestClient {

    public ValidatableResponse postFullData(String login, String password, String firstName) {
        CourierData courierData = new CourierData(login, password, firstName);
        return given().spec(baseSpec())
                .and()
                .body(courierData)
                .when()
                .post("/api/v1/courier")
                .then();
    }

    public ValidatableResponse postDataWithoutFirstName(String login, String password) {
        CourierData courierData = new CourierData(login, password);
        return given().spec(baseSpec())
                .and()
                .body(courierData)
                .when()
                .post("/api/v1/courier")
                .then();
    }

    public ValidatableResponse postEmptyData() {
        CourierData courierData = new CourierData();
        return given().spec(baseSpec())
                .and()
                .body(courierData)
                .when()
                .post("/api/v1/courier")
                .then();
    }

}
