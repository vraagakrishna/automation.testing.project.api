package model.ndosiautomation;

public class RegisterResponseData {

    // <editor-fold desc="Class Fields">
    private String id;

    private String firstName;

    private String lastName;

    private String email;

    private String createdAt;
    // </editor-fold>

    // <editor-fold desc="Ctor">
    public RegisterResponseData() {
    }
    // </editor-fold>

    // <editor-fold desc="Getters and Setters">
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
    // </editor-fold>

}
