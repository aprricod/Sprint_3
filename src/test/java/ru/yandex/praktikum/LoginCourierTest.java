package ru.yandex.praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class LoginCourierTest {
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

}
