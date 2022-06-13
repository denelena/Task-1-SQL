package ru.netology.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.UserInfo;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {
    private SelenideElement codeField = $("[data-test-id=code] input");
    private SelenideElement verifyButton = $("[data-test-id=action-verify]");

    public VerificationPage() {
        codeField.shouldBe(visible);
    }

    public void validVerify(int smsCode) {
        codeField.setValue(Integer.toString(smsCode));
        verifyButton.click();

        //expect Dashboard page
        SelenideElement headerField = $("[data-test-id='dashboard']");
        headerField.shouldBe(visible);
        headerField.shouldHave(Condition.text("Личный кабинет"));
    }

    public void invalidVerify(int smsCode) {
        codeField.setValue(Integer.toString(smsCode));
        verifyButton.click();

        //expect error popup
        SelenideElement errHeaderField = $("[data-test-id='error-notification'] .notification__title");
        errHeaderField.shouldBe(visible);
        errHeaderField.shouldHave(text("Ошибка"));

        SelenideElement errContentField = $("[data-test-id='error-notification'] .notification__content");
        errContentField.shouldBe(visible);
        errContentField.shouldHave(text("Неверно указан код! Попробуйте ещё раз."));
    }

    public LoginPage invalidVerifyTooManyBadAttempts(int smsCode) {
        codeField.setValue(Integer.toString(smsCode));
        verifyButton.click();

        //expect error popup
        SelenideElement errHeaderField = $("[data-test-id='error-notification'] .notification__title");
        errHeaderField.shouldBe(visible);
        errHeaderField.shouldHave(text("Ошибка"));

        SelenideElement errContentField = $("[data-test-id='error-notification'] .notification__content");
        errContentField.shouldBe(visible);
        errContentField.shouldHave(text("Превышено количество попыток"));

        return new LoginPage();
    }
}
