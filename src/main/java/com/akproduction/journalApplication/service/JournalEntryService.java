package com.akproduction.journalApplication.service;

import com.akproduction.journalApplication.entity.JournalEntry;
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

    public void postJournalEntry(JournalEntry journalEntry){
        journalEntry.setDate(LocalDateTime.now());
        journalEntryRepo.save(journalEntry);
    }

    public List<JournalEntry> getAllJournalEntry(){
        return journalEntryRepo.findAll();
    }


    public Optional<JournalEntry> getJournalEntryById(ObjectId id){
        return journalEntryRepo.findById(String.valueOf(id));
    }


    public void deleteJournalEntryById(ObjectId id){
        journalEntryRepo.deleteById(String.valueOf(id));
    }
}
