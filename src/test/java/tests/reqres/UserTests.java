package tests.reqres;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
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

@Story("User Tests")
public class UserTests extends ReqResApiTests {

    @Description("Get all users without api key")
    @Test(priority = 1)
    @Severity(SeverityLevel.CRITICAL)
    public void getAllUsersWithoutApiKey() {
        Failure failureResponse = getUsers(false)
                .then()
                .assertThat().statusCode(HttpStatus.SC_UNAUTHORIZED)
                .extract().as(Failure.class);

        validateFailedResponse(failureResponse, "Missing API key");
    }

    @Description("Get all users")
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

    @Description("Get all users with delay")
    @Test(priority = 3)
    @Severity(SeverityLevel.CRITICAL)
    public void getAllUsersWithDelay() {
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

    @Description("Get all users with page restriction")
    @Test(priority = 4)
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

    @Description("Delete a user without api key")
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

    @Description("Delete a user")
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

    @Description("Delete a user by invalid id")
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
