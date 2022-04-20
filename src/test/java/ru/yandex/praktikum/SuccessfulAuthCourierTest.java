package ru.yandex.praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class SuccessfulAuthCourierTest {

    CreateCourier createCourier = new CreateCourier();
    AuthCourier authCourier = new AuthCourier();
    DeleteCourier deleteCourier = new DeleteCourier();

    String courierLogin;
    String courierPassword;
    String courierFirstName;

    int courierId;

    @Before
    public void setUp() {

        courierLogin = RandomStringUtils.randomAlphabetic(10);
        courierPassword = RandomStringUtils.randomAlphabetic(10);
        courierFirstName = RandomStringUtils.randomAlphabetic(10);

        //создаем курьера
        ValidatableResponse create = createCourier.postFullData(courierLogin, courierPassword, courierFirstName);
        create.assertThat()
                .body("ok", equalTo(true))
                .and().statusCode(201);
    }

    //проверка успешной авторизации
    @Test
    @DisplayName("Auth courier with correct data")
    @Description("Successful auth courier with correct data")
    public void courierAuthWithCorrectData() {

        //логинимся курьером с целью узнать id
        ValidatableResponse auth = authCourier.postFullAuthData(courierLogin, courierPassword);
        auth.assertThat()
                .body("id", notNullValue())
                .and().statusCode(200);
        courierId = auth.extract().body().path("id");
    }

    @After
    public void cleanUp() {
        //удаляем курьера
        ValidatableResponse delete = deleteCourier.deleteCourierByID(courierId);
        delete.assertThat()
                .body("ok", equalTo(true))
                .and().statusCode(200);
    }
}
