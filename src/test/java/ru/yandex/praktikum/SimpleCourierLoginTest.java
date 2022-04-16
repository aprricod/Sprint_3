package ru.yandex.praktikum;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.Courier;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class SimpleCourierLoginTest {
    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
    }

    @Test
    //авторизация с некорректными данными/несуществующий юзер
    public void courierLoginWithWrongData() {

        Courier courier = new Courier("anUnknownUserArtem", "1234");
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(courier)
                        .when()
                        .post("/api/v1/courier/login");
        response.then().assertThat()
                .body("message", equalTo("Учетная запись не найдена"))
                .and().statusCode(404);
    }

    @Test
    //авторизация без одного поля (без пароля)
    public void courierLoginWithoutPassword() {

        Courier courier = new Courier("testArtemCourier");
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(courier)
                        .when()
                        .post("/api/v1/courier/login");
        response.then().assertThat()
                .body("message", equalTo("Недостаточно данных для входа"))
                .and().statusCode(400);
        System.out.println(response.body().asString());
    }

    @Test
    //проверка успешной авторизации
    public void createNewCourierThenLoginThenDelete() {

        Courier courier = new Courier("testArtemCourier", "1234", "artem");
        //создаем курьера
        Response create =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(courier)
                        .when()
                        .post("/api/v1/courier");
        create.then().assertThat().body("ok", equalTo(true))
                .and().statusCode(201);

        //логинимся курьером
        Response login =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(courier)
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