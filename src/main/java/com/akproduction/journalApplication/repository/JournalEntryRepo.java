package com.akproduction.journalApplication.repository;

import com.akproduction.journalApplication.entity.JournalEntry;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournalEntryRepo extends MongoRepository<JournalEntry, String> {
}
