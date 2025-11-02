package requestbuilder;

import io.restassured.response.Response;
import model.weatherapi.PostStation;

import static io.restassured.RestAssured.given;

public class WeatherApiRequestBuilder extends BaseWeatherApiRequestBuilder {

    private static final String appId = System.getProperty("WEATHER_API_KEY", System.getenv("WEATHER_API_KEY"));

    public static Response getStations() {
        return given()
                .spec(baseSpec)
                .basePath("/stations")
                .queryParam("appid", appId)
                .log().all()
                .get()
                .then()
                .log().all()
                .extract().response();
    }

    public static Response registerStation(PostStation<Object> station) {
        return given()
                .spec(baseSpec)
                .basePath("/stations")
                .queryParam("appid", appId)
                .body(station)
                .log().all()
                .post()
                .then()
                .log().all()
                .extract().response();
    }

    public static Response getStationById(String stationId) {
        return given()
                .spec(baseSpec)
                .basePath("/stations/" + stationId)
                .queryParam("appid", appId)
                .log().all()
                .get()
                .then()
                .log().all()
                .extract().response();
    }

    public static Response updateStationById(String stationId, PostStation<Object> station) {
        return given()
                .spec(baseSpec)
                .basePath("/stations/" + stationId)
                .queryParam("appid", appId)
                .body(station)
                .log().all()
                .put()
                .then()
                .log().all()
                .extract().response();
    }

    public static Response deleteStationById(String stationId) {
        return given()
                .spec(baseSpec)
                .basePath("/stations/" + stationId)
                .queryParam("appid", appId)
                .log().all()
                .delete()
                .then()
                .log().all()
                .extract().response();
    }

}
