package com.akproduction.journalApplication.service;

import com.akproduction.journalApplication.entity.JournalEntry;
import com.akproduction.journalApplication.entity.User;
import com.akproduction.journalApplication.repository.JournalEntryRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepo journalEntryRepo;
    @Autowired
    private UserService userService;

    public void postJournalEntry(JournalEntry journalEntry, String userName){
        User user = userService.findByUserName(userName);
        journalEntry.setDate(LocalDateTime.now());
        JournalEntry saved = journalEntryRepo.save(journalEntry);
        user.getJournalEntries().add(saved);
        userService.postUser(user);
    }

    public void postJournalEntry(JournalEntry journalEntry){
        journalEntryRepo.save(journalEntry);
    }

    public List<JournalEntry> getAllJournalEntry(){
        return journalEntryRepo.findAll();
    }


    public Optional<JournalEntry> getJournalEntryById(ObjectId id){
        return journalEntryRepo.findById(String.valueOf(id));
    }


    public void deleteJournalEntryById(ObjectId id, String userName){
        User user = userService.findByUserName(userName);
        user.getJournalEntries().removeIf(journal -> journal.getId().equals(id));
        userService.postUser(user);
        journalEntryRepo.deleteById(String.valueOf(id));
    }
}
