package ru.netology.data;

public class UserInfo {
    private String login;
    private String password;

    public UserInfo(String login, String password){
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }
    public String getPassword() {
        return password;
    }
}
