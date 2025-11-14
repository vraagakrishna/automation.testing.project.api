package tests.ndosiautomation;

import io.qameta.allure.*;
import model.ndosiautomation.Failure;
import model.ndosiautomation.LoginRequest;
import model.ndosiautomation.RegisterRequest;
import model.ndosiautomation.RegisterResponse;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

import static requestbuilder.ndosiautomation.NdosiAutomationRequestBuilder.login;
import static requestbuilder.ndosiautomation.NdosiAutomationRequestBuilder.register;
import static utils.ValidateNdosiAutomationUtils.validateFailedResponse;
import static utils.ValidateNdosiAutomationUtils.validateSuccessRegisterResponse;

@Feature("User Authentication Endpoints")
public class AuthTests extends NdosiAutomationTests {

    @Story("Login")
    @Description("Verify that logging in without a username returns a validation error")
    @Test(priority = 1)
    @Severity(SeverityLevel.CRITICAL)
    public void loginUserWithoutEmail() {
        LoginRequest<Object> loginRequest = new LoginRequest<>();
        loginRequest.setPassword(data.getPassword());

        Failure failureResponse = login(loginRequest)
                .then()
                .assertThat().statusCode(HttpStatus.SC_UNAUTHORIZED)
                .extract().as(Failure.class);

        validateFailedResponse(failureResponse, "Email and password are required");
    }

    @Story("Login")
    @Description("Verify that logging in without a password returns a validation error")
    @Test(priority = 2)
    @Severity(SeverityLevel.CRITICAL)
    public void loginUserWithoutPassword() {
        LoginRequest<Object> loginRequest = new LoginRequest<>();
        loginRequest.setEmail(data.getEmail());

        Failure failureResponse = login(loginRequest)
                .then()
                .assertThat().statusCode(HttpStatus.SC_UNAUTHORIZED)
                .extract().as(Failure.class);

        validateFailedResponse(failureResponse, "Email and password are required");
    }

    @Story("Login")
    @Description("Verify that logging in invalid data formats returns a validation error")
    @Test(priority = 3)
    @Severity(SeverityLevel.CRITICAL)
    public void loginUserWithInvalidDataFormats() {
        LoginRequest<Object> loginRequest = new LoginRequest<>();
        loginRequest.setEmail("123");
        loginRequest.setPassword(true);

        Failure failureResponse = login(loginRequest)
                .then()
                .assertThat().statusCode(HttpStatus.SC_UNAUTHORIZED)
                .extract().as(Failure.class);

        validateFailedResponse(failureResponse, "Invalid email or password");
    }

    @Story("Login")
    @Description("Verify that logging in before registration returns an error")
    @Test(priority = 4)
    @Severity(SeverityLevel.CRITICAL)
    public void loginUserWithValidDataBeforeRegistration() {
        LoginRequest<Object> loginRequest = new LoginRequest<>();
        loginRequest.setEmail(data.getEmail());
        loginRequest.setPassword(data.getPassword());

        Failure failureResponse = login(loginRequest)
                .then()
                .assertThat().statusCode(HttpStatus.SC_UNAUTHORIZED)
                .extract().as(Failure.class);

        validateFailedResponse(failureResponse, "Invalid email or password");
    }

    @Story("Register")
    @Description("Verify that registering without a first name returns a validation error")
    @Test(priority = 1, dependsOnMethods = "loginUserWithValidDataBeforeRegistration")
    @Severity(SeverityLevel.CRITICAL)
    public void registerUserWithoutFirstName() {
        RegisterRequest<Object> registerRequest = new RegisterRequest<>();
        registerRequest.setLastName(data.getLastName());
        registerRequest.setEmail(data.getEmail());
        registerRequest.setPassword(data.getPassword());
        registerRequest.setConfirmPassword(data.getPassword());

        Failure failureResponse = register(registerRequest)
                .then()
                .assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract().as(Failure.class);

        validateFailedResponse(failureResponse, "All fields are required");
    }

    @Story("Register")
    @Description("Verify that registering without a last name returns a validation error")
    @Test(priority = 2, dependsOnMethods = "loginUserWithValidDataBeforeRegistration")
    @Severity(SeverityLevel.CRITICAL)
    public void registerUserWithoutLastName() {
        RegisterRequest<Object> registerRequest = new RegisterRequest<>();
        registerRequest.setFirstName(data.getFirstName());
        registerRequest.setEmail(data.getEmail());
        registerRequest.setPassword(data.getPassword());
        registerRequest.setConfirmPassword(data.getPassword());

        Failure failureResponse = register(registerRequest)
                .then()
                .assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract().as(Failure.class);

        validateFailedResponse(failureResponse, "All fields are required");
    }

    @Story("Register")
    @Description("Verify that registering without an email returns a validation error")
    @Test(priority = 3, dependsOnMethods = "loginUserWithValidDataBeforeRegistration")
    @Severity(SeverityLevel.CRITICAL)
    public void registerUserWithoutEmail() {
        RegisterRequest<Object> registerRequest = new RegisterRequest<>();
        registerRequest.setFirstName(data.getFirstName());
        registerRequest.setLastName(data.getLastName());
        registerRequest.setPassword(data.getPassword());
        registerRequest.setConfirmPassword(data.getPassword());

        Failure failureResponse = register(registerRequest)
                .then()
                .assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract().as(Failure.class);

        validateFailedResponse(failureResponse, "All fields are required");
    }

    @Story("Register")
    @Description("Verify that registering without a password returns a validation error")
    @Test(priority = 4, dependsOnMethods = "loginUserWithValidDataBeforeRegistration")
    @Severity(SeverityLevel.CRITICAL)
    public void registerUserWithoutPassword() {
        RegisterRequest<Object> registerRequest = new RegisterRequest<>();
        registerRequest.setFirstName(data.getFirstName());
        registerRequest.setLastName(data.getLastName());
        registerRequest.setEmail(data.getEmail());
        registerRequest.setConfirmPassword(data.getPassword());

        Failure failureResponse = register(registerRequest)
                .then()
                .assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract().as(Failure.class);

        validateFailedResponse(failureResponse, "All fields are required");
    }

    @Story("Register")
    @Description("Verify that registering without a confirm password returns a validation error")
    @Test(priority = 5, dependsOnMethods = "loginUserWithValidDataBeforeRegistration")
    @Severity(SeverityLevel.CRITICAL)
    public void registerUserWithoutConfirmPassword() {
        RegisterRequest<Object> registerRequest = new RegisterRequest<>();
        registerRequest.setFirstName(data.getFirstName());
        registerRequest.setLastName(data.getLastName());
        registerRequest.setEmail(data.getEmail());
        registerRequest.setPassword(data.getPassword());

        Failure failureResponse = register(registerRequest)
                .then()
                .assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract().as(Failure.class);

        validateFailedResponse(failureResponse, "All fields are required");
    }

    @Story("Register")
    @Description("Verify that registering with invalid email returns a validation error")
    @Test(priority = 6, dependsOnMethods = "loginUserWithValidDataBeforeRegistration")
    @Severity(SeverityLevel.CRITICAL)
    public void registerUserWithInvalidEmail() {
        RegisterRequest<Object> registerRequest = new RegisterRequest<>();
        registerRequest.setFirstName(data.getFirstName());
        registerRequest.setLastName(data.getLastName());
        registerRequest.setEmail(data.getFirstName());
        registerRequest.setPassword(data.getPassword());
        registerRequest.setConfirmPassword(data.getPassword());

        Failure failureResponse = register(registerRequest)
                .then()
                .assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract().as(Failure.class);

        validateFailedResponse(failureResponse, "Invalid email format");
    }

    @Story("Register")
    @Description("Verify that registering with mismatch passwords returns a validation error")
    @Test(priority = 7, dependsOnMethods = "loginUserWithValidDataBeforeRegistration")
    @Severity(SeverityLevel.CRITICAL)
    public void registerUserWithMismatchPassword() {
        RegisterRequest<Object> registerRequest = new RegisterRequest<>();
        registerRequest.setFirstName(data.getFirstName());
        registerRequest.setLastName(data.getLastName());
        registerRequest.setEmail(data.getEmail());
        registerRequest.setPassword(data.getPassword());
        registerRequest.setConfirmPassword(data.getFirstName());

        Failure failureResponse = register(registerRequest)
                .then()
                .assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract().as(Failure.class);

        validateFailedResponse(failureResponse, "Passwords do not match");
    }

    @Story("Register")
    @Description("Verify that registering with weak passwords returns a validation error")
    @Test(priority = 8, dependsOnMethods = "loginUserWithValidDataBeforeRegistration")
    @Severity(SeverityLevel.CRITICAL)
    public void registerUserWithWeakPassword() {
        RegisterRequest<Object> registerRequest = new RegisterRequest<>();
        registerRequest.setFirstName(data.getFirstName());
        registerRequest.setLastName(data.getLastName());
        registerRequest.setEmail(data.getEmail());
        registerRequest.setPassword(data.weakPassword);
        registerRequest.setConfirmPassword(data.weakPassword);

        Failure failureResponse = register(registerRequest)
                .then()
                .assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract().as(Failure.class);

        validateFailedResponse(failureResponse, "Password must be at least 6 characters long");
    }

    @Story("Register")
    @Description("Verify that registering with valid credentials returns 201 Created")
    @Test(priority = 9, dependsOnMethods = "loginUserWithValidDataBeforeRegistration")
    @Severity(SeverityLevel.CRITICAL)
    public void registerUserWithValidDetails() {
        RegisterRequest<Object> registerRequest = new RegisterRequest<>();
        registerRequest.setFirstName(data.getFirstName());
        registerRequest.setLastName(data.getLastName());
        registerRequest.setEmail(data.getEmail());
        registerRequest.setPassword(data.getPassword());
        registerRequest.setConfirmPassword(data.getPassword());

        RegisterResponse registerResponse = register(registerRequest)
                .then()
                .assertThat().statusCode(HttpStatus.SC_CREATED)
                .extract().as(RegisterResponse.class);

        validateSuccessRegisterResponse(registerResponse, data, softAssert);

        checkSoftAssertion();
    }

    @Story("Register")
    @Description("Verify that registering after registration returns an error")
    @Test(priority = 1, dependsOnMethods = "registerUserWithValidDetails")
    @Severity(SeverityLevel.CRITICAL)
    public void registerUserAfterRegistrationDetails() {
        RegisterRequest<Object> registerRequest = new RegisterRequest<>();
        registerRequest.setFirstName(data.getFirstName());
        registerRequest.setLastName(data.getLastName());
        registerRequest.setEmail(data.getEmail());
        registerRequest.setPassword(data.getPassword());
        registerRequest.setConfirmPassword(data.getPassword());

        Failure failureResponse = register(registerRequest)
                .then()
                .assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract().as(Failure.class);

        validateFailedResponse(failureResponse, "User with this email already exists");
    }

    @Story("Login")
    @Description("Verify that logging in after registration returns a success")
    @Test(priority = 2, dependsOnMethods = "registerUserWithValidDetails")
    @Severity(SeverityLevel.CRITICAL)
    public void loginUserWithValidDataAfterRegistration() {
        this.loginUserWithValidData();
    }

}
