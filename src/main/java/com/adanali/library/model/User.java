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

    protected User(String name, String email, String password){
        setName(name);
        setEmail(email);
        setPassword(password);
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        StringUtil.validateNotNullOrBlank(name,"User Name");
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (StringUtil.isValidEmail(email)) {
            this.email = email;
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (StringUtil.isValidPassword(password)) {
            this.password = password;
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
        return String.format("%-15s | %-15s",getName(),getEmail());
    }
}
