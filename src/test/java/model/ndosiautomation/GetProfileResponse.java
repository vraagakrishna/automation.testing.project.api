package model.ndosiautomation;

public class GetProfileResponse {

    // <editor-fold desc="Class Fields">
    private boolean success;

    private String message;

    private GetProfileResponseData data;
    // </editor-fold>

    // <editor-fold desc="Ctor">
    public GetProfileResponse() {
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

    public GetProfileResponseData getData() {
        return data;
    }

    public void setData(GetProfileResponseData data) {
        this.data = data;
    }
    // </editor-fold>

}
