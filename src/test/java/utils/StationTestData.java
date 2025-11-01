package utils;

import com.github.javafaker.Faker;

public class StationTestData {

    private static final Faker faker = new Faker();

    public String stationName = faker.address().city() + " Station";

    public String externalId = Double.toString(Math.floor(Math.random() * 1000000));

    private String stationId;

    private Double stationLatitude;

    private Double stationLongitude;

    private Double stationAltitude;

    public Double getStationLatitude() {
        return stationLatitude;
    }

    public void setStationLatitude(Double stationLatitude) {
        this.stationLatitude = stationLatitude;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public Double getStationLongitude() {
        return stationLongitude;
    }

    public void setStationLongitude(Double stationLongitude) {
        this.stationLongitude = stationLongitude;
    }

    public Double getStationAltitude() {
        return stationAltitude;
    }

    public void setStationAltitude(Double stationAltitude) {
        this.stationAltitude = stationAltitude;
    }
}
