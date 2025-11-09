package model.reqres;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetSingleResource {

    // <editor-fold desc="Class Fields">
    private GetResourceData data;
    // </editor-fold>

    // <editor-fold desc="Getters and Setters">
    public GetResourceData getData() {
        return data;
    }

    public void setData(GetResourceData data) {
        this.data = data;
    }
    // </editor-fold>

}
