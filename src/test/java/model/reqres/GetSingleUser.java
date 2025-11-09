package model.reqres;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetSingleUser {

    // <editor-fold desc="Class Fields">
    private GetUserData data;
    // </editor-fold>

    // <editor-fold desc="Getters and Setters">
    public GetUserData getData() {
        return data;
    }

    public void setData(GetUserData data) {
        this.data = data;
    }
    // </editor-fold>

}
