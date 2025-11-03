package model.reqres;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomEndpoint<T> {

    // <editor-fold desc="Class Fields">
    private T name;

    private T path;

    private T method;

    @JsonProperty("response_data")
    private T responseData;

    @JsonProperty("status_code")
    private T statusCode;

    private T headers;

    @JsonProperty("is_active")
    private T isActive;
    // </editor-fold>

    // <editor-fold desc="Ctor">
    public CustomEndpoint() {
    }
    // </editor-fold>

    // <editor-fold desc="Getters and Setters">
    public T getName() {
        return name;
    }

    public void setName(T name) {
        this.name = name;
    }

    public T getPath() {
        return path;
    }

    public void setPath(T path) {
        this.path = path;
    }

    public T getMethod() {
        return method;
    }

    public void setMethod(T method) {
        this.method = method;
    }

    public T getResponseData() {
        return responseData;
    }

    public void setResponseData(T responseData) {
        this.responseData = responseData;
    }

    public T getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(T statusCode) {
        this.statusCode = statusCode;
    }

    public T getHeaders() {
        return headers;
    }

    public void setHeaders(T headers) {
        this.headers = headers;
    }

    public T getIsActive() {
        return isActive;
    }

    public void setIsActive(T isActive) {
        this.isActive = isActive;
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
