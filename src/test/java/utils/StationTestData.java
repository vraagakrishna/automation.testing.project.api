package utils;

import com.github.javafaker.Faker;

public class StationTestData {

    // <editor-fold desc="Class fields">
    private static final Faker faker = new Faker();

    private final double latitudeMin = -90.0;

    private final double latitudeMax = 90.0;

    private final double longitudeMin = -180.0;

    private final double longitudeMax = 180.0;

    private final double altitudeMin = -500; // below sea level

    private final double altitudeMax = 10000; // highest on Earth [in meters]

    public String stationName = faker.address().city() + " Station";

    public String externalId = Double.toString(Math.floor(Math.random() * 1000000));

    private String stationId;

    private Double stationLatitude;

    private Double stationLongitude;

    private Double stationAltitude;
    // </editor-fold>

    // <editor-fold desc="Getters and Setters">
    public double getLatitudeMin() {
        return latitudeMin;
    }

    public double getLatitudeMax() {
        return latitudeMax;
    }

    public double getLongitudeMin() {
        return longitudeMin;
    }

    public double getLongitudeMax() {
        return longitudeMax;
    }

    public double getAltitudeMin() {
        return altitudeMin;
    }

    public double getAltitudeMax() {
        return altitudeMax;
    }

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
    // </editor-fold>

}
