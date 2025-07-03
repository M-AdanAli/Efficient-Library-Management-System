package com.adanali.library.model;

/**
 * Abstract base class representing a user in the library system.
 */
public abstract class User {
    private String name;
    private String userId;
    private String password;

    public User(String name, String userId, String password) {
        setName(name);
        setUserId(userId);
        setPassword(password);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            System.err.println("Name cannot be empty!");
        } else {
            this.name = name;
        }
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        if (userId == null || userId.isEmpty()) {
            System.err.println("User Id cannot be empty!");
        } else {
            this.userId = userId;
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        // For now, only digits allowed. Will relax it later
        if (password == null || password.isEmpty() || !password.matches("\\d+")) {
            System.err.println("Not a valid Password");
        } else {
            this.password = password;
        }
    }

    /**
     * Abstract method to get the user's role.
     */
    public abstract String getRole();

    @Override
    public String toString() {
        return String.format("User[ID=%s, Name=%s, Role=%s]", userId, name, getRole());
    }
}
