package ru.yandex.praktikum;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.Courier;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class SimpleCourierCreateTest {
    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
    }

    @Test
    //создание курьера с неуникальным логином
    public void createNewCourier() {

        //создаем курьера
        Courier courierFirst = new Courier("artem8", "1234", "artemFirst");
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(courierFirst)
                        .when()
                        .post("/api/v1/courierFirst");
        response.then().assertThat()
                .body("ok", equalTo(true))
                .and().statusCode(201);

        //логинимся курьером с целью узнать id
        Response login =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(courierFirst)
                        .when()
                        .post("/api/v1/courierFirst/login");
        int courierId = login.then().extract().body().path("id");
        login.then().assertThat().body("id", notNullValue()).and().statusCode(200);

        //пытаемся создать курьера с неуникальным логином
        Courier courierSecond = new Courier("artem8", "1234", "artemSecond");
        Response response1 =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(courierSecond)
                        .when()
                        .post("/api/v1/courierFirst");
        response1.then().assertThat()
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."))
                .and().statusCode(409);

        //удаляем курьера
        Response delete =
                given()
                        .header("Content-type", "application/json")
                        .when()
                        .delete("/api/v1/courierFirst/{courierId}", courierId);
        delete.then().assertThat().body("ok", equalTo(true))
                .and().statusCode(200);
    }
}
