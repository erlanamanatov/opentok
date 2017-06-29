package com.erkprog.opentok;

public class User {
    private String uId;
    private String displayName;
    private String email;

    public User(String uId, String displayName, String email) {
        this.uId = uId;
        this.displayName = displayName;
        this.email = email;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

