package ru.netology.tests;

import org.junit.jupiter.api.*;
import ru.netology.data.DataGenerator;
import ru.netology.data.DatabaseHelper;
import ru.netology.data.UserInfo;
import ru.netology.pages.LoginPage;
import ru.netology.pages.VerificationPage;

import javax.management.InvalidApplicationException;
import java.sql.SQLException;

import static com.codeborne.selenide.Selenide.*;

public class LoginTest {

    @Test
    @DisplayName("Should attempt invalid login,using correct user name and wrong password. Expecting error popup.")
    public void shouldInvalidLoginGoodNameBadPwd() {
        open("http://localhost:9999");

        LoginPage loginPage = new LoginPage();

        UserInfo testUserData = DataGenerator.getValidLoginInvalidPassword();
        loginPage.invalidLogin(testUserData);
    }

    @Test
    @DisplayName("Should attempt invalid login,using wrong user name and wrong password. Expecting error popup.")
    public void shouldInvalidLoginBadNameBadPwd() {
        open("http://localhost:9999");

        LoginPage loginPage = new LoginPage();

        UserInfo testUserData = DataGenerator.getInvalidLoginInvalidPassword();
        loginPage.invalidLogin(testUserData);
    }

    @Test
    @DisplayName("Should attempt invalid login,using wrong user name and correct password. Expecting error popup.")
    public void shouldInvalidLoginBadNameGoodPwd() {
        open("http://localhost:9999");

        LoginPage loginPage = new LoginPage();

        UserInfo testUserData = DataGenerator.getInvalidLoginValidPassword();
        loginPage.invalidLogin(testUserData);
    }

    @Test
    @DisplayName("Should attempt valid login,using correct user name and correct password. Expecting the validation page to come up.")
    public void shouldValidLoginAndGetVerificationPage() {
        open("http://localhost:9999");

        LoginPage loginPage = new LoginPage();

        UserInfo testUserData = DataGenerator.getValidLoginValidPassword();
        VerificationPage vp = loginPage.validLogin(testUserData);
    }

    @Test
    @DisplayName("Should attempt valid login,using correct user name and correct password. Then enter correct code on Verification page. Expecting to get to the dashboard page.")
    public void shouldValidLoginAndEnterGoodCode() throws SQLException, InvalidApplicationException {
        DatabaseHelper.resetCodes(); //That seems to reset invalid login count in SUT
        open("http://localhost:9999");

        LoginPage loginPage = new LoginPage();

        UserInfo testUserData = DataGenerator.getValidLoginValidPassword();
        VerificationPage verificationPage = loginPage.validLogin(testUserData);

        int validCode = DatabaseHelper.getVerificationCode(testUserData.getLogin());
        verificationPage.validVerify(validCode);
    }

    @Test
    @DisplayName("Should attempt valid login,using correct user name and correct password. Then enter wrong code on Verification page. Expecting error popup. ")
    public void shouldValidLoginAndEnterBadCode() throws SQLException, InvalidApplicationException {
        DatabaseHelper.resetCodes(); //That seems to reset invalid login count in SUT
        open("http://localhost:9999");

        LoginPage loginPage = new LoginPage();

        UserInfo testUserData = DataGenerator.getValidLoginValidPassword();
        VerificationPage verificationPage = loginPage.validLogin(testUserData);

        int validCode = DatabaseHelper.getVerificationCode(testUserData.getLogin());
        int invalidCode = validCode + 42;  //why 42? Only Douglas Adams would know. But the code is sure invalid ))

        verificationPage.invalidVerify(invalidCode);
    }

    @Test
    @DisplayName("Should perform valid login. Enter wrong code on Verification page; repeat 3 times. Then login once again with wrong code. Expecting error popup - too many wrong attempts, then go back to login page. User is blocked at this point.")
    public void shouldValidLoginAndEnterBadCode3times() throws SQLException, InvalidApplicationException {
        DatabaseHelper.resetCodes(); //That seems to reset invalid login count in SUT

        UserInfo testUserData = DataGenerator.getValidLoginValidPassword();

        open("http://localhost:9999");

        LoginPage loginPage = new LoginPage();
        VerificationPage verificationPage = loginPage.validLogin(testUserData);
        int validCode = DatabaseHelper.getVerificationCode(testUserData.getLogin());
        int invalidCode1 = validCode + 10;
        verificationPage.invalidVerify(invalidCode1);//first bad attempt, expect "error - bad code"

        open("http://localhost:9999");
        loginPage = new LoginPage();
        verificationPage = loginPage.validLogin(testUserData);
        validCode = DatabaseHelper.getVerificationCode(testUserData.getLogin());
        int invalidCode2 = validCode + 20;
        verificationPage.invalidVerify(invalidCode2);//second bad attempt, expect "error - bad code"

        open("http://localhost:9999");
        loginPage = new LoginPage();
        verificationPage = loginPage.validLogin(testUserData);
        validCode = DatabaseHelper.getVerificationCode(testUserData.getLogin());
        int invalidCode3 = validCode + 30;
        verificationPage.invalidVerify(invalidCode3);//third bad attempt, expect "error - bad code"

        open("http://localhost:9999");
        loginPage = new LoginPage();
        verificationPage = loginPage.validLogin(testUserData);
        validCode = DatabaseHelper.getVerificationCode(testUserData.getLogin());
        int invalidCode4 = validCode + 40;
        loginPage = verificationPage.invalidVerifyTooManyBadAttempts(invalidCode4);//last bad attempt, expect "too many bad logins, then jump to login page"

        //attempt to login again with the valid code:
        open("http://localhost:9999");
        loginPage = new LoginPage();
        verificationPage = loginPage.validLogin(testUserData);
        validCode = DatabaseHelper.getVerificationCode(testUserData.getLogin());
        loginPage = verificationPage.invalidVerifyTooManyBadAttempts(validCode);//valid code works no more, expect "too many bad logins, then jump to login page"
    }
}
