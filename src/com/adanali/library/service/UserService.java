package com.adanali.library.service;

import com.adanali.library.model.User;
import com.adanali.library.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserService {
    List<User> users;

    public UserService(){
        users = new ArrayList<>();
    }

    public String authenticate(String email, String password){
        if (StringUtil.isValidEmail(email)){
            User user = getUserByEmail(email);
            if (user != null && user.getPassword().equals(password)){
                return user.getRole();
            }
        }
        return null;
    }

    public void addUser(User user){
        if (user != null && getUserByEmail(user.getEmail()) == null){
            users.add(user);
        }else {
            System.err.println("Pass a valid user!");
        }
    }

    public boolean removeUser(String email){
        if (StringUtil.isValidEmail(email)){
            return users.removeIf(u -> u.getEmail().equalsIgnoreCase(email));
        }else {
            System.err.println("Pass a valid E-mail!");
        }
        return false;
    }

    public boolean updateUserName(String email, String newName){
        if (StringUtil.isValidEmail(email) && StringUtil.isNotNullOrBlank(newName)){
            User user = getUserByEmail(email);
            if (user != null){
                user.setName(newName);
                return true;
            }
        }else {
            System.err.println("Invalid email or name!");
        }
        return false;
    }

    public boolean updatePassword(String email, String newPassword){
        if (StringUtil.isValidEmail(email) && StringUtil.isValidPassword(newPassword)){
            User user = getUserByEmail(email);
            if (user != null){
                user.setPassword(newPassword);
                return true;
            }
        }else {
            System.err.println("Invalid email or new Password!");
        }
        return false;
    }

    public User getUserByEmail(String email){
        if(StringUtil.isValidEmail(email)){
            for (User user : users){
                if (user.getEmail().equalsIgnoreCase(email)){
                    return user;
                }
            }
            System.err.println("User does not exists!");
        }else {
            System.err.println("Pass a valid E-mail!");
        }
        return null;
    }

    public List<User> listAllUsers(){
        return Collections.unmodifiableList(users);
    }

    public List<User> searchUser(String query){
        List<User> result = new ArrayList<>();
        if (StringUtil.isNotNullOrBlank(query)){
            query = query.toLowerCase();
            for(User user : users){
                String emailLowercase = user.getEmail().toLowerCase();
                String nameLowercase = user.getName().toLowerCase();
               if (emailLowercase.contains(query) || nameLowercase.contains(query)){
                   result.add(user);
               }
            }
        }
        return Collections.unmodifiableList(result);
    }

}
