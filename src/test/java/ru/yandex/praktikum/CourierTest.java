package ru.yandex.praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class CourierTest {

    CreateCourier createCourier = new CreateCourier();

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
    }

    @Test
    @DisplayName("Check status code of /api/v1/courier/login") // имя теста
    @Description("Basic test for /api/v1/courier") // описание теста
    public void loginCourier() {

        File json = new File("src/test/resources/newCourier.json");
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(json)
                        .when()
                        .post("/api/v1/courier/login");
        response.then().assertThat().statusCode(200);
        System.out.println(response.body().asString());
    }

    @Test
    @DisplayName("Courier create test") // имя теста
    @Description("Basic test for /api/v1/courier. Check creation, status code and answer is true") // описание теста
    public void createNewCourier() {
        String courierLogin = RandomStringUtils.randomAlphabetic(10);
        // с помощью библиотеки RandomStringUtils генерируем пароль
        String courierPassword = RandomStringUtils.randomAlphabetic(10);
        // с помощью библиотеки RandomStringUtils генерируем имя курьера
        String courierFirstName = RandomStringUtils.randomAlphabetic(10);
        System.out.println(courierLogin);
        ValidatableResponse response = createCourier.postCourierData(courierLogin, courierPassword, courierFirstName);
        response.assertThat().body("ok", equalTo(true)).and().statusCode(201);
    }
}