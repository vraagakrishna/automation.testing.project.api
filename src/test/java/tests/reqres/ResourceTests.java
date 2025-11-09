package tests.reqres;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
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

@Story("Resource Tests")
public class ResourceTests extends ReqResApiTests {

    @Description("Get all resources without api key")
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

    @Description("Get all resources")
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

    @Description("Get all resources with page restriction")
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

    @Description("Get all resources with pagination")
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

    @Description("Get a resource without api key")
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

    @Description("Get a resource")
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

    @Description("Get a resource by invalid id")
    @Test(dependsOnMethods = "getAllResources", priority = 3)
    @Severity(SeverityLevel.NORMAL)
    public void getResourceByInvalidId() {
        List<GetResourceData> resources = data.getResources();
        int id = randomNumberGenerator.generateRandomNumber(resources.size() + 1, resources.size() + 20);
        getResourceById(true, data.getResourceName(), id)
                .then()
                .assertThat().statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Description("Put a resource without api key")
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

    @Description("Put a resource without payload")
    @Test(dependsOnMethods = "getAllResources", priority = 5)
    @Severity(SeverityLevel.CRITICAL)
    public void putResourceWithoutPayload() {
        List<GetResourceData> resources = data.getResources();
        GetResourceData expectedResource = resources.get(1);
        Failure failureResponse = putResourceById(true, data.getResourceName(), expectedResource.getId(), new GetResourceData())
                .then()
                .assertThat().statusCode(HttpStatus.SC_UNAUTHORIZED)
                .extract().as(Failure.class);

        validateFailedResponse(failureResponse, "Missing API key");
    }

    @Description("Put a resource")
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

    @Description("Put a resource by invalid id")
    @Test(dependsOnMethods = "getAllResources", priority = 7)
    @Severity(SeverityLevel.NORMAL)
    public void putResourceByInvalidId() {
        List<GetResourceData> resources = data.getResources();
        int id = randomNumberGenerator.generateRandomNumber(resources.size() + 1, resources.size() + 20);
        putResourceById(true, data.getResourceName(), id, resources.get(0))
                .then()
                .assertThat().statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Description("Patch a resource without api key")
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

    @Description("Patch a resource without payload")
    @Test(dependsOnMethods = "getAllResources", priority = 9)
    @Severity(SeverityLevel.CRITICAL)
    public void patchResourceWithoutPayload() {
        List<GetResourceData> resources = data.getResources();
        GetResourceData expectedResource = resources.get(1);
        Failure failureResponse = patchResourceById(false, data.getResourceName(), expectedResource.getId(), new GetResourceData())
                .then()
                .assertThat().statusCode(HttpStatus.SC_UNAUTHORIZED)
                .extract().as(Failure.class);

        validateFailedResponse(failureResponse, "Missing API key");
    }

    @Description("Patch a resource")
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

    @Description("Patch a resource by invalid id")
    @Test(dependsOnMethods = "getAllResources", priority = 11)
    @Severity(SeverityLevel.NORMAL)
    public void patchResourceByInvalidId() {
        List<GetResourceData> resources = data.getResources();
        int id = randomNumberGenerator.generateRandomNumber(resources.size() + 1, resources.size() + 20);
        patchResourceById(true, data.getResourceName(), id, resources.get(0))
                .then()
                .assertThat().statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Description("Delete a resource without api key")
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

    @Description("Delete a resource")
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

    @Description("Delete a resource by invalid id")
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
