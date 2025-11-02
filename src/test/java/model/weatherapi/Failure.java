package model.weatherapi;

import com.fasterxml.jackson.annotation.JsonAlias;

public class Failure {

    // <editor-fold desc="Class Fields">
    private String message;

    @JsonAlias({"code", "cod"})
    private int code;
    // </editor-fold>

    // <editor-fold desc="Ctor">
    public Failure() {
    }
    // </editor-fold>

    // <editor-fold desc="Getters and Setters">
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
    // </editor-fold>
}
