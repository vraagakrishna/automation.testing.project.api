package model.ndosiautomation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginRequest<T> {

    // <editor-fold desc="Class Fields">
    private T email;

    private T password;
    // </editor-fold>

    // <editor-fold desc="Ctor">
    public LoginRequest() {
    }
    // </editor-fold>

    // <editor-fold desc="Getters and Setters">
    public T getEmail() {
        return email;
    }

    public void setEmail(T username) {
        this.email = username;
    }

    public T getPassword() {
        return password;
    }

    public void setPassword(T password) {
        this.password = password;
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
