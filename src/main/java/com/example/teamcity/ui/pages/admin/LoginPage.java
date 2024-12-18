package com.example.teamcity.ui.pages.admin;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.api.models.User;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage extends BasePage {
    private static final String LOGIN_URL = "/login.html";

    private SelenideElement inputUsername = $("#username");
    private SelenideElement inputPassword = $("#password");
    private SelenideElement inputSubmitLogin = $(".loginButton");
    private SelenideElement icon = $(".ring-icon-icon");

    public SelenideElement inputSubmitLogin2 = $(".loginButton");

    @Step("Open Login Page")
    public static LoginPage open () {
        return Selenide.open(LOGIN_URL, LoginPage.class);
    }

    @Step("Login as {user.username}")
    public ProjectPage login(User user) {
        // Метод val вместо clear, sendKeys
        inputUsername.val(user.getUsername());
        inputPassword.val(user.getPassword());
        inputSubmitLogin.click();
        icon.shouldBe(Condition.visible, BASE_WAITING);
        return Selenide.page(ProjectPage.class);
    }
}