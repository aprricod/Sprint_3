package ru.yandex.praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class SuccessfulCreateCourierTest {

    CreateCourier createCourier = new CreateCourier();
    DeleteCourier deleteCourier = new DeleteCourier();
    AuthCourier authCourier = new AuthCourier();

    String courierLogin = RandomStringUtils.randomAlphabetic(10);
    String courierPassword = RandomStringUtils.randomAlphabetic(10);
    String courierFirstName = RandomStringUtils.randomAlphabetic(10);

    @After
    public void cleanUp() {

        ValidatableResponse auth = authCourier.postAuthData(courierLogin, courierPassword);
        auth.assertThat()
                .body("id", notNullValue())
                .and().statusCode(200);
        int courierId = auth.extract().body().path("id");

        ValidatableResponse delete = deleteCourier.deleteCourierByID(courierId);
        delete.assertThat()
                .body("ok", equalTo(true))
                .and().statusCode(200);
    }

    @Test
    @DisplayName("Courier create with full data test")
    @Description("Basic test for /api/v1/courier. Check creation, status code and answer is true")
    public void createCourierWithFullData() {

        ValidatableResponse create = createCourier.postFullData(courierLogin, courierPassword, courierFirstName);
        create.assertThat()
                .body("ok", equalTo(true))
                .and().statusCode(201);

        ValidatableResponse auth = createCourier.postFullData(courierLogin, courierPassword, courierFirstName);
        auth.assertThat()
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."))
                .and().statusCode(409);

    }
}