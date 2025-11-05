package tests.reqres;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import model.reqres.Failure;
import model.reqres.GetResource;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

import static requestbuilder.reqres.ReqResApiRequestBuilder.getResources;
import static utils.ValidateReqResUtils.validateFailedResponse;
import static utils.ValidateReqResUtils.validateResponse;

@Story("Resource Tests")
public class ResourceTests extends ReqResApiTests {

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

}
