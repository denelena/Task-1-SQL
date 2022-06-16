package ru.netology.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class PagesHelper {
    public static void waitElementVisible(String cssSelector, String containsText) {
        SelenideElement element = $(cssSelector);
        element.shouldBe(visible);
        element.shouldHave(text(containsText));
    }
}
