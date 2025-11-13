package utils;

import com.github.javafaker.Faker;
import model.ndosiautomation.LoginResponseUserData;
import model.ndosiautomation.RegisterResponseData;

public class NdosiAutomationTestData {

    // <editor-fold desc="Class fields">
    private static final Faker faker = new Faker();

    public String password = generateFakePassword();

    public String weakPassword = faker.internet().password(1, 5, true, true, true);

    private String firstName = generateFakeFirstName();

    private String lastName = generateFakeLastName();

    private String email = generateFakeEmail();

    private RegisterResponseData registerResponseData;

    private LoginResponseUserData loginResponseUserData;

    private String token;
    // </editor-fold>

    // <editor-fold desc="Getters and Setters">
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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

    // <editor-fold desc="Public Methods">
    public void generateNewFirstName() {
        firstName = generateFakeFirstName();
    }

    public void generateNewLastName() {
        lastName = generateFakeLastName();
    }

    public void generateNewEmail() {
        email = generateFakeEmail();
    }
    // </editor-fold>

    // <editor-fold desc="Private Methods">
    private String generateFakeFirstName() {
        return faker.name().firstName();
    }

    private String generateFakeLastName() {
        return faker.name().lastName();
    }

    private String generateFakeEmail() {
        return lastName + "." + firstName + "." + faker.number().numberBetween(0, 10000) + "@gmail.com";
    }

    private String generateFakePassword() {
        //return "12345678";
        return faker.internet().password(8, 16, true, true, true);
    }
    // </editor-fold>

}
