package model.weatherapi;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostStation<T> {

    // <editor-fold desc="Class Fields">
    @JsonProperty("external_id")
    private T externalId;

    private T name;

    private T latitude;

    private T longitude;

    private T altitude;
    // </editor-fold>

    // <editor-fold desc="Ctor">
    public PostStation() {
    }
    // </editor-fold>

    // <editor-fold desc="Getters and Setters">
    public T getExternalId() {
        return externalId;
    }

    public void setExternalId(T externalId) {
        this.externalId = externalId;
    }

    public T getName() {
        return name;
    }

    public void setName(T name) {
        this.name = name;
    }

    public T getLatitude() {
        return latitude;
    }

    public void setLatitude(T latitude) {
        this.latitude = latitude;
    }

    public T getLongitude() {
        return longitude;
    }

    public void setLongitude(T longitude) {
        this.longitude = longitude;
    }

    public T getAltitude() {
        return altitude;
    }

    public void setAltitude(T altitude) {
        this.altitude = altitude;
    }
    // </editor-fold>

    // <editor-fold desc="Overrides">
    @Override
    public String toString() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(this);
        } catch (Exception e) {
            return super.toString();
        }
    }
    // </editor-fold>

}
