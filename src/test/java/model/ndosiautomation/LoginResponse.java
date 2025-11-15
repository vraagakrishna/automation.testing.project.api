package model.ndosiautomation;

public class LoginResponse {

    // <editor-fold desc="Class Fields">
    private boolean success;

    private String message;

    private LoginResponseData data;
    // </editor-fold>

    // <editor-fold desc="Ctor">
    public LoginResponse() {
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

    public LoginResponseData getData() {
        return data;
    }

    public void setData(LoginResponseData data) {
        this.data = data;
    }
    // </editor-fold>

}
