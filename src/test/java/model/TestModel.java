package model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TestModel {

    // <editor-fold desc="Class Fields">
    @JsonProperty("param_1")
    private String param1;

    private String param2;

    private int param3;
    // </editor-fold>

    // <editor-fold desc="Ctor">
    public TestModel(String param1, String param2, int param3) {
        this.param1 = param1;
        this.param2 = param2;
        this.param3 = param3;
    }
    // </editor-fold>

    // <editor-fold desc="Getters and Setters">
    public String getParam1() {
        return param1;
    }

    public void setParam1(String param1) {
        this.param1 = param1;
    }

    public String getParam2() {
        return param2;
    }

    public void setParam2(String param2) {
        this.param2 = param2;
    }

    public int getParam3() {
        return param3;
    }

    public void setParam3(int param3) {
        this.param3 = param3;
    }
    // </editor-fold>

}
