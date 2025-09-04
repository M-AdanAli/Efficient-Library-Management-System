package com.adanali.library.model;

import com.adanali.library.util.StringUtil;

import java.util.Objects;

/**
 * Abstract base class representing a user in the library system.
 */
public abstract class User {
    private String name;
    private String email;
    private String password;

    protected User(String name, String email, String password) {
        setName(name);
        setEmail(email);
        setPassword(password);
    }

    public String getName() {
        return name;
    }
    public boolean setName(String name) {
        if (StringUtil.isNotNullOrBlank(name)) {
            this.name = name;
            return true;
        } else {
            System.err.println("Name cannot be empty!");
            return false;
        }
    }

    public String getEmail() {
        return email;
    }

    public boolean setEmail(String email) {
        if (StringUtil.isValidEmail(email)) {
            this.email = email;
            return true;
        } else {
            System.err.println("Pass a valid email.");
            return false;
        }
    }

    public String getPassword() {
        return password;
    }

    public boolean setPassword(String password) {
        if (StringUtil.isValidPassword(password)) {
            this.password = password;
            return true;
        } else {
            System.err.println("Not a valid Password");
            return false;
        }
    }

    /**
     * Abstract method to get the user's role.
     */
    public abstract String getRole();

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(this.getEmail(), user.getEmail());
    }

    @Override
    public int hashCode() {
        return this.getEmail().hashCode();
    }

    @Override
    public String toString() {
        return String.format("User[E-mail=%s, Name=%s, Role=%s]", getEmail(), getName(), getRole());
    }
}
