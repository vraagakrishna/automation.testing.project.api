package utils;

import com.github.javafaker.Faker;
import model.reqres.GetResourceData;
import model.reqres.GetUserData;

import java.util.List;

public class ReqResTestData {

    // <editor-fold desc="Class fields">
    private static final Faker faker = new Faker();

    private final String firstName = faker.name().firstName();

    private final String lastName = faker.name().lastName();

    private String password = faker.internet().password(8, 16, true, true, true);

    private String email = firstName + "." + lastName + "@gmail.com";

    private List<GetUserData> users;

    private List<GetResourceData> resources;

    private String token;

    private String id;

    private String resourceName;
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

    public List<GetResourceData> getResources() {
        return resources;
    }

    public void setResources(List<GetResourceData> resources) {
        this.resources = resources;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }
    // </editor-fold>

}
