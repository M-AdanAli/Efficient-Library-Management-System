package com.adanali.library.service;

import com.adanali.library.exceptions.EntityDuplicationException;
import com.adanali.library.exceptions.InvalidCredentialsException;
import com.adanali.library.exceptions.EntityNotFoundException;
import com.adanali.library.model.Librarian;
import com.adanali.library.model.Student;
import com.adanali.library.model.User;
import com.adanali.library.repository.UsersRepository;
import com.adanali.library.util.StringUtil;

import java.util.*;

public class UserService {
    private UsersRepository usersRepository;

    public UserService(){
        usersRepository = new UsersRepository();
    }

    public User authenticate(String email, String password) throws EntityNotFoundException, InvalidCredentialsException {
        User user = getUserByEmail(email).orElseThrow(() -> new EntityNotFoundException("User not found with E-mail : " + email));
        if (user.getPassword().equals(password)){
            return user;
        }else {
            throw new InvalidCredentialsException("Invalid Password!");
        }
    }

    public enum UserRole{
        LIBRARIAN, STUDENT
    }

    public void addUser(String name, String email, String password, String address,UserRole role) throws EntityDuplicationException {
        if (StringUtil.isValidEmail(email) && StringUtil.isValidPassword(password)){
            StringUtil.validateNotNullOrBlank(name,"User Name");
            if (Objects.requireNonNull(role) == UserRole.STUDENT) {
                StringUtil.validateNotNullOrBlank(address,"Address");
                usersRepository.add(new Student(name, email, password, address));
            } else if (role == UserRole.LIBRARIAN) {
                usersRepository.add(new Librarian(name, email, password));
            }
        }
    }

    public void removeUser(String email){
        if (StringUtil.isValidEmail(email)) {
            usersRepository.remove(email);
        }
    }

    public Optional<User> getUserByEmail(String email){
        if (StringUtil.isValidEmail(email)){
            return usersRepository.getById(email);
        }return Optional.empty(); // Unreachable because in case of false exception will be thrown
    }

    public List<User> listAllUsers(){
        return usersRepository.getAll();
    }

    public List<User> searchUsers(String query){
        StringUtil.validateNotNullOrBlank(query,"Search Query");
        return usersRepository.search(query);
    }

    public void updateUserName(String email, String newName) throws EntityNotFoundException {
        User user = getUserByEmail(email).orElseThrow(() -> new EntityNotFoundException("User not found with E-mail : " + email));
        user.setName(newName);
    }

    public void updatePassword(String email, String newPassword) throws EntityNotFoundException {
        User user = getUserByEmail(email).orElseThrow(() -> new EntityNotFoundException("User not found with E-mail : " + email));
        user.setPassword(newPassword);
    }

}
