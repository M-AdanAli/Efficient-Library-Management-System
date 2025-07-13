package com.adanali.library.service;

import com.adanali.library.model.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserService {
    List<User> users;

    public UserService(){
        users = new ArrayList<>();
    }

    public void addUser(User user){
        if (user != null && getUserById(user.getUserId()) == null){
            users.add(user);
        }else {
            System.err.println("Pass a valid user!");
        }
    }

    public boolean removeUser(String userId){
        if (userId != null && !userId.isEmpty() && userId.matches("\\d+")){
            return users.removeIf(u -> u.getUserId().equals(userId));
        }else {
            System.err.println("Pass a valid UserId!");
        }
        return false;
    }

    public boolean updateUser(String userId , User user){
        if (userId != null && !userId.isEmpty() && userId.matches("\\d+") && user!=null){
            for (int i=0 ; i<users.size() ; i++){
                if (users.get(i).getUserId().equals(userId)){
                    users.set(i , user);
                    return true;
                }
            }
        }else {
            System.err.println("Pass valid Arguments!");
        }
        return false;
    }

    public User getUserById(String userId){
        if(userId != null && !userId.isEmpty() && userId.matches("\\d+")){
            for (User user : users){
                if (user.getUserId().equals(userId)){
                    return user;
                }
            }
        }else {
            System.err.println("Pass a valid UserId!");
        }
        return null;
    }

    public List<User> listAllUsers(){
        return Collections.unmodifiableList(users);
    }

    public List<User> searchUser(String query){
        List<User> result = new ArrayList<>();
        if (query != null && !query.isEmpty()){
            query = query.toLowerCase();
            for(User user : users){
                String userIdLowercase = user.getUserId().toLowerCase();
                String nameLowercase = user.getName().toLowerCase();
               if (userIdLowercase.contains(query) || nameLowercase.contains(query)){
                   result.add(user);
               }
            }
        }else {
            System.err.println("Pass a valid query");
        }
        return result;
    }

}
