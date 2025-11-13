package model.ndosiautomation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateRequest<T> {

    // <editor-fold desc="Class Fields">
    @JsonProperty("firstName")
    private T firstName;

    @JsonProperty("lastName")
    private T lastName;

    private T email;
    // </editor-fold>

    // <editor-fold desc="Ctor">
    public UpdateRequest() {
    }
    // </editor-fold>

    // <editor-fold desc="Getters and Setters">
    public T getEmail() {
        return email;
    }

    public void setEmail(T email) {
        this.email = email;
    }

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
