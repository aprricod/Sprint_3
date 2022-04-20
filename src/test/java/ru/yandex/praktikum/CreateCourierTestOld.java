package ru.yandex.praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.Courier;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class CreateCourierTestOld {
    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";

        //создаем курьера
        Courier courierFirst = new Courier("testArtemCourier", "1234", "artemFirst");
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(courierFirst)
                        .when()
                        .post("/api/v1/courier");
        response.then().assertThat()
                .body("ok", equalTo(true))
                .and().statusCode(201);
    }

    //создание курьера с неуникальным логином
    @Test
    @DisplayName("Create courier with non-unique login")
    @Description("Unsuccessful create courier with non-unique login")
    public void createNewCourierWithNonUniqueLogin() {

        //пытаемся создать курьера с неуникальным логином
        Courier courierSecond = new Courier("testArtemCourier", "1234", "artemSecond");
        Response response1 =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(courierSecond)
                        .when()
                        .post("/api/v1/courier");
        response1.then().assertThat()
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."))
                .and().statusCode(409);
    }

    @After
    public void cleanUp() {
        Courier courierFirst = new Courier("testArtemCourier", "1234");

        //логинимся курьером с целью узнать id
        Response login =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(courierFirst)
                        .when()
                        .post("/api/v1/courier/login");
        int courierId = login.then().extract().body().path("id");
        login.then().assertThat().body("id", notNullValue()).and().statusCode(200);

        //удаляем курьера
        Response delete =
                given()
                        .header("Content-type", "application/json")
                        .when()
                        .delete("/api/v1/courier/{courierId}", courierId);
        delete.then().assertThat().body("ok", equalTo(true))
                .and().statusCode(200);
    }
}
