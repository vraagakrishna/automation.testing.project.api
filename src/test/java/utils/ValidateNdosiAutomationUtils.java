package utils;

import model.ndosiautomation.*;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import static utils.ValidateFormats.isValidIso8601;

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
                isValidIso8601(registerResponseData.getCreatedAt()),
                "Invalid ISO 8601 format: " + registerResponseData.getCreatedAt()
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
                isValidIso8601(loginResponseUserData.getCreatedAt()),
                "Invalid ISO 8601 format: " + loginResponseUserData.getCreatedAt()
        );

        softAssert.assertTrue(
                isValidIso8601(loginResponseUserData.getUpdatedAt()),
                "Invalid ISO 8601 format: " + loginResponseUserData.getUpdatedAt()
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

}
