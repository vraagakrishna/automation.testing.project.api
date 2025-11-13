package tests.ndosiautomation;

import io.qameta.allure.*;
import model.ndosiautomation.Failure;
import model.ndosiautomation.GetProfileResponse;
import model.ndosiautomation.UpdateRequest;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

import static requestbuilder.ndosiautomation.NdosiAutomationRequestBuilder.getUserProfile;
import static requestbuilder.ndosiautomation.NdosiAutomationRequestBuilder.updateUserProfile;
import static utils.JwtUtils.expireJwtToken;
import static utils.ValidateNdosiAutomationUtils.validateFailedResponse;
import static utils.ValidateNdosiAutomationUtils.validateSuccessGetProfileResponse;

@Feature("User Profile Management Endpoints")
public class UserProfileTests extends NdosiAutomationTests {

    @Story("Get Profile")
    @Description("Verify that getting profile without a token returns an authentication error")
    @Test(priority = 1)
    @Severity(SeverityLevel.CRITICAL)
    public void getProfileWithoutToken() {
        Failure failureResponse = getUserProfile(null)
                .then()
                .assertThat().statusCode(HttpStatus.SC_UNAUTHORIZED)
                .extract().as(Failure.class);

        validateFailedResponse(failureResponse, "Authorization token required");
    }

    @Story("Get Profile")
    @Description("Verify that getting profile with an invalid token returns an authentication error")
    @Test(priority = 2)
    @Severity(SeverityLevel.CRITICAL)
    public void getProfileWithInvalidToken() {
        Failure failureResponse = getUserProfile(data.getFirstName())
                .then()
                .assertThat().statusCode(HttpStatus.SC_UNAUTHORIZED)
                .extract().as(Failure.class);

        validateFailedResponse(failureResponse, "Invalid or expired token");
    }

    @Story("Get Profile")
    @Description("Verify that getting profile with an expired token returns an authentication error")
    @Test(priority = 3)
    @Severity(SeverityLevel.CRITICAL)
    public void getProfileWithExpiredToken() {
        String expiredToken = expireJwtToken(data.getToken());

        Failure failureResponse = getUserProfile(expiredToken)
                .then()
                .assertThat().statusCode(HttpStatus.SC_UNAUTHORIZED)
                .extract().as(Failure.class);

        validateFailedResponse(failureResponse, "Invalid or expired token");
    }

    @Story("Get Profile")
    @Description("Verify that getting profile with a token returns the user profile")
    @Test(priority = 4)
    @Severity(SeverityLevel.CRITICAL)
    public void getProfileWithToken() {
        GetProfileResponse getProfileResponse = getUserProfile(data.getToken())
                .then()
                .assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(GetProfileResponse.class);

        validateSuccessGetProfileResponse(getProfileResponse, data, softAssert, "Profile retrieved successfully");

        checkSoftAssertion();
    }

    @Story("Update Profile")
    @Description("Verify that updating profile without a token returns an authentication error")
    @Test(priority = 5)
    @Severity(SeverityLevel.CRITICAL)
    public void updateProfileWithoutToken() {
        Failure failureResponse = updateUserProfile(null, new UpdateRequest<>())
                .then()
                .assertThat().statusCode(HttpStatus.SC_UNAUTHORIZED)
                .extract().as(Failure.class);

        validateFailedResponse(failureResponse, "Authorization token required");
    }

    @Story("Update Profile")
    @Description("Verify that updating profile with an invalid token returns an authentication error")
    @Test(priority = 6)
    @Severity(SeverityLevel.CRITICAL)
    public void updateProfileWithInvalidToken() {
        Failure failureResponse = updateUserProfile(data.getFirstName(), new UpdateRequest<>())
                .then()
                .assertThat().statusCode(HttpStatus.SC_UNAUTHORIZED)
                .extract().as(Failure.class);

        validateFailedResponse(failureResponse, "Invalid or expired token");
    }

    @Story("Update Profile")
    @Description("Verify that updating profile with an expired token returns an authentication error")
    @Test(priority = 7)
    @Severity(SeverityLevel.CRITICAL)
    public void updateProfileWithExpiredToken() {
        String expiredToken = expireJwtToken(data.getToken());

        Failure failureResponse = updateUserProfile(expiredToken, new UpdateRequest<>())
                .then()
                .assertThat().statusCode(HttpStatus.SC_UNAUTHORIZED)
                .extract().as(Failure.class);

        validateFailedResponse(failureResponse, "Invalid or expired token");
    }

    @Story("Update Profile")
    @Description("Verify that updating profile with an invalid payload returns an error")
    @Test(priority = 8)
    @Severity(SeverityLevel.CRITICAL)
    public void updateProfileWithoutPayload() {
        Failure failureResponse = updateUserProfile(data.getToken(), new UpdateRequest<>())
                .then()
                .assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(Failure.class);

        validateFailedResponse(failureResponse, "At least one field is required to update");
    }

    @Story("Update Profile")
    @Description("Verify that updating profile with an invalid email returns an error")
    @Test(priority = 9)
    @Severity(SeverityLevel.CRITICAL)
    public void updateProfileWithInvalidEmail() {
        UpdateRequest<Object> updateRequest = new UpdateRequest<>();
        updateRequest.setEmail("");

        Failure failureResponse = updateUserProfile(data.getToken(), updateRequest)
                .then()
                .assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(Failure.class);

        validateFailedResponse(failureResponse, "Invalid email format");
    }

    @Story("Update Profile")
    @Description("Verify that updating profile without a name")
    @Test(priority = 10)
    @Severity(SeverityLevel.CRITICAL)
    public void updateProfileWithoutName() {
        data.setFirstName("");
        data.setLastName("");

        UpdateRequest<Object> updateRequest = new UpdateRequest<>();
        updateRequest.setFirstName(data.getFirstName());
        updateRequest.setLastName(data.getLastName());

        GetProfileResponse getProfileResponse = updateUserProfile(data.getToken(), updateRequest)
                .then()
                .assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(GetProfileResponse.class);

        validateSuccessGetProfileResponse(getProfileResponse, data, softAssert, "Profile updated successfully");

        checkSoftAssertion();
    }

    @Story("Get Profile")
    @Description("Verify that getting profile after updating the name returns the updated user profile")
    @Test(priority = 11)
    @Severity(SeverityLevel.CRITICAL)
    public void getProfileAfterUpdatingName() {
        GetProfileResponse getProfileResponse = getUserProfile(data.getToken())
                .then()
                .assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(GetProfileResponse.class);

        validateSuccessGetProfileResponse(getProfileResponse, data, softAssert, "Profile retrieved successfully");

        checkSoftAssertion();
    }

    @Story("Update Profile")
    @Description("Verify that updating profile with name returns an error")
    @Test(priority = 12)
    @Severity(SeverityLevel.CRITICAL)
    public void updateProfileWithName() {
        data.generateNewFirstName();
        data.generateNewLastName();

        UpdateRequest<Object> updateRequest = new UpdateRequest<>();
        updateRequest.setFirstName(data.getFirstName());
        updateRequest.setLastName(data.getLastName());

        GetProfileResponse getProfileResponse = updateUserProfile(data.getToken(), updateRequest)
                .then()
                .assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(GetProfileResponse.class);

        validateSuccessGetProfileResponse(getProfileResponse, data, softAssert, "Profile updated successfully");

        checkSoftAssertion();
    }

    @Story("Get Profile")
    @Description("Verify that getting profile after updating the name returns the updated user profile")
    @Test(priority = 13)
    @Severity(SeverityLevel.CRITICAL)
    public void getProfileAfterUpdatingWithName() {
        GetProfileResponse getProfileResponse = getUserProfile(data.getToken())
                .then()
                .assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(GetProfileResponse.class);

        validateSuccessGetProfileResponse(getProfileResponse, data, softAssert, "Profile retrieved successfully");

        checkSoftAssertion();
    }

    @Story("Update Profile")
    @Description("Verify that updating profile with an email returns an success response")
    @Test(priority = 14)
    @Severity(SeverityLevel.CRITICAL)
    public void updateProfileWithEmail() {
        data.generateNewEmail();

        UpdateRequest<Object> updateRequest = new UpdateRequest<>();
        updateRequest.setEmail(data.getEmail());

        GetProfileResponse getProfileResponse = updateUserProfile(data.getToken(), updateRequest)
                .then()
                .assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(GetProfileResponse.class);

        validateSuccessGetProfileResponse(getProfileResponse, data, softAssert, "Profile updated successfully");

        checkSoftAssertion();
    }

    @Story("Get Profile")
    @Description("Verify that getting profile after updating the email returns the updated user profile")
    @Test(priority = 15)
    @Severity(SeverityLevel.CRITICAL)
    public void getProfileAfterUpdatingEmail() {
        GetProfileResponse getProfileResponse = getUserProfile(data.getToken())
                .then()
                .assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(GetProfileResponse.class);

        validateSuccessGetProfileResponse(getProfileResponse, data, softAssert, "Profile retrieved successfully");

        checkSoftAssertion();
    }

}
