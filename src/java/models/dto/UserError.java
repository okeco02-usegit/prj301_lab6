package models.dto;

import java.io.Serializable;

public class UserError implements Serializable {
    private String userNameError;
    private String passwordError;
    private String lastNameError;
    private String duplicateUserName;

    public String getUserNameError() { return userNameError; }
    public void setUserNameError(String value) { userNameError = value; }
    public String getPasswordError() { return passwordError; }
    public void setPasswordError(String value) { passwordError = value; }
    public String getLastNameError() { return lastNameError; }
    public void setLastNameError(String value) { lastNameError = value; }
    public String getDuplicateUserName() { return duplicateUserName; }
    public void setDuplicateUserName(String value) { duplicateUserName = value; }
}
