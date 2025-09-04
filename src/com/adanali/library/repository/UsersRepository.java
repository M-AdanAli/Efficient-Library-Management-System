package com.adanali.library.repository;

import com.adanali.library.model.Librarian;
import com.adanali.library.model.Student;
import com.adanali.library.model.User;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class UsersRepository implements RepositoryPattern<User,String>{
    private Set<User> userSet ;

    public UsersRepository(){
        this.userSet = new HashSet<>();
        this.userSet.add(new Librarian("Adan Ali", "madnog007@gmail.com", "P@kistan786*"));
        this.userSet.add(new Student("Rabbet Ali", "beetu@gmail.com", "P@kistan786*", "Model Colony"));
        System.out.println(this.userSet);
    }

    public List<User> search(String query){
        return userSet.stream()
                .filter(user -> user.getName().toLowerCase().contains(query) || user.getEmail().toLowerCase().contains(query))
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public boolean add(User user) {
        if (getById(user.getEmail()).isEmpty()){
            userSet.add(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean remove(String email) {
        return userSet.removeIf(user -> user.getEmail().equals(email));
    }

    @Override
    public Optional<User> getById(String email) {
        return userSet.stream().filter(user -> user.getEmail().equals(email)).findFirst();
    }

    @Override
    public List<User> getAll() {
        return List.copyOf(userSet);
    }
}
