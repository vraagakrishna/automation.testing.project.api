package model.ndosiautomation;

public class LoginResponseUserData {

    // <editor-fold desc="Class Fields">
    private String id;

    private String firstName;

    private String lastName;

    private String email;

    private String createdAt;

    private String updatedAt;
    // </editor-fold>

    // <editor-fold desc="Ctor">
    public LoginResponseUserData() {
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

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
    // </editor-fold>

}
