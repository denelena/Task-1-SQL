package ru.netology.pages;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.UserInfo;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {

    private SelenideElement loginField =$("[data-test-id=login] input");
    private SelenideElement passwordField =$("[data-test-id=password] input");
    private SelenideElement loginButton =$("[data-test-id=action-login]");

    public VerificationPage validLogin(UserInfo li) {
        doFillAndClick(li.getLogin(), li.getPassword());
        return new VerificationPage();
    }

    public void invalidLogin(UserInfo li) {
        doFillAndClick(li.getLogin(), li.getPassword());

        //expect error popup
        PagesHelper.waitElementVisible("[data-test-id='error-notification'] .notification__title", "Ошибка");
        PagesHelper.waitElementVisible("[data-test-id='error-notification'] .notification__content", "Неверно указан логин или пароль");
    }

    private void doFillAndClick(String login, String pwd){
        loginField.setValue(login);
        passwordField.setValue(pwd);
        loginButton.click();
    }




}
