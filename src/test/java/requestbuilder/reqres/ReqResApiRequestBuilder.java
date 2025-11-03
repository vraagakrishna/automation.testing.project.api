package requestbuilder.reqres;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.reqres.CustomEndpoint;
import model.reqres.LoginRegisterUser;
import model.reqres.ProSubscription;
import utils.AllureUtils;

import static io.restassured.RestAssured.given;

public class ReqResApiRequestBuilder extends BaseReqResApiRequestBuilder {

    private static final String appId = System.getProperty("REQRES_API_KEY", System.getenv("REQRES_API_KEY"));

    private static final String appKey = "x-api-key";

    public static Response getResources(boolean includeAppId, int page, int perPage) {
        AllureUtils.attachUri("GET /");

        RequestSpecification req = given()
                .spec(baseSpec)
                .basePath("/");

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

        AllureUtils.attachResponse(response.getBody().asString());

        return response;
    }

    public static Response getUsers(boolean includeAppId) {
        AllureUtils.attachUri("GET /users");

        RequestSpecification req = given()
                .spec(baseSpec)
                .basePath("/users");

        if (includeAppId)
            req.header(appKey, appId);

        Response response = req
                .log().all()
                .get()
                .then()
                .log().all()
                .extract().response();

        AllureUtils.attachResponse(response.getBody().asString());

        return response;
    }

    public static Response getUserById(boolean includeAppId, String userId) {
        AllureUtils.attachUri("GET /users/" + (userId != null ? userId : ""));

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

        AllureUtils.attachResponse(response.getBody().asString());

        return response;
    }

    public static Response putUserById(boolean includeAppId, String userId) {
        AllureUtils.attachUri("PUT /users/" + (userId != null ? userId : ""));

        RequestSpecification req = given()
                .spec(baseSpec)
                .basePath("/users/" + userId);

        if (includeAppId)
            req.header(appKey, appId);

        Response response = req
                .log().all()
                .put()
                .then()
                .log().all()
                .extract().response();

        AllureUtils.attachResponse(response.getBody().asString());

        return response;
    }

    public static Response patchUserById(boolean includeAppId, String userId) {
        AllureUtils.attachUri("PATCH /users/" + (userId != null ? userId : ""));

        RequestSpecification req = given()
                .spec(baseSpec)
                .basePath("/users/" + userId);

        if (includeAppId)
            req.header(appKey, appId);

        Response response = req
                .log().all()
                .patch()
                .then()
                .log().all()
                .extract().response();

        AllureUtils.attachResponse(response.getBody().asString());

        return response;
    }

    public static Response deleteUserById(boolean includeAppId, String userId) {
        AllureUtils.attachUri("DELETE /users/" + (userId != null ? userId : ""));

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

        AllureUtils.attachResponse(response.getBody().asString());

        return response;
    }

    public static Response getResourceById(boolean includeAppId, String resourceId) {
        AllureUtils.attachUri("GET /{resource}" + (resourceId != null ? resourceId : ""));

        RequestSpecification req = given()
                .spec(baseSpec)
                .basePath("/{resource}/" + resourceId);

        if (includeAppId)
            req.header(appKey, appId);

        Response response = req
                .log().all()
                .get()
                .then()
                .log().all()
                .extract().response();

        AllureUtils.attachResponse(response.getBody().asString());

        return response;
    }

    public static Response putResourceById(boolean includeAppId, String resourceId) {
        AllureUtils.attachUri("PUT /{resource}" + (resourceId != null ? resourceId : ""));

        RequestSpecification req = given()
                .spec(baseSpec)
                .basePath("/{resource}/" + resourceId);

        if (includeAppId)
            req.header(appKey, appId);

        Response response = req
                .log().all()
                .put()
                .then()
                .log().all()
                .extract().response();

        AllureUtils.attachResponse(response.getBody().asString());

        return response;
    }

    public static Response patchResourceById(boolean includeAppId, String resourceId) {
        AllureUtils.attachUri("PATCH /{resource}" + (resourceId != null ? resourceId : ""));

        RequestSpecification req = given()
                .spec(baseSpec)
                .basePath("/{resource}/" + resourceId);

        if (includeAppId)
            req.header(appKey, appId);

        Response response = req
                .log().all()
                .patch()
                .then()
                .log().all()
                .extract().response();

        AllureUtils.attachResponse(response.getBody().asString());

        return response;
    }

    public static Response deleteResourceById(boolean includeAppId, String resourceId) {
        AllureUtils.attachUri("DELETE /{resource}" + (resourceId != null ? resourceId : ""));

        RequestSpecification req = given()
                .spec(baseSpec)
                .basePath("/{resource}/" + resourceId);

        if (includeAppId)
            req.header(appKey, appId);

        Response response = req
                .log().all()
                .delete()
                .then()
                .log().all()
                .extract().response();

        AllureUtils.attachResponse(response.getBody().asString());

        return response;
    }

    public static Response login(boolean includeAppId, LoginRegisterUser<Object> body) {
        AllureUtils.attachUri("POST /login");
        AllureUtils.attachRequest(body.toString());

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

        AllureUtils.attachResponse(response.getBody().asString());

        return response;
    }

    public static Response register(boolean includeAppId, LoginRegisterUser<Object> body) {
        AllureUtils.attachUri("POST /register");
        AllureUtils.attachRequest(body.toString());

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

        AllureUtils.attachResponse(response.getBody().asString());

        return response;
    }

    public static Response logout(boolean includeAppId) {
        AllureUtils.attachUri("POST /logout");

        RequestSpecification req = given()
                .spec(baseSpec)
                .basePath("/logout");

        if (includeAppId)
            req.header(appKey, appId);

        Response response = req
                .log().all()
                .post()
                .then()
                .log().all()
                .extract().response();

        AllureUtils.attachResponse(response.getBody().asString());

        return response;
    }

    public static Response linkClerkWithProSubscription(boolean includeAppId, ProSubscription<Object> body) {
        AllureUtils.attachUri("POST /clerk/link-pro");
        AllureUtils.attachRequest(body.toString());

        RequestSpecification req = given()
                .spec(baseSpec)
                .basePath("/clerk/link-pro");

        if (includeAppId)
            req.header(appKey, appId);

        Response response = req
                .body(body)
                .log().all()
                .post()
                .then()
                .log().all()
                .extract().response();

        AllureUtils.attachResponse(response.getBody().asString());

        return response;
    }

    public static Response autoLinkClerkWithProSubscription(boolean includeAppId) {
        AllureUtils.attachUri("POST /clerk/auto-link-pro");

        RequestSpecification req = given()
                .spec(baseSpec)
                .basePath("/clerk/auto-link-pro");

        if (includeAppId)
            req.header(appKey, appId);

        Response response = req
                .log().all()
                .post()
                .then()
                .log().all()
                .extract().response();

        AllureUtils.attachResponse(response.getBody().asString());

        return response;
    }

    public static Response getUserSubscriptionStatus(boolean includeAppId) {
        AllureUtils.attachUri("GET /clerk/subscription-status");

        RequestSpecification req = given()
                .spec(baseSpec)
                .basePath("/clerk/subscription-status");

        if (includeAppId)
            req.header(appKey, appId);

        Response response = req
                .log().all()
                .get()
                .then()
                .log().all()
                .extract().response();

        AllureUtils.attachResponse(response.getBody().asString());

        return response;
    }

    public static Response getPaymentStatus(boolean includeAppId) {
        AllureUtils.attachUri("GET /clerk/payment-status");

        RequestSpecification req = given()
                .spec(baseSpec)
                .basePath("/clerk/payment-status");

        if (includeAppId)
            req.header(appKey, appId);

        Response response = req
                .log().all()
                .get()
                .then()
                .log().all()
                .extract().response();

        AllureUtils.attachResponse(response.getBody().asString());

        return response;
    }

    public static Response unlinkProSubscription(boolean includeAppId) {
        AllureUtils.attachUri("POST /clerk/unlink-pro");

        RequestSpecification req = given()
                .spec(baseSpec)
                .basePath("/clerk/unlink-pro");

        if (includeAppId)
            req.header(appKey, appId);

        Response response = req
                .log().all()
                .post()
                .then()
                .log().all()
                .extract().response();

        AllureUtils.attachResponse(response.getBody().asString());

        return response;
    }

    public static Response getCustomEndpoints(boolean includeAppId) {
        AllureUtils.attachUri("GET /custom-endpoints");

        RequestSpecification req = given()
                .spec(baseSpec)
                .basePath("/custom-endpoints");

        if (includeAppId)
            req.header(appKey, appId);

        Response response = req
                .log().all()
                .get()
                .then()
                .log().all()
                .extract().response();

        AllureUtils.attachResponse(response.getBody().asString());

        return response;
    }

    public static Response createCustomEndpoint(boolean includeAppId, CustomEndpoint<Object> endpoint) {
        AllureUtils.attachUri("POST /custom-endpoints");
        AllureUtils.attachRequest(endpoint.toString());

        RequestSpecification req = given()
                .spec(baseSpec)
                .basePath("/custom-endpoints");

        if (includeAppId)
            req.header(appKey, appId);

        Response response = req
                .body(endpoint)
                .log().all()
                .post()
                .then()
                .log().all()
                .extract().response();

        AllureUtils.attachResponse(response.getBody().asString());

        return response;
    }

    public static Response getCustomEndpointById(boolean includeAppId, Integer endpointId) {
        AllureUtils.attachUri("GET /custom-endpoints/" + (endpointId != null ? endpointId : ""));

        RequestSpecification req = given()
                .spec(baseSpec)
                .basePath("/custom-endpoints/" + endpointId);

        if (includeAppId)
            req.header(appKey, appId);

        Response response = req
                .log().all()
                .get()
                .then()
                .log().all()
                .extract().response();

        AllureUtils.attachResponse(response.getBody().asString());

        return response;
    }

    public static Response putCustomEndpoint(boolean includeAppId, Integer endpointId, CustomEndpoint<Object> endpoint) {
        AllureUtils.attachUri("PUT /custom-endpoints/" + (endpointId != null ? endpointId : ""));
        AllureUtils.attachRequest(endpoint.toString());

        RequestSpecification req = given()
                .spec(baseSpec)
                .basePath("/custom-endpoints/" + endpointId);

        if (includeAppId)
            req.header(appKey, appId);

        Response response = req
                .body(endpoint)
                .log().all()
                .put()
                .then()
                .log().all()
                .extract().response();

        AllureUtils.attachResponse(response.getBody().asString());

        return response;
    }

    public static Response deleteCustomEndpointById(boolean includeAppId, Integer endpointId) {
        AllureUtils.attachUri("DELETE /custom-endpoints/" + (endpointId != null ? endpointId : ""));

        RequestSpecification req = given()
                .spec(baseSpec)
                .basePath("/custom-endpoints/" + endpointId);

        if (includeAppId)
            req.header(appKey, appId);

        Response response = req
                .log().all()
                .delete()
                .then()
                .log().all()
                .extract().response();

        AllureUtils.attachResponse(response.getBody().asString());

        return response;
    }

    public static Response executeCustomGetEndpoint(boolean includeAppId, String path) {
        AllureUtils.attachUri("GET /custom/" + path);

        RequestSpecification req = given()
                .spec(baseSpec)
                .basePath("/custom/" + path);

        if (includeAppId)
            req.header(appKey, appId);

        Response response = req
                .log().all()
                .get()
                .then()
                .log().all()
                .extract().response();

        AllureUtils.attachResponse(response.getBody().asString());

        return response;
    }

    public static Response executeCustomPostEndpoint(boolean includeAppId, String path) {
        AllureUtils.attachUri("POST /custom/" + path);

        RequestSpecification req = given()
                .spec(baseSpec)
                .basePath("/custom/" + path);

        if (includeAppId)
            req.header(appKey, appId);

        Response response = req
                .log().all()
                .post()
                .then()
                .log().all()
                .extract().response();

        AllureUtils.attachResponse(response.getBody().asString());

        return response;
    }

    public static Response executeCustomPutEndpoint(boolean includeAppId, String path) {
        AllureUtils.attachUri("PUT /custom/" + path);

        RequestSpecification req = given()
                .spec(baseSpec)
                .basePath("/custom/" + path);

        if (includeAppId)
            req.header(appKey, appId);

        Response response = req
                .log().all()
                .put()
                .then()
                .log().all()
                .extract().response();

        AllureUtils.attachResponse(response.getBody().asString());

        return response;
    }

    public static Response executeCustomPatchEndpoint(boolean includeAppId, String path) {
        AllureUtils.attachUri("PATCH /custom/" + path);

        RequestSpecification req = given()
                .spec(baseSpec)
                .basePath("/custom/" + path);

        if (includeAppId)
            req.header(appKey, appId);

        Response response = req
                .log().all()
                .patch()
                .then()
                .log().all()
                .extract().response();

        AllureUtils.attachResponse(response.getBody().asString());

        return response;
    }

    public static Response executeCustomDeleteEndpoint(boolean includeAppId, String path) {
        AllureUtils.attachUri("DELETE /custom/" + path);

        RequestSpecification req = given()
                .spec(baseSpec)
                .basePath("/custom/" + path);

        if (includeAppId)
            req.header(appKey, appId);

        Response response = req
                .log().all()
                .delete()
                .then()
                .log().all()
                .extract().response();

        AllureUtils.attachResponse(response.getBody().asString());

        return response;
    }

}
