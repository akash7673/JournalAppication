package com.akproduction.journalApplication.controller;

import com.akproduction.journalApplication.entity.User;
import com.akproduction.journalApplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getAll")
    public List<User> getAllUsers(){
        return userService.getAllUser();
    }

    @PostMapping("/postUser")
    public void postUser(@RequestBody User user){
        userService.postUser(user);
    }

    @PutMapping("/updateUser/{userName}")
    public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable String userName){
        User exsitingUser = userService.findByUserName(userName);
        if(exsitingUser != null){
            exsitingUser.setUserName(user.getUserName());
            exsitingUser.setPassword(user.getPassword());
            userService.postUser(exsitingUser);
        }
        return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
