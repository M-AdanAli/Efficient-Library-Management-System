package com.adanali.library.model;

public abstract class User {

    String name;
    String userId;
    String userPassword;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isEmpty()){
            System.err.println("Name cannot be empty!");
        }else{
            this.name = name;
        }
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        if (userId == null || userId.isEmpty()){
            System.err.println("User Id cannot be empty!");
        }else {
            this.userId = userId;
        }
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        if (userPassword == null || userPassword.isEmpty() || !userPassword.matches("\\d+")){
            System.err.println("Not a valid Password");
        }else {
            this.userPassword = userPassword;
        }
    }
}