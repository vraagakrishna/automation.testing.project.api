package tests.ndosiautomation;

import io.qameta.allure.*;
import model.ndosiautomation.Failure;
import model.ndosiautomation.GetProfileResponse;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

import static requestbuilder.ndosiautomation.NdosiAutomationRequestBuilder.getUserProfile;
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

}
