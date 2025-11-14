package utils;

import com.github.javafaker.Faker;
import model.ndosiautomation.GetProfileResponseData;
import model.ndosiautomation.LoginResponseUserData;
import model.ndosiautomation.RegisterResponseData;

public class NdosiAutomationTestData {

    // <editor-fold desc="Class fields">
    private static final Faker faker = new Faker();

    public String weakPassword = faker.internet().password(1, 5, true, true, true);

    private String password = generateFakePassword();

    private String firstName = generateFakeFirstName();

    private String lastName = generateFakeLastName();

    private String email = generateFakeEmail();

    private RegisterResponseData registerResponseData;

    private LoginResponseUserData loginResponseUserData;

    private String token;

    private GetProfileResponseData getProfileResponseData;
    // </editor-fold>

    // <editor-fold desc="Getters and Setters">
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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

    public GetProfileResponseData getGetProfileResponseData() {
        return getProfileResponseData;
    }

    public void setGetProfileResponseData(GetProfileResponseData getProfileResponseData) {
        this.getProfileResponseData = getProfileResponseData;
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

    public void generateNewPassword() {
        password = generateFakePassword();
    }
    // </editor-fold>

    // <editor-fold desc="Private Methods">
    private String sanitize(String input) {
        return input.replaceAll("[^A-Za-z0-9]", "");
    }

    private String generateFakeFirstName() {
        return faker.name().firstName();
    }

    private String generateFakeLastName() {
        return faker.name().lastName();
    }

    private String generateFakeEmail() {
        return sanitize(lastName) + "." + sanitize(firstName) + "." + faker.number().numberBetween(0, 10000) + "@gmail.com";
    }

    private String generateFakePassword() {
        return faker.internet().password(8, 16, true, true, true);
    }
    // </editor-fold>

}
