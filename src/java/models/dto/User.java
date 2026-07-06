package models.dto;

import java.io.Serializable;

public class User implements Serializable {
    private String userName;
    private String password;
    private String lastName;
    private boolean admin;

    public User() {
    }

    public User(String userName, String password, String lastName, boolean admin) {
        this.userName = userName;
        this.password = password;
        this.lastName = lastName;
        this.admin = admin;
    }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public boolean isAdmin() { return admin; }
    public void setAdmin(boolean admin) { this.admin = admin; }
}
