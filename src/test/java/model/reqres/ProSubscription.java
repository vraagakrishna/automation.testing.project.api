package model.reqres;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProSubscription<T> {

    // <editor-fold desc="Class Fields">
    private T email;
    // </editor-fold>

    // <editor-fold desc="Ctor">
    public ProSubscription() {
    }
    // </editor-fold>

    // <editor-fold desc="Getters and Setters">
    public T getEmail() {
        return email;
    }

    public void setEmail(T email) {
        this.email = email;
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
