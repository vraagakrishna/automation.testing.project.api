package tests.reqres;

import io.qameta.allure.*;
import io.restassured.response.Response;
import model.reqres.Failure;
import model.reqres.LoginRegisterUser;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.AllureUtils;

import static requestbuilder.reqres.ReqResApiRequestBuilder.*;
import static utils.ValidateReqResUtils.validateFailedResponse;

@Feature("Auth Endpoints")
public class AuthTests extends ReqResApiTests {

    @Story("Login")
    @Description("Verify that logging in without an API key returns an authentication error")
    @Test(priority = 1)
    @Severity(SeverityLevel.CRITICAL)
    public void loginUserWithoutApiKey() {
        LoginRegisterUser<Object> loginRegisterUser = new LoginRegisterUser<>();
        Failure failureResponse = login(false, loginRegisterUser)
                .then()
                .assertThat().statusCode(HttpStatus.SC_UNAUTHORIZED)
                .extract().as(Failure.class);

        validateFailedResponse(failureResponse, "Missing API key");
    }

    @Story("Login")
    @Description("Verify that logging in without an email returns a validation error")
    @Test(priority = 2)
    @Severity(SeverityLevel.CRITICAL)
    public void loginUserWithoutEmail() {
        LoginRegisterUser<Object> loginRegisterUser = new LoginRegisterUser<>();
        loginRegisterUser.setPassword(data.getPassword());

        Failure failureResponse = login(true, loginRegisterUser)
                .then()
                .assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract().as(Failure.class);

        validateFailedResponse(failureResponse, "Missing email or username");
    }

    @Story("Login")
    @Description("Verify that logging in without a password returns a validation error")
    @Test(priority = 3)
    @Severity(SeverityLevel.CRITICAL)
    public void loginUserWithoutPassword() {
        LoginRegisterUser<Object> loginRegisterUser = new LoginRegisterUser<>();
        loginRegisterUser.setEmail(data.getEmail());

        Failure failureResponse = login(true, loginRegisterUser)
                .then()
                .assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract().as(Failure.class);

        validateFailedResponse(failureResponse, "Missing password");
    }

    @Story("Register")
    @Description("Verify that registering without an API key returns an authentication error")
    @Test(priority = 4)
    @Severity(SeverityLevel.CRITICAL)
    public void registerUserWithoutApiKey() {
        LoginRegisterUser<Object> loginRegisterUser = new LoginRegisterUser<>();
        Failure failureResponse = register(false, loginRegisterUser)
                .then()
                .assertThat().statusCode(HttpStatus.SC_UNAUTHORIZED)
                .extract().as(Failure.class);

        validateFailedResponse(failureResponse, "Missing API key");
    }

    @Story("Login")
    @Description("Verify that logging in without registration returns an error")
    @Test(priority = 5)
    @Severity(SeverityLevel.BLOCKER)
    public void loginUserWithoutRegister() {
        LoginRegisterUser<Object> loginRegisterUser = new LoginRegisterUser<>();
        loginRegisterUser.setEmail(data.getEmail());
        loginRegisterUser.setPassword(data.getPassword());

        Failure failureResponse = login(true, loginRegisterUser)
                .then()
                .assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract().as(Failure.class);

        validateFailedResponse(failureResponse, "user not found");
    }

    @Story("Register")
    @Description("Verify that registering without an email returns a validation error")
    @Test(priority = 1, dependsOnMethods = "loginUserWithoutRegister")
    @Severity(SeverityLevel.BLOCKER)
    public void registerUserWithoutEmail() {
        LoginRegisterUser<Object> loginRegisterUser = new LoginRegisterUser<>();
        loginRegisterUser.setPassword(data.getPassword());

        Failure failureResponse = register(true, loginRegisterUser)
                .then()
                .assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract().as(Failure.class);

        validateFailedResponse(failureResponse, "Missing email or username");
    }

    @Story("Register")
    @Description("Verify that registering without a password returns a validation error")
    @Test(priority = 2, dependsOnMethods = "loginUserWithoutRegister")
    @Severity(SeverityLevel.BLOCKER)
    public void registerUserWithoutPassword() {
        LoginRegisterUser<Object> loginRegisterUser = new LoginRegisterUser<>();
        loginRegisterUser.setEmail(data.getEmail());

        Failure failureResponse = register(true, loginRegisterUser)
                .then()
                .assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract().as(Failure.class);

        validateFailedResponse(failureResponse, "Missing password");
    }

    @Story("Register")
    @Description("Verify that registering without invalid credentials returns an error")
    @Test(priority = 3, dependsOnMethods = "loginUserWithoutRegister")
    @Severity(SeverityLevel.BLOCKER)
    public void registerUserInvalidCredentials() {
        AllureUtils.attachNote("The email and password have to be hard-coded to work...");

        LoginRegisterUser<Object> loginRegisterUser = new LoginRegisterUser<>();
        loginRegisterUser.setEmail(data.getEmail());
        loginRegisterUser.setPassword(data.getPassword());

        Failure failureResponse = register(true, loginRegisterUser)
                .then()
                .assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract().as(Failure.class);

        validateFailedResponse(failureResponse, "Note: Only defined users succeed registration");
    }

    @Story("Register")
    @Description("Verify that registering with invalid data types returns a validation error")
    @Test(priority = 4, dependsOnMethods = "loginUserWithoutRegister")
    @Severity(SeverityLevel.BLOCKER)
    public void registerUserInvalidDataTypes() {
        LoginRegisterUser<Object> loginRegisterUser = new LoginRegisterUser<>();
        loginRegisterUser.setEmail(1);
        loginRegisterUser.setPassword(1);

        Failure failureResponse = register(true, loginRegisterUser)
                .then()
                .assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract().as(Failure.class);

        validateFailedResponse(failureResponse, "Note: Only defined users succeed registration");
    }

    @Story("Register")
    @Description("Verify that registering with valid credentials returns a success response")
    @Test(priority = 5, dependsOnMethods = "loginUserWithoutRegister")
    @Severity(SeverityLevel.BLOCKER)
    public void registerUserWithValidCredentials() {
        AllureUtils.attachNote("The email and password have to be hard-coded to work...");

        data.setEmail("eve.holt@reqres.in");
        data.setPassword("pistol");

        LoginRegisterUser<Object> loginRegisterUser = new LoginRegisterUser<>();
        loginRegisterUser.setEmail(data.getEmail());
        loginRegisterUser.setPassword(data.getPassword());

        Response response = register(true, loginRegisterUser)
                .then()
                .assertThat().statusCode(HttpStatus.SC_OK)
                .extract().response();

        data.setId(response.jsonPath().getString("id"));
        data.setToken(response.jsonPath().getString("token"));
    }

    @Story("Login")
    @Description("Verify logging in after registration returns a success response")
    @Test(dependsOnMethods = "registerUserWithValidCredentials")
    @Severity(SeverityLevel.BLOCKER)
    public void loginUserAfterRegister() {
        LoginRegisterUser<Object> loginRegisterUser = new LoginRegisterUser<>();
        loginRegisterUser.setEmail(data.getEmail());
        loginRegisterUser.setPassword(data.getPassword());

        Response response = login(true, loginRegisterUser)
                .then()
                .assertThat().statusCode(HttpStatus.SC_OK)
                .extract().response();

        Assert.assertEquals(
                response.jsonPath().getString("token"),
                data.getToken(),
                "Token does not match"
        );
    }

    @Story("Logout")
    @Description("Verify that logging out without an API key returns an authentication error")
    @Test(dependsOnMethods = "loginUserAfterRegister", priority = 1)
    @Severity(SeverityLevel.CRITICAL)
    public void logoutUserWithoutApiKey() {
        Failure failureResponse = logout(false)
                .then()
                .assertThat().statusCode(HttpStatus.SC_UNAUTHORIZED)
                .extract().as(Failure.class);

        validateFailedResponse(failureResponse, "Missing API key");
    }

    @Story("Logout")
    @Description("Verify logging out returns a success response")
    @Test(dependsOnMethods = "loginUserAfterRegister", priority = 2)
    @Severity(SeverityLevel.BLOCKER)
    public void logoutUser() {
        logout(true)
                .then()
                .assertThat().statusCode(HttpStatus.SC_OK)
                .extract().response();
    }

}
