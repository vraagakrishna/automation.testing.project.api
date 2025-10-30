package requestbuilder;

import io.restassured.response.Response;
import model.TestModel;

import static common.BasePaths.WeatherBaseUrl;
import static io.restassured.RestAssured.given;

public class WeatherApiRequestBuilder {

    public static Response testRequest() {
        TestModel body = new TestModel("a", "b", 2);

        return given()
                .basePath(WeatherBaseUrl)
                .basePath("/test")
                .contentType("application/json")
                .body(body)
                .log().all()
                .post()
                .then()
                .log().all()
                .extract().response();
    }

}
