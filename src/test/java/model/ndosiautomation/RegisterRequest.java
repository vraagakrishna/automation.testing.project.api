package model.ndosiautomation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegisterRequest<T> {

    // <editor-fold desc="Class Fields">
    private T firstName;

    private T lastName;

    private T email;

    private T password;

    private T confirmPassword;
    // </editor-fold>

    // <editor-fold desc="Ctor">
    public RegisterRequest() {
    }
    // </editor-fold>

    // <editor-fold desc="Getters and Setters">
    public T getFirstName() {
        return firstName;
    }

    public void setFirstName(T firstName) {
        this.firstName = firstName;
    }

    public T getLastName() {
        return lastName;
    }

    public void setLastName(T lastName) {
        this.lastName = lastName;
    }

    public T getEmail() {
        return email;
    }

    public void setEmail(T email) {
        this.email = email;
    }

    public T getPassword() {
        return password;
    }

    public void setPassword(T password) {
        this.password = password;
    }

    public T getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(T confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
    // </editor-fold>

    // <editor-fold desc="Overrides">
    @Override
    public String toString() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(this);
        } catch (Exception e) {
            return super.toString();
        }
    }
    // </editor-fold>

}
