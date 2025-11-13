package requestbuilder.ndosiautomation;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.ndosiautomation.LoginRequest;
import model.ndosiautomation.RegisterRequest;
import model.ndosiautomation.UpdateRequest;

import static io.restassured.RestAssured.given;

public class NdosiAutomationRequestBuilder extends BaseNdosiAutomationRequestBuilder {

    // <editor-fold desc="Public Methods">
    public static Response login(LoginRequest<Object> body) {
        RequestSpecification req = given()
                .spec(baseSpec)
                .basePath("/login");

        addDataToAllureReport(req);

        Response response = req
                .body(body)
                .log().all()
                .post()
                .then()
                .log().all()
                .extract().response();

        addDataToAllureReport(req, body, response);

        return response;
    }

    public static Response register(RegisterRequest<Object> body) {
        RequestSpecification req = given()
                .spec(baseSpec)
                .basePath("/register");

        addDataToAllureReport(req);

        Response response = req
                .body(body)
                .log().all()
                .post()
                .then()
                .log().all()
                .extract().response();

        addDataToAllureReport(req, body, response);

        return response;
    }

    public static Response getUserProfile(String token) {
        RequestSpecification req = given()
                .spec(baseSpec)
                .basePath("/profile");

        if (token != null)
            req.header("Authorization", "Bearer " + token);

        addDataToAllureReport(req);

        Response response = req
                .log().all()
                .get()
                .then()
                .log().all()
                .extract().response();

        addDataToAllureReport(req, null, response);

        return response;
    }

    public static Response updateUserProfile(String token, UpdateRequest<Object> body) {
        RequestSpecification req = given()
                .spec(baseSpec)
                .basePath("/profile");

        if (token != null)
            req.header("Authorization", "Bearer " + token);

        addDataToAllureReport(req);

        Response response = req
                .body(body)
                .log().all()
                .put()
                .then()
                .log().all()
                .extract().response();

        addDataToAllureReport(req, body, response);

        return response;
    }
    // </editor-fold>

}
