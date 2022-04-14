package ru.yandex.praktikum.model;

public class CourierData {

    private String login;
    private String password;
    private String firstName;

    public CourierData() {
    }

    public CourierData(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public CourierData(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

}