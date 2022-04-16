package ru.yandex.praktikum;

import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderTest {

    private final String firstName;
    private final String lastName;
    private final String address;
    private final String metroStation;
    private final String phone;
    private final int rentTime;
    private final String deliveryDate;
    private final String comment;
    private final String[] color;
    private final int expectedStatusCode;


    public CreateOrderTest(String firstName, String lastName, String address,
                           String metroStation, String phone, int rentTime,
                           String deliveryDate, String comment, String[] color,
                           int expectedStatusCode) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
        this.expectedStatusCode = expectedStatusCode;

    }

    @Parameterized.Parameters(name = "параметры заказа: {0}, {1}," +
            "{2}, {3}, {4}, {5}, {6}, {7}," +
            "{8} - этот параметр не обязателен, {9} ожидаемый результат для возврата track номера")

    public static Object[] getNewOrderData() {
        return new Object[][]{
                {"testFirstName", "testLastname", "testAddress", "metroStationTest",
                        "phoneTest", 6, "04-15-2022", "commentTest", new String[]{}, 201},
                {"testFirstName", "testLastname", "testAddress", "metroStationTest",
                        "phoneTest", 6, "04-15-2022", "commentTest", new String[]{"BLACK"}, 201},
                {"testFirstName", "testLastname", "testAddress", "metroStationTest",
                        "phoneTest", 6, "04-15-2022", "commentTest", new String[]{"BLACK, GREY"}, 201},
        };
    }

    @Test
    public void orderCanBeCreated() {
        CreateOrder createOrder = new CreateOrder();
        ValidatableResponse response = createOrder.postOrderData(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
        int trackId = response.extract().path("track");
        response.assertThat().body("track", notNullValue()).and().statusCode(201);
        System.out.println(trackId);
    }
}