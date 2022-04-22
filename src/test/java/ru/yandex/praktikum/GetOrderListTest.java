package ru.yandex.praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

public class GetOrderListTest {
    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
    }

    @Test
    @DisplayName("Get order list")
    @Description("Check order list is not empty")
    public void checkOrderListNotEmpty() {
        Response response = given()
                .get("api/v1/orders");
        response.then().assertThat()
                .body("orders", notNullValue(),
                        "pageInfo", notNullValue(),
                        "availableStations", notNullValue())
                .and().statusCode(200);
    }
}
