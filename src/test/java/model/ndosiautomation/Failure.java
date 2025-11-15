package model.ndosiautomation;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Failure {

    // <editor-fold desc="Class Fields">
    private boolean success;

    private String message;

    @JsonProperty("error_code")
    private String errorCode;
    // </editor-fold>

    // <editor-fold desc="Ctor">
    public Failure() {
    }
    // </editor-fold>

    // <editor-fold desc="Getters and Setters">
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
    // </editor-fold>

}
