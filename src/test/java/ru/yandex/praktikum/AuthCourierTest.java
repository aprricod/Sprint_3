package ru.yandex.praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.Courier;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class AuthCourierTest {

    //    String courierLogin = "testArtemCourier";
//    String courierPassword = "1234";
//    String courierFirstName = "artemFirst";

//    String courierLogin = RandomStringUtils.randomAlphabetic(10);
//    String courierPassword = RandomStringUtils.randomAlphabetic(10);
//    String courierFirstName = RandomStringUtils.randomAlphabetic(10);

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
    }

    //авторизация с некорректными данными/несуществующий юзер
    @Test
    @DisplayName("Auth courier with invalid credentials")
    @Description("Auth courier with invalid credentials or nonexistent user")
    public void courierAuthWithWrongData() {

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

    //авторизация без одного поля (без пароля)
    @Test
    @DisplayName("Auth courier without required data")
    @Description("Unsuccessful auth courier without required password")
    public void courierAuthWithoutPassword() {

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

    //проверка успешной авторизации
    @Test
    @DisplayName("Auth courier with correct data")
    @Description("Successful auth courier with correct data")
    public void createNewCourierThenAuthThenDelete() {

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
        Response auth =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(courier)
                        .when()
                        .post("/api/v1/courier/login");
        int courierId = auth.then().extract().body().path("id");
        auth.then().assertThat().body("id", notNullValue()).and().statusCode(200);

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