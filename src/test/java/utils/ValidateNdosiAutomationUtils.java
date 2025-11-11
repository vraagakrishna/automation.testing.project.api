package utils;

import model.ndosiautomation.Failure;
import org.testng.Assert;

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

}
