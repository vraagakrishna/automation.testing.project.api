package tests.reqres;

import io.qameta.allure.*;
import io.restassured.response.Response;
import model.reqres.Failure;
import model.reqres.GetResource;
import model.reqres.GetResourceData;
import model.reqres.GetSingleResource;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.List;

import static requestbuilder.reqres.ReqResApiRequestBuilder.*;
import static utils.ValidateFormats.isValidIso8601;
import static utils.ValidateReqResUtils.*;

@Feature("Resource Endpoints")
public class ResourceTests extends ReqResApiTests {

    @Story("Get All Resources")
    @Description("Verify that requesting all resources without an API key returns an authentication error")
    @Test(priority = 1)
    @Severity(SeverityLevel.CRITICAL)
    public void getAllResourcesWithoutApiKey() {
        data.setResourceName("test");

        Failure failureResponse = getResources(false, data.getResourceName(), 1, 10)
                .then()
                .assertThat().statusCode(HttpStatus.SC_UNAUTHORIZED)
                .extract().as(Failure.class);

        validateFailedResponse(failureResponse, "Missing API key");
    }

    @Story("Get All Resources")
    @Description("Verify that the initial request to retrieve all resources is successful")
    @Test(priority = 2)
    @Severity(SeverityLevel.BLOCKER)
    public void getAllResources() {
        GetResource resource = getResources(true, data.getResourceName())
                .then()
                .assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(GetResource.class);

        validateResource(resource);

        // copy the resources to another list
        data.setResources(resource.getData());
    }

    @Story("Get All Resources")
    @Description("Verify that requesting all resources with page numbers is successful")
    @Test(priority = 3)
    @Severity(SeverityLevel.BLOCKER)
    public void getAllResourcesWithPage() {
        int page = 10;
        int perPage = randomNumberGenerator.generateRandomNumber(1, 10);
        GetResource resource = getResources(true, data.getResourceName(), page, perPage)
                .then()
                .assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(GetResource.class);

        validateResource(resource, page, perPage);
    }

    @Story("Get All Resources")
    @Description("Verify that requesting all resources with multiple pages is successful")
    @Test(priority = 4)
    @Severity(SeverityLevel.CRITICAL)
    public void getAllResourcesWithPagination() {
        int page = 1;
        int perPage = randomNumberGenerator.generateRandomNumber(1, 2);
        GetResource resource1 = getResources(true, data.getResourceName(), page, perPage)
                .then()
                .assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(GetResource.class);

        validateResource(resource1, page, perPage);

        GetResource resource2 = getResources(true, data.getResourceName(), page + 1, perPage)
                .then()
                .assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(GetResource.class);

        validateResource(resource2, page + 1, perPage);

        List<GetResourceData> resourceDataList1 = resource1.getData();
        List<GetResourceData> resourceDataList2 = resource2.getData();

        Assert.assertTrue(
                Collections.disjoint(resourceDataList1, resourceDataList2),
                "Pages have overlapping resources"
        );
    }

    @Story("Get Resource by ID")
    @Description("Verify that requesting a resource without an API key returns an authentication error")
    @Test(dependsOnMethods = "getAllResources", priority = 1)
    @Severity(SeverityLevel.CRITICAL)
    public void getResourceWithoutApiKey() {
        List<GetResourceData> resources = data.getResources();
        GetResourceData expectedResource = resources.get(1);
        Failure failureResponse = getResourceById(false, data.getResourceName(), expectedResource.getId())
                .then()
                .assertThat().statusCode(HttpStatus.SC_UNAUTHORIZED)
                .extract().as(Failure.class);

        validateFailedResponse(failureResponse, "Missing API key");
    }

    @Story("Get Resource by ID")
    @Description("Verify that getting resource by valid ID returns correct data and 200 OK")
    @Test(dependsOnMethods = "getAllResources", priority = 2)
    @Severity(SeverityLevel.BLOCKER)
    public void getResource() {
        List<GetResourceData> resources = data.getResources();
        GetResourceData expectedResource = resources.get(
                randomNumberGenerator.generateRandomNumber(1, resources.size() - 1)
        );
        GetSingleResource resourceResponse = getResourceById(true, data.getResourceName(), expectedResource.getId())
                .then()
                .assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(GetSingleResource.class);

        validateSingleResource(expectedResource, resourceResponse);
    }

    @Story("Get Resource by ID")
    @Description("Verify that getting resource with an invalid ID returns 404 Not Found")
    @Test(dependsOnMethods = "getAllResources", priority = 3)
    @Severity(SeverityLevel.NORMAL)
    public void getResourceByInvalidId() {
        List<GetResourceData> resources = data.getResources();
        int id = randomNumberGenerator.generateRandomNumber(resources.size() + 1, resources.size() + 20);
        getResourceById(true, data.getResourceName(), id)
                .then()
                .assertThat().statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Story("Put Resource by ID")
    @Description("Verify that updating (PUT) a resource without an API key returns an authentication error")
    @Test(dependsOnMethods = "getAllResources", priority = 4)
    @Severity(SeverityLevel.CRITICAL)
    public void putResourceWithoutApiKey() {
        List<GetResourceData> resources = data.getResources();
        GetResourceData expectedResource = resources.get(1);
        Failure failureResponse = putResourceById(false, data.getResourceName(), expectedResource.getId(), expectedResource)
                .then()
                .assertThat().statusCode(HttpStatus.SC_UNAUTHORIZED)
                .extract().as(Failure.class);

        validateFailedResponse(failureResponse, "Missing API key");
    }

    @Story("Put Resource by ID")
    @Description("Verify that updating (PUT) a resource without a payload returns an error")
    @Test(dependsOnMethods = "getAllResources", priority = 5)
    @Severity(SeverityLevel.CRITICAL)
    public void putResourceWithoutPayload() {
        List<GetResourceData> resources = data.getResources();
        GetResourceData expectedResource = resources.get(1);
        Failure failureResponse = putResourceById(true, data.getResourceName(), expectedResource.getId(), new GetResourceData())
                .then()
                .assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract().as(Failure.class);

        validateFailedResponse(failureResponse, "Missing data");
    }

    @Story("Put Resource by ID")
    @Description("Verify that updating (PUT) a resource with valid data returns 200 OK")
    @Test(dependsOnMethods = "getAllResources", priority = 6)
    @Severity(SeverityLevel.BLOCKER)
    public void putResource() {
        List<GetResourceData> resources = data.getResources();
        GetResourceData expectedResource = resources.get(
                randomNumberGenerator.generateRandomNumber(1, resources.size() - 1)
        );

        Response response = putResourceById(true, data.getResourceName(), expectedResource.getId(), expectedResource)
                .then()
                .assertThat().statusCode(HttpStatus.SC_OK)
                .extract().response();

        String updatedAt = response.jsonPath().getString("updatedAt");

        Assert.assertTrue(
                isValidIso8601(updatedAt),
                "Invalid ISO 8601 format: " + updatedAt
        );
    }

    @Story("Put Resource by ID")
    @Description("Verify that updating (PUT) a non-existent resource ID returns 404 Not Found")
    @Test(dependsOnMethods = "getAllResources", priority = 7)
    @Severity(SeverityLevel.NORMAL)
    public void putResourceByInvalidId() {
        List<GetResourceData> resources = data.getResources();
        int id = randomNumberGenerator.generateRandomNumber(resources.size() + 1, resources.size() + 20);
        putResourceById(true, data.getResourceName(), id, resources.get(0))
                .then()
                .assertThat().statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Story("Patch Resource by ID")
    @Description("Verify that updating (PATCH) a resource without an API key returns an authentication error")
    @Test(dependsOnMethods = "getAllResources", priority = 8)
    @Severity(SeverityLevel.CRITICAL)
    public void patchResourceWithoutApiKey() {
        List<GetResourceData> resources = data.getResources();
        GetResourceData expectedResource = resources.get(1);
        Failure failureResponse = patchResourceById(false, data.getResourceName(), expectedResource.getId(), expectedResource)
                .then()
                .assertThat().statusCode(HttpStatus.SC_UNAUTHORIZED)
                .extract().as(Failure.class);

        validateFailedResponse(failureResponse, "Missing API key");
    }

    @Story("Patch Resource by ID")
    @Description("Verify that updating (PATCH) a resource without a payload returns an error")
    @Test(dependsOnMethods = "getAllResources", priority = 9)
    @Severity(SeverityLevel.CRITICAL)
    public void patchResourceWithoutPayload() {
        List<GetResourceData> resources = data.getResources();
        GetResourceData expectedResource = resources.get(1);
        Failure failureResponse = patchResourceById(true, data.getResourceName(), expectedResource.getId(), new GetResourceData())
                .then()
                .assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract().as(Failure.class);

        validateFailedResponse(failureResponse, "Missing data");
    }

    @Story("Patch Resource by ID")
    @Description("Verify that updating (PATCH) a resource with valid data returns 200 OK")
    @Test(dependsOnMethods = "getAllResources", priority = 10)
    @Severity(SeverityLevel.BLOCKER)
    public void patchResource() {
        List<GetResourceData> resources = data.getResources();
        GetResourceData expectedResource = resources.get(
                randomNumberGenerator.generateRandomNumber(1, resources.size() - 1)
        );

        Response response = patchResourceById(true, data.getResourceName(), expectedResource.getId(), expectedResource)
                .then()
                .assertThat().statusCode(HttpStatus.SC_OK)
                .extract().response();

        String updatedAt = response.jsonPath().getString("updatedAt");

        Assert.assertTrue(
                isValidIso8601(updatedAt),
                "Invalid ISO 8601 format: " + updatedAt
        );
    }

    @Story("Patch Resource by ID")
    @Description("Verify that updating (PATCH) a non-existent resource ID returns 404 Not Found")
    @Test(dependsOnMethods = "getAllResources", priority = 11)
    @Severity(SeverityLevel.NORMAL)
    public void patchResourceByInvalidId() {
        List<GetResourceData> resources = data.getResources();
        int id = randomNumberGenerator.generateRandomNumber(resources.size() + 1, resources.size() + 20);
        patchResourceById(true, data.getResourceName(), id, resources.get(0))
                .then()
                .assertThat().statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Story("Delete Resource by ID")
    @Description("Verify that deleting a resource without an API key returns an authentication error")
    @Test(dependsOnMethods = "getAllResources", priority = 12)
    @Severity(SeverityLevel.CRITICAL)
    public void deleteResourceWithoutApiKey() {
        List<GetResourceData> resources = data.getResources();
        GetResourceData expectedResource = resources.get(1);
        Failure failureResponse = deleteResourceById(false, data.getResourceName(), expectedResource.getId())
                .then()
                .assertThat().statusCode(HttpStatus.SC_UNAUTHORIZED)
                .extract().as(Failure.class);

        validateFailedResponse(failureResponse, "Missing API key");
    }

    @Story("Delete Resource by ID")
    @Description("Verify that deleting a valid resource succeeds with 204 No Content")
    @Test(dependsOnMethods = "getAllResources", priority = 13)
    @Severity(SeverityLevel.BLOCKER)
    public void deleteResource() {
        List<GetResourceData> resources = data.getResources();
        GetResourceData expectedResource = resources.get(
                randomNumberGenerator.generateRandomNumber(1, resources.size() - 1)
        );
        deleteResourceById(true, data.getResourceName(), expectedResource.getId())
                .then()
                .assertThat().statusCode(HttpStatus.SC_NO_CONTENT);
    }

    @Story("Delete Resource by ID")
    @Description("Verify that deleting a non-existent resource ID returns 404 Not Found")
    @Test(dependsOnMethods = "getAllResources", priority = 14)
    @Severity(SeverityLevel.NORMAL)
    public void deleteResourceByInvalidId() {
        List<GetResourceData> resources = data.getResources();
        int id = randomNumberGenerator.generateRandomNumber(resources.size() + 1, resources.size() + 20);
        deleteResourceById(true, data.getResourceName(), id)
                .then()
                .assertThat().statusCode(HttpStatus.SC_NOT_FOUND);
    }

}
