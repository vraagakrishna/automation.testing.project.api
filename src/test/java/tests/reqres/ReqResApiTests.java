package tests.reqres;

import io.qameta.allure.*;
import model.reqres.Failure;
import model.reqres.GetResource;
import model.reqres.GetResourceData;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.RandomNumberGenerator;

import java.util.List;

import static requestbuilder.reqres.ReqResApiRequestBuilder.getResources;

@Test
@Feature("ReqRes")
@Story("Keep reviews moving with living APIs")
public class ReqResApiTests {

    RandomNumberGenerator randomNumberGenerator = new RandomNumberGenerator();

    @Description("Get all resources without api key")
    @Test(priority = 1)
    @Severity(SeverityLevel.BLOCKER)
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
        int page = 10;
        int perPage = (int) randomNumberGenerator.generateRandomNumber(0, 10);
        GetResource resource = getResources(true, "flower", page, perPage)
                .then()
                .assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(GetResource.class);

        validateAllResponses(resource, page, perPage);
    }

    // <editor-fold desc="Private Methods">
    private void validateAllResponses(GetResource resource, int page, int perPage) {
        Assert.assertNotNull(resource, "Response should not be null");

        // validate top-level fields
        Assert.assertTrue(resource.getPage() >= 0, "Page should be greater than 0");
        Assert.assertTrue(resource.getPerPage() >= 0, "Per page should be greater than 0");
        Assert.assertTrue(resource.getTotal() >= 0, "Total should be greater than 0");
        Assert.assertTrue(resource.getTotalPages() >= 0, "Total pages should be greater than 0");

        // validate data list
        List<GetResourceData> resourceDataList = resource.getData();
        Assert.assertNotNull(resourceDataList, "Response data should not be null");
        Assert.assertFalse(resourceDataList.isEmpty(), "Data list should not be empty");
        Assert.assertTrue(resourceDataList.size() <= perPage, "Data list has too many items");

        for (GetResourceData resourceData : resourceDataList) {
            Assert.assertNotNull(resourceData.getId(), "Id should not be null");
            Assert.assertNotNull(resourceData.getName(), "Name should not be null");
            Assert.assertNotNull(resourceData.getColor(), "Color should not be null");
            Assert.assertNotNull(resourceData.getYear(), "Year should not be null");
            Assert.assertNotNull(resourceData.getPantoneValue(), "Pantone value should not be null");
        }
    }

    private void validateFailedResponse(Failure failureResponse, String message) {
        Assert.assertTrue(
                failureResponse.getError().toLowerCase().contains(message.toLowerCase()),
                "Response message is incorrect"
        );
    }
    // </editor-fold>

}
