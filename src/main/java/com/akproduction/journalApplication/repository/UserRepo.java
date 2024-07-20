package com.akproduction.journalApplication.repository;

import com.akproduction.journalApplication.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepo extends MongoRepository<User, String> {
    User findByUserName(String username);
}
