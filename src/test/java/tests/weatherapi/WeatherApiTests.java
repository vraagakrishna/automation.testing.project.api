package tests.weatherapi;

import io.qameta.allure.*;
import io.restassured.response.Response;
import model.weatherapi.Failure;
import model.weatherapi.GetStation;
import model.weatherapi.GetStationList;
import model.weatherapi.PostStation;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.RandomNumberGenerator;
import utils.StationTestData;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static requestbuilder.weatherapi.WeatherApiRequestBuilder.*;

@Epic("Weather API")
@Feature("Stations Endpoints")
public class WeatherApiTests {

    StationTestData data = new StationTestData();

    RandomNumberGenerator randomNumberGenerator = new RandomNumberGenerator();

    @Story("Get All Stations")
    @Description("Verify that requesting all stations without an API key returns an authentication error")
    @Test(priority = 1)
    @Severity(SeverityLevel.CRITICAL)
    public void getAllStationsWithoutApiKey() {
        Failure failureResponse = getStations(false)
                .then()
                .assertThat().statusCode(HttpStatus.SC_UNAUTHORIZED)
                .extract().as(Failure.class);

        validateFailedResponseUsingContains(
                failureResponse,
                401,
                "Invalid API key"
        );
    }

    @Story("Get All Stations")
    @Description("Verify that the initial request to retrieve all stations is successful")
    @Test(priority = 2)
    @Severity(SeverityLevel.BLOCKER)
    public void getAllStationsInitially() {
        Response response = getStations(true)
                .then()
                .assertThat().statusCode(HttpStatus.SC_OK)
                .extract().response();

        validateAllStations(response, false);
    }

    @Story("Register Station")
    @Description("Verify that registering a station without an API key returns 401 Unauthorized")
    @Test(dependsOnMethods = "getAllStationsInitially")
    @Severity(SeverityLevel.CRITICAL)
    public void registerStationWithoutApiKey() {
        PostStation<Object> postStation = new PostStation<>();

        Failure failureResponse = registerStation(false, postStation)
                .then()
                .assertThat().statusCode(HttpStatus.SC_UNAUTHORIZED)
                .extract().as(Failure.class);

        validateFailedResponseUsingContains(
                failureResponse,
                401,
                "Invalid API key"
        );
    }

    @Story("Register Station")
    @Description("Verify that registering a station with blank input fields returns validation errors")
    @Test(dependsOnMethods = "getAllStationsInitially", priority = 1)
    @Severity(SeverityLevel.CRITICAL)
    public void registerBlankStation() {
        PostStation<Object> postStation = new PostStation<>();

        Failure failureResponse = registerStation(true, postStation)
                .then()
                .assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract().as(Failure.class);

        validateFailedResponse(
                failureResponse,
                400001,
                "Bad external id"
        );
    }

    @Story("Register Station")
    @Description("Verify that registering a station with an invalid external ID fails validation")
    @Test(dependsOnMethods = "getAllStationsInitially", priority = 2)
    @Severity(SeverityLevel.NORMAL)
    public void registerStationWithInvalidExternalId() {
        PostStation<Object> postStation = new PostStation<>();
        postStation.setExternalId(0);

        Failure failureResponse = registerStation(true, postStation)
                .then()
                .assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract().as(Failure.class);

        validateFailedResponseUsingContains(
                failureResponse,
                400001,
                "expected=string, got=number"
        );
    }

    @Story("Register Station")
    @Description("Verify that registering a station with no name returns a bad request")
    @Test(dependsOnMethods = "getAllStationsInitially", priority = 3)
    @Severity(SeverityLevel.NORMAL)
    public void registerStationWithNoStationName() {
        PostStation<Object> postStation = new PostStation<>();
        postStation.setExternalId(" ");

        Failure failureResponse = registerStation(true, postStation)
                .then()
                .assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract().as(Failure.class);

        validateFailedResponse(
                failureResponse,
                400001,
                "Bad or zero length station name"
        );
    }

    @Story("Register Station")
    @Description("Verify that registering a station with an invalid name returns an error")
    @Test(dependsOnMethods = "getAllStationsInitially", priority = 4)
    @Severity(SeverityLevel.NORMAL)
    public void registerStationWithInvalidStationName() {
        PostStation<Object> postStation = new PostStation<>();
        postStation.setExternalId(" ");
        postStation.setName(0);

        Failure failureResponse = registerStation(true, postStation)
                .then()
                .assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract().as(Failure.class);

        validateFailedResponseUsingContains(
                failureResponse,
                400001,
                "expected=string, got=number"
        );
    }

    @Story("Register Station")
    @Description("Verify that invalid latitude values are validated correctly")
    @Test(dependsOnMethods = "getAllStationsInitially", priority = 5)
    @Severity(SeverityLevel.NORMAL)
    public void registerStationWithInvalidLatitude() {
        PostStation<Object> postStation = new PostStation<>();
        postStation.setExternalId(" ");
        postStation.setName("");
        postStation.setLatitude("0");

        Failure failureResponse = registerStation(true, postStation)
                .then()
                .assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract().as(Failure.class);

        validateFailedResponseUsingContains(
                failureResponse,
                400001,
                "expected=float64, got=string"
        );
    }

    @Story("Register Station")
    @Description("Verify that too small latitude values are validated correctly")
    @Test(dependsOnMethods = "getAllStationsInitially", priority = 6)
    @Severity(SeverityLevel.MINOR)
    public void registerStationWithSmallLatitude() {
        PostStation<Object> postStation = new PostStation<>();
        postStation.setExternalId(" ");
        postStation.setName(" ");
        postStation.setLatitude(randomNumberGenerator.generateRandomNumber(data.getLatitudeMin() - 180.0, data.getLatitudeMin() - 0.1));

        Failure failureResponse = registerStation(true, postStation)
                .then()
                .assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract().as(Failure.class);

        validateFailedResponseUsingContains(
                failureResponse,
                400001,
                "latitude"
        );
    }

    @Story("Register Station")
    @Description("Verify that too large latitude values are validated correctly")
    @Test(dependsOnMethods = "getAllStationsInitially", priority = 7)
    @Severity(SeverityLevel.MINOR)
    public void registerStationWithLargeLatitude() {
        PostStation<Object> postStation = new PostStation<>();
        postStation.setExternalId(" ");
        postStation.setName(" ");
        postStation.setLatitude(randomNumberGenerator.generateRandomNumber(data.getLatitudeMax() + 0.1, data.getLatitudeMax() + 180.0));

        Failure failureResponse = registerStation(true, postStation)
                .then()
                .assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract().as(Failure.class);

        validateFailedResponseUsingContains(
                failureResponse,
                400001,
                "latitude"
        );
    }

    @Story("Register Station")
    @Description("Verify that invalid longitude values are validated correctly")
    @Test(dependsOnMethods = "getAllStationsInitially", priority = 8)
    @Severity(SeverityLevel.NORMAL)
    public void registerStationWithInvalidLongitude() {
        PostStation<Object> postStation = new PostStation<>();
        postStation.setExternalId(" ");
        postStation.setName("");
        postStation.setLongitude("0");

        Failure failureResponse = registerStation(true, postStation)
                .then()
                .assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract().as(Failure.class);

        validateFailedResponseUsingContains(
                failureResponse,
                400001,
                "expected=float64, got=string"
        );
    }

    @Story("Register Station")
    @Description("Verify that too small longitude values are validated correctly")
    @Test(dependsOnMethods = "getAllStationsInitially", priority = 9)
    @Severity(SeverityLevel.MINOR)
    public void registerStationWithSmallLongitude() {
        PostStation<Object> postStation = new PostStation<>();
        postStation.setExternalId(" ");
        postStation.setName(" ");
        postStation.setLongitude(randomNumberGenerator.generateRandomNumber(data.getLongitudeMin() - 180.0, data.getLongitudeMin() - 0.1));

        Failure failureResponse = registerStation(true, postStation)
                .then()
                .assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract().as(Failure.class);

        validateFailedResponseUsingContains(
                failureResponse,
                400001,
                "longitude"
        );
    }

    @Story("Register Station")
    @Description("Verify that too large longitude values are validated correctly")
    @Test(dependsOnMethods = "getAllStationsInitially", priority = 10)
    @Severity(SeverityLevel.MINOR)
    public void registerStationWithLargeLongitude() {
        PostStation<Object> postStation = new PostStation<>();
        postStation.setExternalId(" ");
        postStation.setName(" ");
        postStation.setLongitude(randomNumberGenerator.generateRandomNumber(data.getLongitudeMax() + 0.1, data.getLongitudeMax() + 180.0));

        Failure failureResponse = registerStation(true, postStation)
                .then()
                .assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract().as(Failure.class);

        validateFailedResponseUsingContains(
                failureResponse,
                400001,
                "longitude"
        );
    }

    @Story("Register Station")
    @Description("Verify that invalid altitude values are validated correctly")
    @Test(dependsOnMethods = "getAllStationsInitially", priority = 11)
    @Severity(SeverityLevel.NORMAL)
    public void registerStationWithInvalidAltitude() {
        PostStation<Object> postStation = new PostStation<>();
        postStation.setExternalId(" ");
        postStation.setName("");
        postStation.setAltitude("0");

        Failure failureResponse = registerStation(true, postStation)
                .then()
                .assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract().as(Failure.class);

        validateFailedResponseUsingContains(
                failureResponse,
                400001,
                "expected=float64, got=string"
        );
    }

    @Story("Register Station")
    @Description("Verify that too small altitude values are validated correctly")
    @Test(dependsOnMethods = "getAllStationsInitially", priority = 12)
    @Severity(SeverityLevel.MINOR)
    public void registerStationWithSmallAltitude() {
        PostStation<Object> postStation = new PostStation<>();
        postStation.setExternalId(" ");
        postStation.setName(" ");
        postStation.setAltitude(randomNumberGenerator.generateRandomNumber(data.getAltitudeMin() - 180.0, data.getAltitudeMin() - 0.1));

        Failure failureResponse = registerStation(true, postStation)
                .then()
                .assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract().as(Failure.class);

        validateFailedResponseUsingContains(
                failureResponse,
                400001,
                "altitude"
        );
    }

    @Story("Register Station")
    @Description("Verify that too large altitude values are validated correctly")
    @Test(dependsOnMethods = "getAllStationsInitially", priority = 13)
    @Severity(SeverityLevel.MINOR)
    public void registerStationWithLargeAltitude() {
        PostStation<Object> postStation = new PostStation<>();
        postStation.setExternalId(" ");
        postStation.setName(" ");
        postStation.setAltitude(randomNumberGenerator.generateRandomNumber(data.getAltitudeMax() + 0.1, data.getAltitudeMax() + 180.0));

        Failure failureResponse = registerStation(true, postStation)
                .then()
                .assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract().as(Failure.class);

        validateFailedResponseUsingContains(
                failureResponse,
                400001,
                "altitude"
        );
    }

    @Story("Register Station")
    @Description("Verify that a valid station (without latitude, longitude and altitude) registration succeeds and returns 201 Created")
    @Test(dependsOnMethods = "getAllStationsInitially", priority = 14)
    @Severity(SeverityLevel.NORMAL)
    public void registerStationWithoutLongitudeLatitudeAltitude() {
        PostStation<Object> postStation = new PostStation<>();
        postStation.setExternalId(" ");
        postStation.setName(" ");

        GetStationList station = registerStation(true, postStation)
                .then()
                .assertThat().statusCode(HttpStatus.SC_CREATED)
                .extract().as(GetStationList.class);

        validateDataAfterRegister(postStation, station);

        validateNotNullAndEqualsZero(
                station.getLatitude(),
                "Latitude"
        );

        validateNotNullAndEqualsZero(
                station.getLongitude(),
                "Longitude"
        );
    }

    @Story("Register Station")
    @Description("Verify that a valid station registration succeeds and returns 201 Created")
    @Test(dependsOnMethods = "getAllStationsInitially", priority = 15)
    @Severity(SeverityLevel.CRITICAL)
    public void registerValidStation() {
        data.setStationLatitude(randomNumberGenerator.generateRandomNumber(data.getLatitudeMin(), data.getLatitudeMax()));
        data.setStationLongitude(randomNumberGenerator.generateRandomNumber(data.getLongitudeMin(), data.getLongitudeMax()));
        data.setStationAltitude(randomNumberGenerator.generateRandomNumber(data.getAltitudeMin(), data.getAltitudeMax()));

        PostStation<Object> postStation = new PostStation<>();
        postStation.setExternalId(data.externalId);
        postStation.setName(data.stationName);
        postStation.setLatitude(data.getStationLatitude());
        postStation.setLongitude(data.getStationLongitude());
        postStation.setAltitude(data.getStationAltitude());

        GetStationList station = registerStation(true, postStation)
                .then()
                .assertThat().statusCode(HttpStatus.SC_CREATED)
                .extract().as(GetStationList.class);

        validateDataAfterRegister(postStation, station);

        data.setStationId(station.getId());
    }

    @Story("Get Station by ID")
    @Description("Verify that getting station info without an API key returns 401 Unauthorized")
    @Test(dependsOnMethods = "registerValidStation")
    @Severity(SeverityLevel.CRITICAL)
    public void getStationInfoWithoutApiKey() {
        Failure failureResponse = getStationById(false, null)
                .then()
                .assertThat().statusCode(HttpStatus.SC_UNAUTHORIZED)
                .extract().as(Failure.class);

        validateFailedResponseUsingContains(
                failureResponse,
                401,
                "Invalid API key"
        );
    }

    @Story("Get Station by ID")
    @Description("Verify that getting station info with an invalid ID returns 404 Not Found")
    @Test(dependsOnMethods = "registerValidStation", priority = 1)
    @Severity(SeverityLevel.NORMAL)
    public void getStationInfoByInvalidId() {
        Failure failureResponse = getStationById(true, null)
                .then()
                .assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract().as(Failure.class);

        validateInvalidStationResponse(failureResponse);
    }

    @Story("Get Station by ID")
    @Description("Verify that getting station info by valid ID returns correct data and 200 OK")
    @Test(dependsOnMethods = "registerValidStation", priority = 2)
    @Severity(SeverityLevel.CRITICAL)
    public void getStationInfo() {
        GetStation station = getStationById(true, data.getStationId())
                .then()
                .assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(GetStation.class);

        validateStationData(station);
    }

    @Story("Get All Stations")
    @Description("Verify that the request to retrieve all stations returns the registered station")
    @Test(dependsOnMethods = "getStationInfo")
    @Severity(SeverityLevel.CRITICAL)
    public void getAllStationsAfterRegistration() {
        Response response = getStations(true)
                .then()
                .assertThat().statusCode(HttpStatus.SC_OK)
                .extract().response();

        validateAllStations(response, true);
    }

    @Story("Update Station Info")
    @Description("Verify that updating station info without an API key returns 401 Unauthorized")
    @Test(dependsOnMethods = "getAllStationsAfterRegistration")
    @Severity(SeverityLevel.CRITICAL)
    public void updateStationInfoWithoutApiKey() {
        PostStation<Object> updatedStation = new PostStation<>();

        Failure failureResponse = updateStationById(false, data.getStationId(), updatedStation)
                .then()
                .assertThat().statusCode(HttpStatus.SC_UNAUTHORIZED)
                .extract().as(Failure.class);

        validateFailedResponseUsingContains(
                failureResponse,
                401,
                "Invalid API key"
        );
    }

    @Story("Update Station Info")
    @Description("Verify that updating a non-existent station ID returns 404 Not Found")
    @Test(dependsOnMethods = "getAllStationsAfterRegistration", priority = 1)
    @Severity(SeverityLevel.NORMAL)
    public void updateStationInfoByInvalidId() {
        PostStation<Object> updatedStation = new PostStation<>();

        Failure failureResponse = updateStationById(true, null, updatedStation)
                .then()
                .assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract().as(Failure.class);

        validateInvalidStationResponse(failureResponse);
    }

    @Story("Update Station Info")
    @Description("Verify that updating station info with invalid external ID returns validation errors")
    @Test(dependsOnMethods = "getAllStationsAfterRegistration", priority = 2)
    @Severity(SeverityLevel.NORMAL)
    public void updateStationInfoInvalidExternalId() {
        PostStation<Object> updatedStation = new PostStation<>();

        Failure failureResponse = updateStationById(true, data.getStationId(), updatedStation)
                .then()
                .assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract().as(Failure.class);

        validateFailedResponse(
                failureResponse,
                400001,
                "Bad external id"
        );
    }

    @Story("Update Station Info")
    @Description("Verify that updating station info with invalid name returns validation errors")
    @Test(dependsOnMethods = "getAllStationsAfterRegistration", priority = 3)
    @Severity(SeverityLevel.NORMAL)
    public void updateStationInfoInvalidStationName() {
        PostStation<Object> updatedStation = new PostStation<>();
        updatedStation.setExternalId(data.externalId);

        Failure failureResponse = updateStationById(true, data.getStationId(), updatedStation)
                .then()
                .assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract().as(Failure.class);

        validateFailedResponse(
                failureResponse,
                400001,
                "Bad or zero length station name"
        );
    }

    @Story("Update Station Info")
    @Description("Verify that updating station info (without longitude, latitude, and altitude) returns 200 OK")
    @Test(dependsOnMethods = "getAllStationsAfterRegistration", priority = 4)
    @Severity(SeverityLevel.CRITICAL)
    public void updateStationInfoWithoutLongitudeLatitudeAltitude() {
        data.setStationLatitude(0.0);
        data.setStationLongitude(0.0);
        data.setStationAltitude(null);

        PostStation<Object> updatedStation = new PostStation<>();
        updatedStation.setExternalId(data.externalId);
        updatedStation.setName(data.stationName);

        GetStation station = updateStationById(true, data.getStationId(), updatedStation)
                .then()
                .assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(GetStation.class);

        validateStationData(station);

        validateNotNullAndEqualsZero(
                station.getLatitude(),
                "Latitude"
        );

        validateNotNullAndEqualsZero(
                station.getLongitude(),
                "Longitude"
        );
    }

    @Story("Get Station by ID")
    @Description("Verify that station info returns the updated station info")
    @Test(dependsOnMethods = "updateStationInfoWithoutLongitudeLatitudeAltitude")
    @Severity(SeverityLevel.CRITICAL)
    public void getStationInfoAfterUpdating() {
        GetStation station = getStationById(true, data.getStationId())
                .then()
                .assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(GetStation.class);

        validateStationData(station);
    }

    @Story("Update Station Info")
    @Description("Verify that updating station info with valid data returns 200 OK")
    @Test(dependsOnMethods = "getStationInfoAfterUpdating")
    @Severity(SeverityLevel.CRITICAL)
    public void updateStationWithCorrectValues() {
        data.setStationLatitude(randomNumberGenerator.generateRandomNumber(data.getLatitudeMin(), data.getLatitudeMax()));
        data.setStationLongitude(randomNumberGenerator.generateRandomNumber(data.getLongitudeMin(), data.getLongitudeMax()));
        data.setStationAltitude(randomNumberGenerator.generateRandomNumber(data.getAltitudeMin(), data.getAltitudeMax()));

        PostStation<Object> updatedStation = new PostStation<>();
        updatedStation.setExternalId(data.externalId);
        updatedStation.setName(data.stationName);
        updatedStation.setLongitude(data.getStationLongitude());
        updatedStation.setLatitude(data.getStationLatitude());
        updatedStation.setAltitude(data.getStationAltitude());

        GetStation station = updateStationById(true, data.getStationId(), updatedStation)
                .then()
                .assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(GetStation.class);

        validateStationData(station);
    }

    @Story("Get Station by ID")
    @Description("Verify that station info returns the updated station info")
    @Test(dependsOnMethods = "updateStationWithCorrectValues")
    @Severity(SeverityLevel.CRITICAL)
    public void getStationInfoAfterUpdatingAgain() {
        GetStation station = getStationById(true, data.getStationId())
                .then()
                .assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(GetStation.class);

        validateStationData(station);
    }

    @Story("Delete Station Info")
    @Description("Verify that deleting a station without an API key returns 401 Unauthorized")
    @Test(dependsOnMethods = "getStationInfoAfterUpdatingAgain")
    @Severity(SeverityLevel.CRITICAL)
    public void deleteStationInfoWithoutApiKey() {
        Failure failureResponse = deleteStationById(false, null)
                .then()
                .assertThat().statusCode(HttpStatus.SC_UNAUTHORIZED)
                .extract().as(Failure.class);

        validateFailedResponseUsingContains(
                failureResponse,
                401,
                "Invalid API key"
        );
    }

    @Story("Delete Station Info")
    @Description("Verify that deleting a station by invalid ID returns 404 Not Found")
    @Test(dependsOnMethods = "getStationInfoAfterUpdatingAgain", priority = 1)
    @Severity(SeverityLevel.NORMAL)
    public void deleteStationInfoByInvalidId() {
        Failure failureResponse = deleteStationById(true, null)
                .then()
                .assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract().as(Failure.class);

        validateInvalidStationResponse(failureResponse);
    }

    @Story("Delete Station Info")
    @Description("Verify that deleting a valid station succeeds with 204 No Content")
    @Test(dependsOnMethods = "getStationInfoAfterUpdatingAgain", priority = 1)
    @Severity(SeverityLevel.CRITICAL)
    public void deleteStationInfoByValidId() {
        deleteStationById(true, data.getStationId())
                .then()
                .assertThat().statusCode(HttpStatus.SC_NO_CONTENT);
    }

    @Story("Get All Stations")
    @Description("Verify that the request to retrieve all stations does not return the deleted station")
    @Test(dependsOnMethods = "deleteStationInfoByValidId")
    @Severity(SeverityLevel.CRITICAL)
    public void getAllStationsAfterDeletion() {
        Response response = getStations(true)
                .then()
                .assertThat().statusCode(HttpStatus.SC_OK)
                .extract().response();

        validateAllStations(response, false);
    }

    @Story("Get Station by ID")
    @Description("Verify that getting station info by valid ID after deletion returns 404 Not Found")
    @Test(dependsOnMethods = "deleteStationInfoByValidId", priority = 2)
    @Severity(SeverityLevel.CRITICAL)
    public void getStationByIdAfterDeletion() {
        Failure failureResponse = getStationById(true, data.getStationId())
                .then()
                .assertThat().statusCode(HttpStatus.SC_NOT_FOUND)
                .extract().as(Failure.class);

        validateFailedResponse(
                failureResponse,
                404001,
                "Station not found"
        );
    }

    @Story("Delete Station Info")
    @Description("Verify that trying to delete the same station again returns 404 Not Found")
    @Test(dependsOnMethods = {"getAllStationsAfterDeletion", "getStationByIdAfterDeletion"})
    @Severity(SeverityLevel.CRITICAL)
    public void deleteStationInfoAfterDeletion() {
        Failure failureResponse = deleteStationById(true, data.getStationId())
                .then()
                .assertThat().statusCode(HttpStatus.SC_NOT_FOUND)
                .extract().as(Failure.class);

        validateFailedResponse(
                failureResponse,
                404001,
                "Station not found"
        );
    }

    // <editor-fold desc="Private Methods">
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
                failureResponse.getMessage().toLowerCase().contains(message.toLowerCase()),
                "Response message is incorrect"
        );
    }

    private void validateDataAfterRegister(PostStation<Object> postStation, GetStationList station) {
        for (Field field : PostStation.class.getDeclaredFields()) {
            field.setAccessible(true);

            try {
                Object requestValue = field.get(postStation);

                Field responseField = GetStationList.class.getDeclaredField(field.getName());
                responseField.setAccessible(true);

                Object responseValue = responseField.get(station);

                if (requestValue != null) {
                    Assert.assertEquals(
                            responseValue,
                            requestValue,
                            String.format("Field mismatch for '%s'", field.getName())
                    );
                }
            } catch (NoSuchFieldException e) {
                System.out.println("Response does not contain field: " + field.getName());
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void validateNotNullAndEqualsZero(Double value, String valueName) {
        Assert.assertNotNull(
                value,
                valueName + " is incorrect"
        );

        Assert.assertEquals(
                value,
                0.0,
                valueName + " is incorrect"
        );
    }

    private void validateStationData(GetStation station) {
        Assert.assertEquals(
                station.getId(),
                data.getStationId(),
                "Station id is incorrect"
        );

        Assert.assertEquals(
                station.getName(),
                data.stationName,
                "Station name is incorrect"
        );

        Assert.assertEquals(
                station.getExternalId(),
                data.externalId,
                "Station external id is incorrect"
        );

        Assert.assertEquals(
                station.getLongitude(),
                data.getStationLongitude(),
                "Station longitude is incorrect"
        );

        Assert.assertEquals(
                station.getLatitude(),
                data.getStationLatitude(),
                "Station latitude is incorrect"
        );

        Assert.assertEquals(
                station.getAltitude(),
                data.getStationAltitude(),
                "Station altitude is incorrect"
        );
    }

    private void validateInvalidStationResponse(Failure failureResponse) {
        Assert.assertEquals(
                failureResponse.getCode(),
                400002,
                "Response code is incorrect"
        );

        Assert.assertEquals(
                failureResponse.getMessage(),
                "Station id not valid",
                "Response message is incorrect"
        );
    }
    // </editor-fold>

}
