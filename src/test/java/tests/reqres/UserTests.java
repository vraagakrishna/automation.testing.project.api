package tests.reqres;

import io.qameta.allure.*;
import io.restassured.response.Response;
import model.reqres.Failure;
import model.reqres.GetSingleUser;
import model.reqres.GetUser;
import model.reqres.GetUserData;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.AllureUtils;

import java.util.List;

import static requestbuilder.reqres.ReqResApiRequestBuilder.*;
import static utils.ValidateFormats.isValidIso8601;
import static utils.ValidateReqResUtils.*;

@Feature("User Endpoints")
public class UserTests extends ReqResApiTests {

    @Story("Get All Users")
    @Description("Verify that requesting all users without an API key returns an authentication error")
    @Test(priority = 1)
    @Severity(SeverityLevel.CRITICAL)
    public void getAllUsersWithoutApiKey() {
        Failure failureResponse = getUsers(false)
                .then()
                .assertThat().statusCode(HttpStatus.SC_UNAUTHORIZED)
                .extract().as(Failure.class);

        validateFailedResponse(failureResponse, "Missing API key");
    }

    @Story("Get All Users")
    @Description("Verify that the initial request to retrieve all users is successful")
    @Test(priority = 2)
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

    @Story("Get All Users")
    @Description("Verify that retrieving all users with a timeout delay times out")
    @Test(priority = 3)
    @Severity(SeverityLevel.CRITICAL)
    public void getAllUsersWithTimeoutDelay() {
        int delayInSeconds = 10;

        Response response = getUsersWithDelay(delayInSeconds)
                .then()
                .assertThat().statusCode(HttpStatus.SC_OK)
                .extract().response();

        double responseTimeInSeconds = response.time() / 1000.0;
        System.out.println("Response Time: " + responseTimeInSeconds + " s");
        AllureUtils.attachResponseTime(String.valueOf(responseTimeInSeconds), "s");

        int maxTimeDelay = delayInSeconds + 3;
        Assert.assertTrue(
                responseTimeInSeconds >= delayInSeconds && responseTimeInSeconds < maxTimeDelay,
                "Expected response time to be around " + delayInSeconds + " seconds, " +
                        "but got " + responseTimeInSeconds + " s"
        );

        GetUser user = response.as(GetUser.class);

        validateUser(user);
    }

    @Story("Get All Users")
    @Description("Verify that retrieving all users with a delay is successful")
    @Test(priority = 4)
    @Severity(SeverityLevel.CRITICAL)
    public void getAllUsersWithDelay() {
        int delayInSeconds = 2;

        Response response = getUsersWithDelay(delayInSeconds)
                .then()
                .assertThat().statusCode(HttpStatus.SC_OK)
                .extract().response();

        double responseTimeInSeconds = response.time() / 1000.0;
        System.out.println("Response Time: " + responseTimeInSeconds + " s");
        AllureUtils.attachResponseTime(String.valueOf(responseTimeInSeconds), "s");

        int maxTimeDelay = delayInSeconds + 3;
        Assert.assertTrue(
                responseTimeInSeconds >= delayInSeconds && responseTimeInSeconds < maxTimeDelay,
                "Expected response time to be around " + delayInSeconds + " seconds, " +
                        "but got " + responseTimeInSeconds + " s"
        );

        GetUser user = response.as(GetUser.class);

        validateUser(user);
    }

    @Story("Get All Users")
    @Description("Verify that requesting all users with page numbers is successful")
    @Test(priority = 5)
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

    @Story("Get User by ID")
    @Description("Verify that requesting a user without an API key returns an authentication error")
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

    @Story("Get User by ID")
    @Description("Verify that getting user by valid ID returns correct data and 200 OK")
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

    @Story("Get User by ID")
    @Description("Verify that getting user with an invalid ID returns 404 Not Found")
    @Test(dependsOnMethods = "getAllUsers", priority = 3)
    @Severity(SeverityLevel.NORMAL)
    public void getUserByInvalidId() {
        List<GetUserData> users = data.getUsers();
        int id = randomNumberGenerator.generateRandomNumber(users.size() + 1, users.size() + 20);
        getUserById(true, id)
                .then()
                .assertThat().statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Story("Put User by ID")
    @Description("Verify that updating (PUT) a user without an API key returns an authentication error")
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

    @Story("Put User by ID")
    @Description("Verify that updating (PUT) a user with valid data returns 200 OK")
    @Test(dependsOnMethods = "getAllUsers", priority = 5)
    @Severity(SeverityLevel.BLOCKER)
    public void putUser() {
        List<GetUserData> users = data.getUsers();
        GetUserData expectedUser = users.get(
                randomNumberGenerator.generateRandomNumber(0, users.size() - 1)
        );

        Response response = putUserById(true, expectedUser.getId())
                .then()
                .assertThat().statusCode(HttpStatus.SC_OK)
                .extract().response();

        String updatedAt = response.jsonPath().getString("updatedAt");

        Assert.assertTrue(
                isValidIso8601(updatedAt),
                "Invalid ISO 8601 format: " + updatedAt
        );
    }

    @Story("Patch User by ID")
    @Description("Verify that updating (PUT) a non-existent user ID returns 404 Not Found")
    @Test(dependsOnMethods = "getAllUsers", priority = 6)
    @Severity(SeverityLevel.NORMAL)
    public void putUserByInvalidId() {
        List<GetUserData> users = data.getUsers();
        int id = randomNumberGenerator.generateRandomNumber(users.size() + 1, users.size() + 20);
        putUserById(true, id)
                .then()
                .assertThat().statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Story("Patch User by ID")
    @Description("Verify that updating (PATCH) a user without an API key returns an authentication error")
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

    @Story("Patch User by ID")
    @Description("Verify that updating (PATCH) a user with valid data returns 200 OK")
    @Test(dependsOnMethods = "getAllUsers", priority = 8)
    @Severity(SeverityLevel.BLOCKER)
    public void patchUser() {
        List<GetUserData> users = data.getUsers();
        GetUserData expectedUser = users.get(
                randomNumberGenerator.generateRandomNumber(0, users.size() - 1)
        );

        Response response = patchUserById(true, expectedUser.getId())
                .then()
                .assertThat().statusCode(HttpStatus.SC_OK)
                .extract().response();

        String updatedAt = response.jsonPath().getString("updatedAt");

        Assert.assertTrue(
                isValidIso8601(updatedAt),
                "Invalid ISO 8601 format: " + updatedAt
        );
    }

    @Story("Patch User by ID")
    @Description("Verify that updating (PATCH) a non-existent user ID returns 404 Not Found")
    @Test(dependsOnMethods = "getAllUsers", priority = 9)
    @Severity(SeverityLevel.NORMAL)
    public void patchUserByInvalidId() {
        List<GetUserData> users = data.getUsers();
        int id = randomNumberGenerator.generateRandomNumber(users.size() + 1, users.size() + 20);
        patchUserById(true, id)
                .then()
                .assertThat().statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Story("Delete User by ID")
    @Description("Verify that deleting a user without an API key returns an authentication error")
    @Test(dependsOnMethods = "getAllUsers", priority = 10)
    @Severity(SeverityLevel.CRITICAL)
    public void deleteUserWithoutApiKey() {
        List<GetUserData> users = data.getUsers();
        GetUserData expectedUser = users.get(1);
        Failure failureResponse = deleteUserById(false, expectedUser.getId())
                .then()
                .assertThat().statusCode(HttpStatus.SC_UNAUTHORIZED)
                .extract().as(Failure.class);

        validateFailedResponse(failureResponse, "Missing API key");
    }

    @Story("Delete User by ID")
    @Description("Verify that deleting a valid user succeeds with 204 No Content")
    @Test(dependsOnMethods = "getAllUsers", priority = 11)
    @Severity(SeverityLevel.BLOCKER)
    public void deleteUser() {
        List<GetUserData> users = data.getUsers();
        GetUserData expectedUser = users.get(
                randomNumberGenerator.generateRandomNumber(0, users.size() - 1)
        );
        deleteUserById(true, expectedUser.getId())
                .then()
                .assertThat().statusCode(HttpStatus.SC_NO_CONTENT);
    }

    @Story("Delete User by ID")
    @Description("Verify that deleting a non-existent user ID returns 404 Not Found")
    @Test(dependsOnMethods = "getAllUsers", priority = 12)
    @Severity(SeverityLevel.NORMAL)
    public void deleteUserByInvalidId() {
        List<GetUserData> users = data.getUsers();
        int id = randomNumberGenerator.generateRandomNumber(users.size() + 1, users.size() + 20);
        deleteUserById(true, id)
                .then()
                .assertThat().statusCode(HttpStatus.SC_NOT_FOUND);
    }

}
