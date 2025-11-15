package model.ndosiautomation;

public class RegisterResponse {

    // <editor-fold desc="Class Fields">
    private boolean success;

    private String message;

    private RegisterResponseData data;
    // </editor-fold>

    // <editor-fold desc="Ctor">
    public RegisterResponse() {
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

    public RegisterResponseData getData() {
        return data;
    }

    public void setData(RegisterResponseData data) {
        this.data = data;
    }
    // </editor-fold>

}
