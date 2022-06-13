package ru.netology.data;

import com.github.javafaker.Faker;
import java.util.Locale;

public class DataGenerator {

    private static String validLogin = "vasya";
    private static String validPwd = "qwerty123";

    public static UserInfo getValidLoginValidPassword(){
        return new UserInfo(validLogin, validPwd);
    }

    public static UserInfo getInvalidLoginValidPassword(){
        String invalidLogin = getInvalidLogin();
        return new UserInfo(invalidLogin, validPwd);
    }

    public static UserInfo getValidLoginInvalidPassword(){
        String invalidPwd = getInvalidPwd();
        return new UserInfo(validLogin, invalidPwd);
    }

    public static UserInfo getInvalidLoginInvalidPassword(){
        String invalidLogin = getInvalidLogin();
        String invalidPwd = getInvalidPwd();
        return new UserInfo(invalidLogin, invalidPwd);
    }

    private static String getInvalidLogin(){
        String invalidLogin = "";
        Faker fkr = new Faker(new Locale("ru"));
        do {
            invalidLogin = fkr.name().username().replace('ё', 'е').replace('Ё', 'Е');;
        }while (invalidLogin == validLogin); // а вдруг???

        return invalidLogin;
    }

    private static String getInvalidPwd(){
        String invalidPwd = "";
        Faker fkr = new Faker(new Locale("ru"));
        do {
            invalidPwd = fkr.funnyName().name();
        }while (invalidPwd == validPwd); // а вдруг???

        return invalidPwd;
    }
}
