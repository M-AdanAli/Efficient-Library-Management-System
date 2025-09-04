package com.adanali.library.service;

import com.adanali.library.model.User;
import com.adanali.library.repository.UsersRepository;
import com.adanali.library.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class UserService {
    private UsersRepository usersRepository;

    public UserService(){
        usersRepository = new UsersRepository();
    }

    public User authenticate(String email, String password){
        if (StringUtil.isValidEmail(email)){
            User user = getUserByEmail(email).orElse(null);
            if (user != null){
                if (user.getPassword().equals(password)){
                    return user;
                }else{
                    System.err.println("Invalid Password!");
                }
            }else {
                System.err.println("User does not exists!");
            }
        }else{
            System.err.println("Pass a valid E-mail!");
        }
        return null;
    }

    public boolean addUser(User user){
        if (user != null){
            usersRepository.add(user);
            return true;
        }else {
            System.err.println("Pass a valid user!");
            return false;
        }
    }

    public boolean removeUser(String email){
        if (StringUtil.isValidEmail(email)){
            return usersRepository.remove(email);
        }else {
            System.err.println("Pass a valid E-mail!");
            return false;
        }
    }

    public Optional<User> getUserByEmail(String email){
        if (StringUtil.isValidEmail(email)){
            return usersRepository.getById(email);
        }
        return Optional.empty();
    }

    public List<User> listAllUsers(){
        return usersRepository.getAll();
    }

    public List<User> searchUsers(String query){
        if (StringUtil.isNotNullOrBlank(query)){
            return usersRepository.search(query);
        }else {
            System.err.println("Invalid Arguments!");
        }
        return Collections.unmodifiableList(new ArrayList<>(0));
    }

    public boolean updateUserName(String email, String newName){
        if (StringUtil.isValidEmail(email) && StringUtil.isNotNullOrBlank(newName)){
            User user = getUserByEmail(email).get();
            if (user != null){
                user.setName(newName);
                return true;
            }
            System.err.println("User does not exists!");
            return false;
        }else {
            System.err.println("Invalid E-mail or Name!");
            return false;
        }
    }

    public boolean updatePassword(String email, String newPassword){
        if (StringUtil.isValidEmail(email) && StringUtil.isValidPassword(newPassword)){
            User user = getUserByEmail(email).get();
            if (user != null){
                user.setPassword(newPassword);
                return true;
            }
            System.err.println("User does not exists!");
            return false;
        }else {
            System.err.println("Invalid email or new Password!");
            return false;
        }
    }

}
