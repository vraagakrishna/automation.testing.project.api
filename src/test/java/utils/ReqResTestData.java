package utils;

import com.github.javafaker.Faker;
import model.reqres.GetUserData;

import java.util.List;

public class ReqResTestData {

    // <editor-fold desc="Class fields">
    private static final Faker faker = new Faker();

    private final String firstName = faker.name().firstName();

    private final String lastName = faker.name().lastName();

    private String password = faker.internet().password(8, 16, true, true, true);

    private String username = firstName.toLowerCase() + "." + lastName.toLowerCase();

    private String email = firstName + "." + lastName + "@gmail.com";

    private List<GetUserData> users;

    private String token;

    private String id;
    // </editor-fold>

    // <editor-fold desc="Getters and Setters">
    public List<GetUserData> getUsers() {
        return users;
    }

    public void setUsers(List<GetUserData> users) {
        this.users = users;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    // </editor-fold>

}
