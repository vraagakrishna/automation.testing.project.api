package model.ndosiautomation;

public class LoginResponseData {

    // <editor-fold desc="Class Fields">
    private LoginResponseUserData user;

    private String token;
    // </editor-fold>

    // <editor-fold desc="Ctor">
    public LoginResponseData() {
    }
    // </editor-fold>

    // <editor-fold desc="Getters and Setters">
    public LoginResponseUserData getUser() {
        return user;
    }

    public void setUser(LoginResponseUserData user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    // </editor-fold>

}
