package utils;

import com.github.javafaker.Faker;
import model.ndosiautomation.LoginResponseUserData;
import model.ndosiautomation.RegisterResponseData;

public class NdosiAutomationTestData {

    // <editor-fold desc="Class fields">
    private static final Faker faker = new Faker();

    public final String firstName = faker.name().firstName();

    public final String lastName = faker.name().lastName();

    public String email = firstName + "." + lastName + "@gmail.com";

    public String password = faker.internet().password(8, 16, true, true, true);

    public String weakPassword = faker.internet().password(1, 5, true, true, true);

    private RegisterResponseData registerResponseData;

    private LoginResponseUserData loginResponseUserData;

    private String token;
    // </editor-fold>

    // <editor-fold desc="Getters and Setters">
    public RegisterResponseData getRegisterResponseData() {
        return registerResponseData;
    }

    public void setRegisterResponseData(RegisterResponseData registerResponseData) {
        this.registerResponseData = registerResponseData;
    }

    public LoginResponseUserData getLoginResponseUserData() {
        return loginResponseUserData;
    }

    public void setLoginResponseUserData(LoginResponseUserData loginResponseUserData) {
        this.loginResponseUserData = loginResponseUserData;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    // </editor-fold>

}
