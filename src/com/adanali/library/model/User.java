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

    public User(String name, String email, String password) {
        setName(name);
        setEmail(email);
        setPassword(password);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (StringUtil.isNotNullOrBlank(name)) {
            this.name = name;
        } else {
            System.err.println("Name cannot be empty!");
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (StringUtil.isValidEmail(email)) {
            this.email = email;
        } else {
            System.err.println("Pass a valid email.");
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (StringUtil.isValidPassword(password)) {
            this.password = password;
        } else {
            System.err.println("Not a valid Password");
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
        return Objects.equals(getName(), user.getName()) && Objects.equals(getEmail(), user.getEmail()) && Objects.equals(getPassword(), user.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getEmail(), getPassword());
    }

    @Override
    public String toString() {
        return String.format("User[E-mail=%s, Name=%s, Role=%s]", email, name, getRole());
    }
}
