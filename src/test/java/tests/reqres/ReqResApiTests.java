package tests.reqres;

import io.qameta.allure.*;
import io.restassured.response.Response;
import model.reqres.*;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.AllureUtils;
import utils.RandomNumberGenerator;
import utils.ReqResTestData;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static requestbuilder.reqres.ReqResApiRequestBuilder.*;
import static utils.ValidateReqResUtils.*;

@Test
@Feature("ReqRes")
@Story("Keep reviews moving with living APIs")
public class ReqResApiTests {

    ReqResTestData data = new ReqResTestData();

    RandomNumberGenerator randomNumberGenerator = new RandomNumberGenerator();

    // <editor-fold desc="Resource Methods">
    @Description("Get all resources without api key")
    @Test(priority = 1)
    @Severity(SeverityLevel.CRITICAL)
    public void getAllResourcesWithoutApiKey() {
        Failure failureResponse = getResources(false, "test", 1, 10)
                .then()
                .assertThat().statusCode(HttpStatus.SC_UNAUTHORIZED)
                .extract().as(Failure.class);

        validateFailedResponse(failureResponse, "Missing API key");
    }

    @Description("Get all resources")
    @Test(priority = 2)
    @Severity(SeverityLevel.BLOCKER)
    public void getAllResources() {
        GetResource resource = getResources(true, "flower")
                .then()
                .assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(GetResource.class);

        validateResponse(resource);
    }

    @Description("Get all resources with page restriction")
    @Test(priority = 3)
    @Severity(SeverityLevel.BLOCKER)
    public void getAllResourcesWithPage() {
        int page = 10;
        int perPage = randomNumberGenerator.generateRandomNumber(0, 10);
        GetResource resource = getResources(true, "flower", page, perPage)
                .then()
                .assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(GetResource.class);

        validateResponse(resource, page, perPage);
    }
    // </editor-fold>

    // <editor-fold desc="User Methods">
    @Description("Get all users without api key")
    @Test(priority = 4)
    @Severity(SeverityLevel.CRITICAL)
    public void getAllUsersWithoutApiKey() {
        Failure failureResponse = getUsers(false)
                .then()
                .assertThat().statusCode(HttpStatus.SC_UNAUTHORIZED)
                .extract().as(Failure.class);

        validateFailedResponse(failureResponse, "Missing API key");
    }

    @Description("Get all users")
    @Test(priority = 5)
    @Severity(SeverityLevel.BLOCKER)
    public void getAllUsers() {
        GetUser user = getUsers(true)
                .then()
                .assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(GetUser.class);

        validateUser(user);

        // copy the users over to another list
        data.setUsers(user.getData());
    }

    @Description("Get all users with page restriction")
    @Test(priority = 6)
    @Severity(SeverityLevel.BLOCKER)
    public void getAllUsersWithPage() {
        int page = 10;
        int perPage = randomNumberGenerator.generateRandomNumber(0, 10);
        GetUser user = getUsers(true, page, perPage)
                .then()
                .assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(GetUser.class);

        validateUser(user, page, perPage);
    }

    @Description("Get a user without api key")
    @Test(dependsOnMethods = "getAllUsers", priority = 1)
    @Severity(SeverityLevel.CRITICAL)
    public void getUserWithoutApiKey() {
        List<GetUserData> users = data.getUsers();
        GetUserData expectedUser = users.get(1);
        Failure failureResponse = getUserById(false, expectedUser.getId())
                .then()
                .assertThat().statusCode(HttpStatus.SC_UNAUTHORIZED)
                .extract().as(Failure.class);

        validateFailedResponse(failureResponse, "Missing API key");
    }

    @Description("Get a user")
    @Test(dependsOnMethods = "getAllUsers", priority = 2)
    @Severity(SeverityLevel.BLOCKER)
    public void getUser() {
        List<GetUserData> users = data.getUsers();
        GetUserData expectedUser = users.get(
                randomNumberGenerator.generateRandomNumber(0, users.size() - 1)
        );
        GetSingleUser userResponse = getUserById(true, expectedUser.getId())
                .then()
                .assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(GetSingleUser.class);

        validateSingleUser(expectedUser, userResponse);
    }

    @Description("Get a user by invalid id")
    @Test(dependsOnMethods = "getAllUsers", priority = 3)
    @Severity(SeverityLevel.NORMAL)
    public void getUserByInvalidId() {
        List<GetUserData> users = data.getUsers();
        int id = randomNumberGenerator.generateRandomNumber(users.size() + 1, users.size() + 20);
        getUserById(true, id)
                .then()
                .assertThat().statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Description("Put a user without api key")
    @Test(dependsOnMethods = "getAllUsers", priority = 4)
    @Severity(SeverityLevel.CRITICAL)
    public void putUserWithoutApiKey() {
        List<GetUserData> users = data.getUsers();
        GetUserData expectedUser = users.get(1);
        Failure failureResponse = putUserById(false, expectedUser.getId())
                .then()
                .assertThat().statusCode(HttpStatus.SC_UNAUTHORIZED)
                .extract().as(Failure.class);

        validateFailedResponse(failureResponse, "Missing API key");
    }

    @Description("Put a user")
    @Test(dependsOnMethods = "getAllUsers", priority = 5)
    @Severity(SeverityLevel.BLOCKER)
    public void putUser() {
        List<GetUserData> users = data.getUsers();
        GetUserData expectedUser = users.get(
                randomNumberGenerator.generateRandomNumber(0, users.size() - 1)
        );
        putUserById(true, expectedUser.getId())
                .then()
                .assertThat().statusCode(HttpStatus.SC_OK)
                .body(containsString("updatedAt"));
    }

    @Description("Put a user by invalid id")
    @Test(dependsOnMethods = "getAllUsers", priority = 6)
    @Severity(SeverityLevel.NORMAL)
    public void putUserByInvalidId() {
        List<GetUserData> users = data.getUsers();
        int id = randomNumberGenerator.generateRandomNumber(users.size() + 1, users.size() + 20);
        putUserById(true, id)
                .then()
                .assertThat().statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Description("Patch a user without api key")
    @Test(dependsOnMethods = "getAllUsers", priority = 7)
    @Severity(SeverityLevel.CRITICAL)
    public void patchUserWithoutApiKey() {
        List<GetUserData> users = data.getUsers();
        GetUserData expectedUser = users.get(1);
        Failure failureResponse = patchUserById(false, expectedUser.getId())
                .then()
                .assertThat().statusCode(HttpStatus.SC_UNAUTHORIZED)
                .extract().as(Failure.class);

        validateFailedResponse(failureResponse, "Missing API key");
    }

    @Description("Patch a user")
    @Test(dependsOnMethods = "getAllUsers", priority = 8)
    @Severity(SeverityLevel.BLOCKER)
    public void patchUser() {
        List<GetUserData> users = data.getUsers();
        GetUserData expectedUser = users.get(
                randomNumberGenerator.generateRandomNumber(0, users.size() - 1)
        );
        patchUserById(true, expectedUser.getId())
                .then()
                .assertThat().statusCode(HttpStatus.SC_OK)
                .body(containsString("updatedAt"));
    }

    @Description("Patch a user by invalid id")
    @Test(dependsOnMethods = "getAllUsers", priority = 9)
    @Severity(SeverityLevel.NORMAL)
    public void patchUserByInvalidId() {
        List<GetUserData> users = data.getUsers();
        int id = randomNumberGenerator.generateRandomNumber(users.size() + 1, users.size() + 20);
        patchUserById(true, id)
                .then()
                .assertThat().statusCode(HttpStatus.SC_NOT_FOUND);
    }
    // </editor-fold>

    // <editor-fold desc="Auth Methods">
    @Description("Login user without api key")
    @Test(priority = 20)
    @Severity(SeverityLevel.CRITICAL)
    public void loginUserWithoutApiKey() {
        LoginRegisterUser<Object> loginRegisterUser = new LoginRegisterUser<>();
        Failure failureResponse = login(false, loginRegisterUser)
                .then()
                .assertThat().statusCode(HttpStatus.SC_UNAUTHORIZED)
                .extract().as(Failure.class);

        validateFailedResponse(failureResponse, "Missing API key");
    }

    @Description("Login user without register")
    @Test(priority = 21)
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

    @Description("Register user without api key")
    @Test(priority = 22)
    @Severity(SeverityLevel.CRITICAL)
    public void registerUserWithoutApiKey() {
        LoginRegisterUser<Object> loginRegisterUser = new LoginRegisterUser<>();
        Failure failureResponse = register(false, loginRegisterUser)
                .then()
                .assertThat().statusCode(HttpStatus.SC_UNAUTHORIZED)
                .extract().as(Failure.class);

        validateFailedResponse(failureResponse, "Missing API key");
    }

    @Description("Register user without api key")
    @Test(priority = 23, dependsOnMethods = "loginUserWithoutRegister")
    @Severity(SeverityLevel.BLOCKER)
    public void registerUser() {
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

    @Description("Login user after register")
    @Test(dependsOnMethods = "registerUser")
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

    @Description("Logout user without api key")
    @Test(dependsOnMethods = "loginUserAfterRegister", priority = 1)
    @Severity(SeverityLevel.CRITICAL)
    public void logoutUserWithoutApiKey() {
        Failure failureResponse = logout(false)
                .then()
                .assertThat().statusCode(HttpStatus.SC_UNAUTHORIZED)
                .extract().as(Failure.class);

        validateFailedResponse(failureResponse, "Missing API key");
    }

    @Description("Logout user")
    @Test(dependsOnMethods = "loginUserAfterRegister", priority = 2)
    @Severity(SeverityLevel.BLOCKER)
    public void logoutUser() {
        logout(true)
                .then()
                .assertThat().statusCode(HttpStatus.SC_OK)
                .extract().response();
    }
    // </editor-fold>

}
