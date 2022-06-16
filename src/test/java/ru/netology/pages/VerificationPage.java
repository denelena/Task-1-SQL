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

    public DashboardPage validVerify(int smsCode) {
        doFillAndClick(smsCode);
        return new DashboardPage();
    }

    public void invalidVerify(int smsCode) {
        doFillAndClick(smsCode);

        //expect error popup
        PagesHelper.waitElementVisible("[data-test-id='error-notification'] .notification__title", "Ошибка");
        PagesHelper.waitElementVisible("[data-test-id='error-notification'] .notification__content", "Неверно указан код! Попробуйте ещё раз.");
    }

    public LoginPage invalidVerifyTooManyBadAttempts(int smsCode) {
        doFillAndClick(smsCode);

        //expect error popup
        PagesHelper.waitElementVisible("[data-test-id='error-notification'] .notification__title", "Ошибка");
        PagesHelper.waitElementVisible("[data-test-id='error-notification'] .notification__content", "Превышено количество попыток");

        return new LoginPage();
    }

    private void doFillAndClick(int smsCode){
        codeField.setValue(Integer.toString(smsCode));
        verifyButton.click();
    }
}
