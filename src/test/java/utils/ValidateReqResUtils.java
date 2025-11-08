package utils;

import model.reqres.*;
import org.testng.Assert;

import java.util.List;

import static utils.ValidateFormats.*;

public class ValidateReqResUtils {

    public static void validateResponse(GetResource resource, int page, int perPage) {
        Assert.assertNotNull(resource, "Response should not be null");

        // validate top-level fields
        Assert.assertTrue(resource.getPage() >= 0, "Page should be greater than 0");
        Assert.assertTrue(resource.getPerPage() >= 0, "Per page should be greater than 0");
        Assert.assertTrue(resource.getTotal() >= 0, "Total should be greater than 0");
        Assert.assertTrue(resource.getTotalPages() >= 0, "Total pages should be greater than 0");

        // validate data list
        List<GetResourceData> resourceDataList = resource.getData();
        Assert.assertNotNull(resourceDataList, "Response data should not be null");
        Assert.assertTrue(resourceDataList.size() <= perPage, "Data list has too many items");

        for (GetResourceData resourceData : resourceDataList) {
            Assert.assertNotNull(resourceData.getId(), "Id should not be null");
            Assert.assertNotNull(resourceData.getName(), "Name should not be null");
            Assert.assertNotNull(resourceData.getColor(), "Color should not be null");
            Assert.assertNotNull(resourceData.getYear(), "Year should not be null");
            Assert.assertNotNull(resourceData.getPantoneValue(), "Pantone value should not be null");
        }
    }

    public static void validateResponse(GetResource resource) {
        Assert.assertNotNull(resource, "Response should not be null");

        // validate top-level fields
        Assert.assertTrue(resource.getPage() >= 0, "Page should be greater than 0");
        Assert.assertTrue(resource.getPerPage() >= 0, "Per page should be greater than 0");
        Assert.assertTrue(resource.getTotal() >= 0, "Total should be greater than 0");
        Assert.assertTrue(resource.getTotalPages() >= 0, "Total pages should be greater than 0");

        // validate data list
        List<GetResourceData> resourceDataList = resource.getData();

        for (GetResourceData resourceData : resourceDataList) {
            validateResourceData(resourceData);
        }
    }

    public static void validateFailedResponse(Failure failureResponse, String message) {
        Assert.assertTrue(
                failureResponse.getError().toLowerCase().contains(message.toLowerCase()),
                "Response message is incorrect"
        );
    }

    public static void validateUser(GetUser user, int page, int perPage) {
        Assert.assertNotNull(user, "User should not be null");

        // validate top-level fields
        Assert.assertTrue(user.getPage() >= 0, "Page should be greater than 0");
        Assert.assertTrue(user.getPerPage() >= 0, "Per page should be greater than 0");
        Assert.assertTrue(user.getTotal() >= 0, "Total should be greater than 0");
        Assert.assertTrue(user.getTotalPages() >= 0, "Total pages should be greater than 0");

        // validate data list
        List<GetUserData> userDataList = user.getData();
        Assert.assertTrue(userDataList.size() <= perPage, "Data list has too many items");

        for (GetUserData userData : userDataList) {
            Assert.assertNotNull(userData.getId(), "Id should not be null");
            Assert.assertNotNull(userData.getEmail(), "Email should not be null");
            Assert.assertNotNull(userData.getFirstName(), "First name should not be null");
            Assert.assertNotNull(userData.getLastName(), "Last name should not be null");
            Assert.assertNotNull(userData.getAvatar(), "Avatar value should not be null");
        }
    }

    public static void validateUser(GetUser user) {
        Assert.assertNotNull(user, "User should not be null");

        // validate top-level fields
        Assert.assertTrue(user.getPage() >= 0, "Page should be greater than 0");
        Assert.assertTrue(user.getPerPage() >= 0, "Per page should be greater than 0");
        Assert.assertTrue(user.getTotal() >= 0, "Total should be greater than 0");
        Assert.assertTrue(user.getTotalPages() >= 0, "Total pages should be greater than 0");

        // validate data list
        List<GetUserData> userDataList = user.getData();

        for (GetUserData userData : userDataList) {
            validateUserData(userData);
        }
    }

    public static void validateSingleUser(GetUserData expectedUser, GetSingleUser userResponse) {
        Assert.assertNotNull(userResponse, "User should not be null");

        // validate data list
        GetUserData userData = userResponse.getData();

        validateUserData(userData);
    }

    public static void validateSingleResource(GetResourceData expectedResource, GetSingleResource resourceResponse) {
        Assert.assertNotNull(resourceResponse, "Resource should not be null");

        // validate data list
        GetResourceData responseData = resourceResponse.getData();
        
        validateResourceData(responseData);
    }

    private static void validateResourceData(GetResourceData resourceData) {
        Assert.assertNotNull(resourceData.getId(), "Id should not be null");
        Assert.assertNotNull(resourceData.getName(), "Name should not be null");
        Assert.assertNotNull(resourceData.getColor(), "Color should not be null");
        Assert.assertNotNull(resourceData.getYear(), "Year should not be null");
        Assert.assertNotNull(resourceData.getPantoneValue(), "Pantone value should not be null");

        // validate formats
        Assert.assertTrue(
                isValidHexColor(resourceData.getColor()),
                "Invalid color: " + resourceData.getColor()
        );
    }

    private static void validateUserData(GetUserData userData) {
        Assert.assertNotNull(userData.getId(), "Id should not be null");
        Assert.assertNotNull(userData.getEmail(), "Email should not be null");
        Assert.assertNotNull(userData.getFirstName(), "First name should not be null");
        Assert.assertNotNull(userData.getLastName(), "Last name should not be null");
        Assert.assertNotNull(userData.getAvatar(), "Avatar value should not be null");

        // validating format
        Assert.assertTrue(
                isValidEmail(userData.getEmail()),
                "Invalid email format: " + userData.getEmail()
        );

        Assert.assertTrue(
                isValidUrl(userData.getAvatar()),
                "Invalid url: " + userData.getAvatar()
        );
    }

}
