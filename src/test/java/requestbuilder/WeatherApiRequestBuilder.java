package requestbuilder;

import io.restassured.response.Response;
import model.PostStation;

import static common.BasePaths.WeatherBaseUrl;
import static io.restassured.RestAssured.given;

public class WeatherApiRequestBuilder {

    private static final String appId = System.getProperty("WEATHER_API_KEY", System.getenv("WEATHER_API_KEY"));

    public static Response getStations() {
        return given()
                .baseUri(WeatherBaseUrl)
                .basePath("/stations")
                .contentType("application/json")
                .queryParam("appid", appId)
                .log().all()
                .get()
                .then()
                .log().all()
                .extract().response();
    }

    public static Response registerStation(PostStation<Object> station) {
        return given()
                .baseUri(WeatherBaseUrl)
                .basePath("/stations")
                .contentType("application/json")
                .queryParam("appid", appId)
                .body(station)
                .log().all()
                .post()
                .then()
                .log().all()
                .extract().response();
    }

}
