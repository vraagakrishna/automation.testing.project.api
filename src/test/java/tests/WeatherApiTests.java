package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import model.Failure;
import model.GetStation;
import model.GetStationList;
import model.PostStation;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.StationTestData;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static requestbuilder.WeatherApiRequestBuilder.*;

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

    @Description("Register with invalid altitude")
    @Test(dependsOnMethods = "getAllStationsInitially", priority = 8)
    public void registerStationWithoutLongitudeLatitudeAltitude() {
        PostStation<Object> postStation = new PostStation<>();
        postStation.setExternalId(" ");
        postStation.setName(" ");

        GetStationList station = registerStation(postStation)
                .then()
                .log().all()
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

    @Description("Register station")
    @Test(dependsOnMethods = "getAllStationsInitially", priority = 9)
    public void registerValidStation() {
        data.setStationLatitude(37.76);
        data.setStationLongitude(-122.43);
        data.setStationAltitude(150.0);

        PostStation<Object> postStation = new PostStation<>();
        postStation.setExternalId(data.externalId);
        postStation.setName(data.stationName);
        postStation.setLatitude(data.getStationLatitude());
        postStation.setLongitude(data.getStationLongitude());
        postStation.setAltitude(data.getStationAltitude());

        GetStationList station = registerStation(postStation)
                .then()
                .log().all()
                .assertThat().statusCode(HttpStatus.SC_CREATED)
                .extract().as(GetStationList.class);

        validateDataAfterRegister(postStation, station);

        data.setStationId(station.getId());
    }

    @Description("Get station with invalid name")
    @Test(dependsOnMethods = "registerValidStation", priority = 1)
    public void getStationInfoByInvalidId() {
        Failure failureResponse = getStationById(null)
                .then()
                .log().all()
                .assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract().as(Failure.class);

        validateInvalidStationResponse(failureResponse);
    }

    @Description("Get station info")
    @Test(dependsOnMethods = "registerValidStation", priority = 2)
    public void getStationInfo() {
        GetStation station = getStationById(data.getStationId())
                .then()
                .log().all()
                .assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(GetStation.class);

        validateStationData(station);
    }

    @Description("Getting all the stations after registration")
    @Test(dependsOnMethods = "getStationInfo")
    public void getAllStationsAfterRegistration() {
        Response response = getStations()
                .then()
                .log().all()
                .assertThat().statusCode(HttpStatus.SC_OK)
                .extract().response();

        validateAllStations(response, true);
    }

    @Description("Update station info with invalid external id")
    @Test(dependsOnMethods = "getAllStationsAfterRegistration")
    public void updateStationInfoInvalidExternalId() {
        PostStation<Object> updatedStation = new PostStation<>();

        Failure failureResponse = updateStationById(data.getStationId(), updatedStation)
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

    @Description("Update station info with invalid station name")
    @Test(dependsOnMethods = "updateStationInfoInvalidExternalId")
    public void updateStationInfoInvalidStationName() {
        PostStation<Object> updatedStation = new PostStation<>();
        updatedStation.setExternalId(data.externalId);

        Failure failureResponse = updateStationById(data.getStationId(), updatedStation)
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

}
