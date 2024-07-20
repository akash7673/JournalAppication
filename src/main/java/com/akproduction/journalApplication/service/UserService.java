package com.akproduction.journalApplication.service;

import com.akproduction.journalApplication.entity.User;
import com.akproduction.journalApplication.repository.UserRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserService {
    @Autowired
    private UserRepo userRepo;


    public void postUser(User user){
        userRepo.save(user);
    }

    public List<User> getAllUser(){
        return userRepo.findAll();
    }


    public Optional<User> getUserById(ObjectId id){
        return userRepo.findById(String.valueOf(id));
    }


    public void deleteUserById(ObjectId id){
        userRepo.deleteById(String.valueOf(id));
    }

    public User findByUserName(String userName){
        return userRepo.findByUserName(userName);
    }
}
