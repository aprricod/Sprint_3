package ru.yandex.praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

public class UnsuccessfulAuthCourierTest {

    AuthCourier authCourier = new AuthCourier();

    String courierLogin = RandomStringUtils.randomAlphabetic(10);
    String courierPassword = RandomStringUtils.randomAlphabetic(10);

    //авторизация с некорректными данными/несуществующий юзер
    @Test
    @DisplayName("Auth courier with invalid credentials")
    @Description("Auth courier with invalid credentials or nonexistent user")
    public void courierAuthWithWrongData() {

        ValidatableResponse auth = authCourier.postFullAuthData(courierLogin, courierPassword);
        auth.assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and().statusCode(404);
    }
}
