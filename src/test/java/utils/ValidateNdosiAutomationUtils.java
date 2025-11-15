package utils;

import model.ndosiautomation.*;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import static utils.ValidateFormats.isValidAnyDateTime;

public class ValidateNdosiAutomationUtils {

    public static void validateFailedResponse(Failure failureResponse, String message) {
        Assert.assertFalse(
                failureResponse.isSuccess(),
                "Success should be false"
        );

        Assert.assertTrue(
                failureResponse.getMessage().toLowerCase().contains(message.toLowerCase()),
                "Response message is incorrect"
        );
    }

    public static void validateSuccessRegisterResponse(RegisterResponse registerResponse, NdosiAutomationTestData data, SoftAssert softAssert) {
        RegisterResponseData registerResponseData = registerResponse.getData();

        softAssert.assertEquals(
                registerResponse.getMessage(),
                "User registered successfully",
                "Response message is incorrect"
        );

        data.setRegisterResponseData(registerResponseData);

        softAssert.assertEquals(
                registerResponseData.getFirstName(),
                data.getFirstName(),
                "First name is incorrect"
        );

        softAssert.assertEquals(
                registerResponseData.getLastName(),
                data.getLastName(),
                "Last name is incorrect"
        );

        softAssert.assertEquals(
                registerResponseData.getEmail(),
                data.getEmail(),
                "Email is incorrect"
        );

        softAssert.assertTrue(
                isValidAnyDateTime(registerResponseData.getCreatedAt()),
                "Invalid datetime format: " + registerResponseData.getCreatedAt()
        );
    }

    public static void validateSuccessLoginResponse(LoginResponse loginResponse, NdosiAutomationTestData data, SoftAssert softAssert) {
        softAssert.assertEquals(
                loginResponse.getMessage(),
                "Login successful",
                "Response message is incorrect"
        );

        LoginResponseData loginResponseData = loginResponse.getData();

        data.setToken(loginResponseData.getToken());

        softAssert.assertNotNull(
                data.getToken(),
                "Token is null"
        );

        LoginResponseUserData loginResponseUserData = loginResponseData.getUser();

        data.setLoginResponseUserData(loginResponseUserData);
        validateJwtToken(data.getToken(), softAssert, loginResponseUserData);

        softAssert.assertEquals(
                loginResponseUserData.getFirstName(),
                data.getFirstName(),
                "First name is incorrect"
        );

        softAssert.assertEquals(
                loginResponseUserData.getLastName(),
                data.getLastName(),
                "Last name is incorrect"
        );

        softAssert.assertEquals(
                loginResponseUserData.getEmail(),
                data.getEmail(),
                "Email is incorrect"
        );

        if (data.getRegisterResponseData() != null) {
            softAssert.assertEquals(
                    loginResponseUserData.getCreatedAt(),
                    data.getRegisterResponseData().getCreatedAt(),
                    "Created at is incorrect"
            );
        }

        softAssert.assertTrue(
                isValidAnyDateTime(loginResponseUserData.getCreatedAt()),
                "Invalid datetime format: " + loginResponseUserData.getCreatedAt()
        );

        softAssert.assertTrue(
                isValidAnyDateTime(loginResponseUserData.getUpdatedAt()),
                "Invalid datetime format: " + loginResponseUserData.getUpdatedAt()
        );
    }

    public static void validateJwtToken(String jwtToken, SoftAssert softAssert, LoginResponseUserData loginResponseUserData) {
        JSONObject decodedJwtToken = JwtUtils.decodeJwt(jwtToken);
        softAssert.assertEquals(
                decodedJwtToken.get("userId"),
                loginResponseUserData.getId(),
                "Id does not match"
        );

        softAssert.assertEquals(
                decodedJwtToken.get("email"),
                loginResponseUserData.getEmail(),
                "Email does not match"
        );
    }

    public static void validateSuccessGetProfileResponse(GetProfileResponse getProfileResponse, NdosiAutomationTestData data, SoftAssert softAssert, String expectedMessage) {
        GetProfileResponseData getProfileResponseData = getProfileResponse.getData();

        softAssert.assertEquals(
                getProfileResponse.getMessage(),
                expectedMessage,
                "Response message is incorrect"
        );

        data.setGetProfileResponseData(getProfileResponseData);

        if (data.getRegisterResponseData() != null) {
            softAssert.assertEquals(
                    getProfileResponseData.getId(),
                    data.getRegisterResponseData().getId(),
                    "Id is incorrect"
            );
        }

        softAssert.assertEquals(
                getProfileResponseData.getFirstName(),
                data.getFirstName(),
                "First name is incorrect"
        );

        softAssert.assertEquals(
                getProfileResponseData.getLastName(),
                data.getLastName(),
                "Last name is incorrect"
        );

        softAssert.assertEquals(
                getProfileResponseData.getEmail(),
                data.getEmail(),
                "Email is incorrect"
        );

        softAssert.assertEquals(
                getProfileResponseData.getCreatedAt(),
                data.getLoginResponseUserData().getCreatedAt(),
                "Created at is incorrect"
        );

        if (expectedMessage.equals("Profile updated successfully")) {
            data.getLoginResponseUserData().setUpdatedAt(getProfileResponseData.getUpdatedAt());
        } else {
            softAssert.assertEquals(
                    getProfileResponseData.getUpdatedAt(),
                    data.getLoginResponseUserData().getUpdatedAt(),
                    "Updated at is incorrect"
            );
        }

        softAssert.assertTrue(
                getProfileResponseData.isActive(),
                "Is active is incorrect"
        );

        softAssert.assertTrue(
                isValidAnyDateTime(getProfileResponseData.getCreatedAt()),
                "Invalid datetime format: " + getProfileResponseData.getCreatedAt()
        );

        softAssert.assertTrue(
                isValidAnyDateTime(getProfileResponseData.getUpdatedAt()),
                "Invalid datetime format: " + getProfileResponseData.getUpdatedAt()
        );
    }

    public static void validateSuccessResponse(Failure successResponse, String message) {
        Assert.assertTrue(
                successResponse.isSuccess(),
                "Success should be true"
        );

        Assert.assertTrue(
                successResponse.getMessage().toLowerCase().contains(message.toLowerCase()),
                "Response message is incorrect"
        );
    }

}
