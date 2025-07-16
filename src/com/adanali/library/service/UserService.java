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

    public void addUser(User user){
        if (user != null && getUserByEmail(user.getEmail()) == null){
            users.add(user);
        }else {
            System.err.println("Pass a valid user!");
        }
    }

    public boolean removeUser(String email){
        if (StringUtil.isValidEmail(email)){
            return users.removeIf(u -> u.getEmail().equals(email));
        }else {
            System.err.println("Pass a valid E-mail!");
        }
        return false;
    }

    public boolean updateUser(String email, User user){
        if (StringUtil.isValidEmail(email) && user!=null){
            for (int i=0 ; i<users.size() ; i++){
                if (users.get(i).getEmail().equals(email)){
                    users.set(i , user);
                    return true;
                }
            }
        }else {
            System.err.println("Pass valid Arguments!");
        }
        return false;
    }

    public User getUserByEmail(String email){
        if(StringUtil.isValidEmail(email)){
            for (User user : users){
                if (user.getEmail().equals(email)){
                    return user;
                }
            }
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
        }else {
            System.err.println("Pass a valid query");
        }
        return result;
    }

}
