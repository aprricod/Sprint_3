package ru.yandex.praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

public class CreateCourierTest {

    CreateCourier createCourier = new CreateCourier();

    @Before
    public void setUp() {
    }

    @After
    public void cleanUo() {
    }


    @Test
    @DisplayName("Courier create with full data test")
    @Description("Basic test for /api/v1/courier. Check creation, status code and answer is true")
    public void createCourierWithFullData() {
        String courierLogin = RandomStringUtils.randomAlphabetic(10);
        // с помощью библиотеки RandomStringUtils генерируем пароль
        String courierPassword = RandomStringUtils.randomAlphabetic(10);
        // с помощью библиотеки RandomStringUtils генерируем имя курьера
        String courierFirstName = RandomStringUtils.randomAlphabetic(10);

        System.out.println(courierLogin + "," + courierPassword + "," + courierFirstName);

        ValidatableResponse response = createCourier.postFullData(courierLogin, courierPassword, courierFirstName);
        response.assertThat()
                .body("ok", equalTo(true))
                .and().statusCode(201);
    }

    @Test
    @DisplayName("Courier create without first name")
    public void createCourierWithoutFirstName() {

        String courierLogin = RandomStringUtils.randomAlphabetic(10);
        // с помощью библиотеки RandomStringUtils генерируем пароль
        String courierPassword = RandomStringUtils.randomAlphabetic(10);

        ValidatableResponse response = createCourier.postDataWithoutFirstName(courierLogin, courierPassword);
        response.assertThat()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and().statusCode(400);
    }

    @Test
    @DisplayName("Courier create with empty data")
    public void createCourierWithEmptyData() {
        ValidatableResponse response = createCourier.postEmptyData();
        response.assertThat()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and().statusCode(400);
    }
}