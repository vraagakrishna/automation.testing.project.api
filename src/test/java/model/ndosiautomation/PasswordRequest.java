package model.ndosiautomation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PasswordRequest<T> {

    // <editor-fold desc="Class Fields">
    private T currentPassword;

    private T newPassword;

    private T confirmPassword;
    // </editor-fold>

    // <editor-fold desc="Ctor">
    public PasswordRequest() {
    }
    // </editor-fold>

    // <editor-fold desc="Getters and Setters">
    public T getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(T currentPassword) {
        this.currentPassword = currentPassword;
    }

    public T getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(T newPassword) {
        this.newPassword = newPassword;
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
