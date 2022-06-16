package ru.netology.pages;

public class DashboardPage {
    public DashboardPage() {
        PagesHelper.waitElementVisible("[data-test-id='dashboard']", "Личный кабинет");
    }
}
