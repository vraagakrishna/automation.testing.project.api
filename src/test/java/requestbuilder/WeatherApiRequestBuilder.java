package requestbuilder;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.weatherapi.PostStation;

import static io.restassured.RestAssured.given;

public class WeatherApiRequestBuilder extends BaseWeatherApiRequestBuilder {

    private static final String appId = System.getProperty("WEATHER_API_KEY", System.getenv("WEATHER_API_KEY"));

    public static Response getStations(boolean includeAppId) {
        RequestSpecification req = given()
                .spec(baseSpec)
                .basePath("/stations");

        if (includeAppId)
            req.queryParam("appid", appId);

        return req
                .log().all()
                .get()
                .then()
                .log().all()
                .extract().response();
    }

    public static Response registerStation(boolean includeAppId, PostStation<Object> station) {
        RequestSpecification req = given()
                .spec(baseSpec)
                .basePath("/stations");

        if (includeAppId)
            req.queryParam("appid", appId);

        return req
                .body(station)
                .log().all()
                .post()
                .then()
                .log().all()
                .extract().response();
    }

    public static Response getStationById(boolean includeAppId, String stationId) {
        RequestSpecification req = given()
                .spec(baseSpec)
                .basePath("/stations/" + stationId);

        if (includeAppId)
            req.queryParam("appid", appId);

        return req
                .log().all()
                .get()
                .then()
                .log().all()
                .extract().response();
    }

    public static Response updateStationById(boolean includeAppId, String stationId, PostStation<Object> station) {
        RequestSpecification req = given()
                .spec(baseSpec)
                .basePath("/stations/" + stationId);

        if (includeAppId)
            req.queryParam("appid", appId);

        return req
                .body(station)
                .log().all()
                .put()
                .then()
                .log().all()
                .extract().response();
    }

    public static Response deleteStationById(boolean includeAppId, String stationId) {
        RequestSpecification req = given()
                .spec(baseSpec)
                .basePath("/stations/" + stationId);

        if (includeAppId)
            req.queryParam("appid", appId);

        return req
                .log().all()
                .delete()
                .then()
                .log().all()
                .extract().response();
    }

}
