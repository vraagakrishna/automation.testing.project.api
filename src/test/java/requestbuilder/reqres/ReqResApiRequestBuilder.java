package requestbuilder.reqres;

import io.restassured.internal.RequestSpecificationImpl;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.reqres.GetResourceData;
import model.reqres.LoginRegisterUser;
import org.testng.Assert;
import utils.AllureUtils;

import static io.restassured.RestAssured.given;

public class ReqResApiRequestBuilder extends BaseReqResApiRequestBuilder {

    // <editor-fold desc="Class Fields">
    private static final String appId = System.getProperty("REQRES_API_KEY", System.getenv("REQRES_API_KEY"));

    private static final String appKey = "x-api-key";
    // </editor-fold>

    // <editor-fold desc="Public Methods">
    public static Response getResources(boolean includeAppId, String resourceName) {
        RequestSpecification req = given()
                .spec(baseSpec)
                .basePath("/{resource}");

        if (includeAppId)
            req.header(appKey, appId);

        Response response = req
                .pathParam("resource", resourceName)
                .log().all()
                .get()
                .then()
                .log().all()
                .extract().response();

        AllureUtils.attachUri(
                ((RequestSpecificationImpl) req).getMethod() + " " + ((RequestSpecificationImpl) req).getURI()
        );
        AllureUtils.attachResponse(response.getBody().asString());

        validateHeaders(response);

        return response;
    }

    public static Response getResources(boolean includeAppId, String resourceName, int page, int perPage) {
        RequestSpecification req = given()
                .spec(baseSpec)
                .basePath("/{resource}");

        if (includeAppId)
            req.header(appKey, appId);

        Response response = req
                .pathParam("resource", resourceName)
                .queryParam("page", page)
                .queryParam("per_page", perPage)
                .log().all()
                .get()
                .then()
                .log().all()
                .extract().response();

        AllureUtils.attachUri(
                ((RequestSpecificationImpl) req).getMethod() + " " + ((RequestSpecificationImpl) req).getURI()
        );
        AllureUtils.attachResponse(response.getBody().asString());

        validateHeaders(response);

        return response;
    }

    public static Response getUsers(boolean includeAppId) {
        RequestSpecification req = getUsersRequestSpecification(includeAppId, null);

        Response response = req
                .log().all()
                .get()
                .then()
                .log().all()
                .extract().response();

        AllureUtils.attachUri(
                ((RequestSpecificationImpl) req).getMethod() + " " + ((RequestSpecificationImpl) req).getURI()
        );
        AllureUtils.attachResponse(response.getBody().asString());

        validateHeaders(response);

        return response;
    }

    public static Response getUsersWithDelay(int delayInSeconds) {
        RequestSpecification req = getUsersRequestSpecification(true, delayInSeconds);

        Response response = req
                .log().all()
                .get()
                .then()
                .log().all()
                .extract().response();

        AllureUtils.attachUri(
                ((RequestSpecificationImpl) req).getMethod() + " " + ((RequestSpecificationImpl) req).getURI()
        );
        AllureUtils.attachResponse(response.getBody().asString());

        validateHeaders(response);

        return response;
    }

    public static Response getUsers(boolean includeAppId, int page, int perPage) {
        RequestSpecification req = given()
                .spec(baseSpec)
                .basePath("/users");

        if (includeAppId)
            req.header(appKey, appId);

        Response response = req
                .queryParam("page", page)
                .queryParam("per_page", perPage)
                .log().all()
                .get()
                .then()
                .log().all()
                .extract().response();

        AllureUtils.attachUri(
                ((RequestSpecificationImpl) req).getMethod() + " " + ((RequestSpecificationImpl) req).getURI()
        );
        AllureUtils.attachResponse(response.getBody().asString());

        validateHeaders(response);

        return response;
    }

    public static Response getUserById(boolean includeAppId, int userId) {
        RequestSpecification req = given()
                .spec(baseSpec)
                .basePath("/users/" + userId);

        if (includeAppId)
            req.header(appKey, appId);

        Response response = req
                .log().all()
                .get()
                .then()
                .log().all()
                .extract().response();

        AllureUtils.attachUri(
                ((RequestSpecificationImpl) req).getMethod() + " " + ((RequestSpecificationImpl) req).getURI()
        );
        AllureUtils.attachResponse(response.getBody().asString());

        validateHeaders(response);

        return response;
    }

    public static Response putUserById(boolean includeAppId, int userId) {
        RequestSpecification req = given()
                .spec(baseSpec)
                .basePath("/users/" + userId);

        if (includeAppId)
            req.header(appKey, appId);

        Response response = req
                .body("{}")
                .log().all()
                .put()
                .then()
                .log().all()
                .extract().response();

        AllureUtils.attachUri(
                ((RequestSpecificationImpl) req).getMethod() + " " + ((RequestSpecificationImpl) req).getURI()
        );
        AllureUtils.attachResponse(response.getBody().asString());

        validateHeaders(response);

        return response;
    }

    public static Response patchUserById(boolean includeAppId, int userId) {
        RequestSpecification req = given()
                .spec(baseSpec)
                .basePath("/users/" + userId);

        if (includeAppId)
            req.header(appKey, appId);

        Response response = req
                .body("{}")
                .log().all()
                .patch()
                .then()
                .log().all()
                .extract().response();

        AllureUtils.attachUri(
                ((RequestSpecificationImpl) req).getMethod() + " " + ((RequestSpecificationImpl) req).getURI()
        );
        AllureUtils.attachResponse(response.getBody().asString());

        return response;
    }

    public static Response deleteUserById(boolean includeAppId, int userId) {
        RequestSpecification req = given()
                .spec(baseSpec)
                .basePath("/users/" + userId);

        if (includeAppId)
            req.header(appKey, appId);

        Response response = req
                .log().all()
                .delete()
                .then()
                .log().all()
                .extract().response();

        AllureUtils.attachUri(
                ((RequestSpecificationImpl) req).getMethod() + " " + ((RequestSpecificationImpl) req).getURI()
        );
        AllureUtils.attachResponse(response.getBody().asString());

        return response;
    }

    public static Response getResourceById(boolean includeAppId, String resourceName, int resourceId) {
        RequestSpecification req = given()
                .spec(baseSpec)
                .basePath("/{resource}/" + resourceId);

        if (includeAppId)
            req.header(appKey, appId);

        Response response = req
                .pathParam("resource", resourceName)
                .log().all()
                .get()
                .then()
                .log().all()
                .extract().response();

        AllureUtils.attachUri(
                ((RequestSpecificationImpl) req).getMethod() + " " + ((RequestSpecificationImpl) req).getURI()
        );
        AllureUtils.attachResponse(response.getBody().asString());

        validateHeaders(response);

        return response;
    }

    public static Response putResourceById(boolean includeAppId, String resourceName, int resourceId, GetResourceData payload) {
        RequestSpecification req = given()
                .spec(baseSpec)
                .basePath("/{resource}/" + resourceId);

        if (includeAppId)
            req.header(appKey, appId);

        Response response = req
                .pathParam("resource", resourceName)
                .body(payload)
                .log().all()
                .put()
                .then()
                .log().all()
                .extract().response();

        AllureUtils.attachUri(
                ((RequestSpecificationImpl) req).getMethod() + " " + ((RequestSpecificationImpl) req).getURI()
        );
        AllureUtils.attachRequest(payload.toString());
        AllureUtils.attachResponse(response.getBody().asString());

        return response;
    }

    public static Response patchResourceById(boolean includeAppId, String resourceName, int resourceId, GetResourceData payload) {
        RequestSpecification req = given()
                .spec(baseSpec)
                .basePath("/{resource}/" + resourceId);

        if (includeAppId)
            req.header(appKey, appId);

        Response response = req
                .pathParam("resource", resourceName)
                .body(payload)
                .log().all()
                .patch()
                .then()
                .log().all()
                .extract().response();

        AllureUtils.attachUri(
                ((RequestSpecificationImpl) req).getMethod() + " " + ((RequestSpecificationImpl) req).getURI()
        );
        AllureUtils.attachRequest(payload.toString());
        AllureUtils.attachResponse(response.getBody().asString());

        validateHeaders(response);

        return response;
    }

    public static Response deleteResourceById(boolean includeAppId, String resourceName, int resourceId) {
        RequestSpecification req = given()
                .spec(baseSpec)
                .basePath("/{resource}/" + resourceId);

        if (includeAppId)
            req.header(appKey, appId);

        Response response = req
                .pathParam("resource", resourceName)
                .log().all()
                .delete()
                .then()
                .log().all()
                .extract().response();

        AllureUtils.attachUri(
                ((RequestSpecificationImpl) req).getMethod() + " " + ((RequestSpecificationImpl) req).getURI()
        );
        AllureUtils.attachResponse(response.getBody().asString());

        return response;
    }

    public static Response login(boolean includeAppId, LoginRegisterUser<Object> body) {
        RequestSpecification req = given()
                .spec(baseSpec)
                .basePath("/login");

        if (includeAppId)
            req.header(appKey, appId);

        Response response = req
                .body(body)
                .log().all()
                .post()
                .then()
                .log().all()
                .extract().response();

        AllureUtils.attachUri(
                ((RequestSpecificationImpl) req).getMethod() + " " + ((RequestSpecificationImpl) req).getURI()
        );
        AllureUtils.attachRequest(body.toString());
        AllureUtils.attachResponse(response.getBody().asString());

        validateHeaders(response);

        return response;
    }

    public static Response register(boolean includeAppId, LoginRegisterUser<Object> body) {
        RequestSpecification req = given()
                .spec(baseSpec)
                .basePath("/register");

        if (includeAppId)
            req.header(appKey, appId);

        Response response = req
                .body(body)
                .log().all()
                .post()
                .then()
                .log().all()
                .extract().response();

        AllureUtils.attachUri(
                ((RequestSpecificationImpl) req).getMethod() + " " + ((RequestSpecificationImpl) req).getURI()
        );
        AllureUtils.attachRequest(body.toString());
        AllureUtils.attachResponse(response.getBody().asString());

        validateHeaders(response);

        return response;
    }

    public static Response logout(boolean includeAppId) {
        RequestSpecification req = given()
                .spec(baseSpec)
                .basePath("/logout");

        if (includeAppId)
            req.header(appKey, appId);

        Response response = req
                .body("{}")
                .log().all()
                .post()
                .then()
                .log().all()
                .extract().response();

        AllureUtils.attachUri(
                ((RequestSpecificationImpl) req).getMethod() + " " + ((RequestSpecificationImpl) req).getURI()
        );
        AllureUtils.attachResponse(response.getBody().asString());

        validateHeaders(response);

        return response;
    }
    // </editor-fold>

    // <editor-fold desc="Private Methods">
    private static RequestSpecification getUsersRequestSpecification(boolean includeAppId, Integer delayInSeconds) {
        RequestSpecification req = given()
                .spec(baseSpec)
                .basePath("/users");

        if (includeAppId)
            req.header(appKey, appId);

        if (delayInSeconds != null)
            req.queryParam("delay", 10);

        return req;
    }

    private static void validateHeaders(Response response) {
        Assert.assertTrue(
                response.headers().hasHeaderWithName("Content-Type"),
                "Content-Type header is missing"
        );

        Assert.assertEquals(
                response.getHeader("Content-Type"),
                "application/json; charset=utf-8",
                "Unexpected Content-Type"
        );

        String poweredBy = response.getHeader("X-Powered-By");
        if (poweredBy != null) {
            Assert.assertEquals(
                    poweredBy,
                    "ReqRes.in - Deploy backends in 30 seconds",
                    "Unexpected X-Powered-By header"
            );
        }

        String reqResMessage = response.getHeader("X-Reqres-Message");
        if (reqResMessage != null) {
            Assert.assertEquals(
                    reqResMessage,
                    "This API is powered by ReqRes. Deploy your own backend in 30 seconds!",
                    "Unexpected X-Reqres-Message"
            );
        }

        Assert.assertNotNull(
                response.getHeader("Access-Control-Allow-Headers"),
                "Access-Control-Allow-Headers is not found"
        );

        Assert.assertNotNull(
                response.getHeader("Access-Control-Allow-Methods"),
                "Access-Control-Allow-Methods is not found"
        );

        Assert.assertNotNull(
                response.getHeader("Access-Control-Allow-Origin"),
                "Access-Control-Allow-Origin is not found"
        );
    }
    // </editor-fold>

}
