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

    @Test
    @DisplayName("Create courier with non-unique login")
    @Description("Unsuccessful create courier with non-unique login")
    public void createNewCourierWithNonUniqueLogin() {

        //создаем курьера
        ValidatableResponse create = createCourier.postFullData(courierLogin, courierPassword, courierFirstName);
        create.assertThat()
                .body("ok", equalTo(true))
                .and().statusCode(201);

        //создание курьера с неуникальным логином
        ValidatableResponse auth = createCourier.postFullData(courierLogin, courierPassword, courierFirstName);
        auth.assertThat()
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."))
                .and().statusCode(409);
    }

    @After
    public void cleanUp() {

        //логинимся курьером с целью узнать id
        ValidatableResponse auth = authCourier.postFullAuthData(courierLogin, courierPassword);
        auth.assertThat()
                .body("id", notNullValue())
                .and().statusCode(200);
        int courierId = auth.extract().body().path("id");

        //удаляем курьера
        ValidatableResponse delete = deleteCourier.deleteCourierByID(courierId);
        delete.assertThat()
                .body("ok", equalTo(true))
                .and().statusCode(200);
    }
}