package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import model.Failure;
import model.PostStation;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.StationTestData;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static requestbuilder.WeatherApiRequestBuilder.getStations;
import static requestbuilder.WeatherApiRequestBuilder.registerStation;

@Test
@Feature("Open WeatherMap")
@Story("Weather Station")
public class WeatherApiTests {

    StationTestData data = new StationTestData();

    @Description("Getting all the stations")
    public void getAllStationsInitially() {
        Response response = getStations()
                .then()
                .log().all()
                .assertThat().statusCode(HttpStatus.SC_OK)
                .extract().response();

        validateAllStations(response, false);
    }

    @Description("Register blank station")
    @Test(dependsOnMethods = "getAllStationsInitially", priority = 1)
    public void registerBlankStation() {
        PostStation<Object> postStation = new PostStation<>();

        Failure failureResponse = registerStation(postStation)
                .then()
                .log().all()
                .assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract().as(Failure.class);

        validateFailedResponse(
                failureResponse,
                400001,
                "Bad external id"
        );
    }

    @Description("Register invalid external id")
    @Test(dependsOnMethods = "getAllStationsInitially", priority = 2)
    public void registerInvalidExternalIdStation() {
        PostStation<Object> postStation = new PostStation<>();
        postStation.setExternalId(0);

        Failure failureResponse = registerStation(postStation)
                .then()
                .log().all()
                .assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract().as(Failure.class);

        validateFailedResponseUsingContains(
                failureResponse,
                400001,
                "expected=string, got=number"
        );
    }

    @Description("Register with no station name")
    @Test(dependsOnMethods = "getAllStationsInitially", priority = 3)
    public void registerNoStationNameStation() {
        PostStation<Object> postStation = new PostStation<>();
        postStation.setExternalId(" ");

        Failure failureResponse = registerStation(postStation)
                .then()
                .log().all()
                .assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract().as(Failure.class);

        validateFailedResponse(
                failureResponse,
                400001,
                "Bad or zero length station name"
        );
    }

    @Description("Register with invalid station name")
    @Test(dependsOnMethods = "getAllStationsInitially", priority = 4)
    public void registerInvalidStationNameStation() {
        PostStation<Object> postStation = new PostStation<>();
        postStation.setExternalId(" ");
        postStation.setName(0);

        Failure failureResponse = registerStation(postStation)
                .then()
                .log().all()
                .assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract().as(Failure.class);

        validateFailedResponseUsingContains(
                failureResponse,
                400001,
                "expected=string, got=number"
        );
    }

    @Description("Register with invalid latitude")
    @Test(dependsOnMethods = "getAllStationsInitially", priority = 5)
    public void registerInvalidLatitudeStation() {
        PostStation<Object> postStation = new PostStation<>();
        postStation.setExternalId(" ");
        postStation.setName("");
        postStation.setLatitude("0");

        Failure failureResponse = registerStation(postStation)
                .then()
                .log().all()
                .assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract().as(Failure.class);

        validateFailedResponseUsingContains(
                failureResponse,
                400001,
                "expected=float64, got=string"
        );
    }

    @Description("Register with invalid longitude")
    @Test(dependsOnMethods = "getAllStationsInitially", priority = 6)
    public void registerInvalidLongitudeStation() {
        PostStation<Object> postStation = new PostStation<>();
        postStation.setExternalId(" ");
        postStation.setName("");
        postStation.setLongitude("0");

        Failure failureResponse = registerStation(postStation)
                .then()
                .log().all()
                .assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract().as(Failure.class);

        validateFailedResponseUsingContains(
                failureResponse,
                400001,
                "expected=float64, got=string"
        );
    }

    @Description("Register with invalid altitude")
    @Test(dependsOnMethods = "getAllStationsInitially", priority = 7)
    public void registerInvalidAltitudeStation() {
        PostStation<Object> postStation = new PostStation<>();
        postStation.setExternalId(" ");
        postStation.setName("");
        postStation.setAltitude("0");

        Failure failureResponse = registerStation(postStation)
                .then()
                .log().all()
                .assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract().as(Failure.class);

        validateFailedResponseUsingContains(
                failureResponse,
                400001,
                "expected=float64, got=string"
        );
    }

    private void validateAllStations(Response response, boolean shouldExist) {
        List<Map<String, Object>> stations = response
                .jsonPath().getList("");

        Assert.assertNotNull(stations, "Response is null");

        if (!stations.isEmpty()) {
            List<String> expectedKeys = Arrays.asList(
                    "id", "created_at", "updated_at", "external_id",
                    "name", "longitude", "latitude", "altitude", "rank"
            );

            for (int i = 0; i < stations.size(); i++) {
                Map<String, Object> item = stations.get(i);
                for (String key : expectedKeys) {
                    Assert.assertTrue(item.containsKey(key),
                            String.format("Missing key '%s' in item %d", key, i));
                }
            }
        } else {
            System.out.println("No data in list, skipping key validation");
        }

        if (!shouldExist) {
            boolean stationExists = stations.stream()
                    .anyMatch(s -> data.stationName.equals(s.get("name")));
            Assert.assertFalse(
                    stationExists,
                    String.format("Generated station name '%s' should not exist in response", data.stationName)
            );

            boolean externalIdExists = stations.stream()
                    .anyMatch(s -> data.externalId.equals(s.get("external_id")));
            Assert.assertFalse(
                    externalIdExists,
                    String.format("Generated external id '%s' should not exist in response", data.externalId)
            );
        } else {
            boolean stationExists = stations.stream()
                    .anyMatch(s -> data.stationName.equals(s.get("name")));
            Assert.assertTrue(
                    stationExists,
                    String.format("Generated station name '%s' does not exist in response", data.stationName)
            );

            boolean externalIdExists = stations.stream()
                    .anyMatch(s -> data.externalId.equals(s.get("external_id")));
            Assert.assertTrue(
                    externalIdExists,
                    String.format("Generated external id '%s' does not exist in response", data.externalId)
            );
        }
    }

    private void validateFailedResponse(Failure failureResponse, int code, String message) {
        Assert.assertEquals(
                failureResponse.getCode(),
                code,
                "Response code is incorrect"
        );

        Assert.assertEquals(
                failureResponse.getMessage(),
                message,
                "Response message is incorrect"
        );
    }

    private void validateFailedResponseUsingContains(Failure failureResponse, int code, String message) {
        Assert.assertEquals(
                failureResponse.getCode(),
                code,
                "Response code is incorrect"
        );

        Assert.assertTrue(
                failureResponse.getMessage().contains(message),
                "Response message is incorrect"
        );
    }

}
