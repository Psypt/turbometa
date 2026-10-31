package com.turbometa.model;

public class User {

    private Long id;
    private String username;
    private String email;
    private String passwordHash;
    private boolean active;
    private boolean isAdmin;

    public User() {
    }

    public User(String username, String email, String passwordHash, boolean active, boolean isAdmin) {
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.active = active;
        this.isAdmin = isAdmin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
