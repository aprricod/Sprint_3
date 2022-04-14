package ru.yandex.praktikum;

import io.restassured.response.ValidatableResponse;
import ru.yandex.praktikum.model.CourierData;

import static io.restassured.RestAssured.given;

public class CreateCourier extends ScooterRestClient {

    public ValidatableResponse postCourierData(String login, String password, String firstName) {
        CourierData courierData = new CourierData(login, password, firstName);
        return given().spec(baseSpec())
                .and()
                .body(courierData)
                .when()
                .post("/api/v1/courier")
                .then();
    }

}
