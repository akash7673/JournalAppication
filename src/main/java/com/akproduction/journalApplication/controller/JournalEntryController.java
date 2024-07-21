package com.akproduction.journalApplication.controller;

import com.akproduction.journalApplication.entity.JournalEntry;
import com.akproduction.journalApplication.entity.User;
import com.akproduction.journalApplication.service.JournalEntryService;
import com.akproduction.journalApplication.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.*;

//@RestController
//@RequestMapping("/journal")
//public class JournalEntryController {
//
//    private Map<Long, JournalEntry> journalEntries = new HashMap<>();
//
//    @GetMapping("/getAllJournals")
//    public List<JournalEntry> getAllJournal(){
//        return new ArrayList<>(journalEntries.values());
//    }
//
//    @PostMapping("/postJournal")
//    public boolean postJournal(@RequestBody JournalEntry newEntry){
//        journalEntries.put(newEntry.getId(), newEntry);
//        return true;
//    }
//
//    @GetMapping("getJournalById/{id}")
//    public JournalEntry getJournalById(@PathVariable Long id){
//        return journalEntries.get(id);
//    }
//
//    @DeleteMapping("deleteJournalById/{id}")
//    public JournalEntry deleteJournalById(@PathVariable Long id){
//        return journalEntries.remove(id);
//    }
//
//
//    @PutMapping("updateJournalById/{id}")
//    public JournalEntry updateJournalById(@PathVariable Long id, @RequestBody JournalEntry updatedJournal){
//        return journalEntries.put(id, updatedJournal);
//    }
//}

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;
    @Autowired
    private UserService userService;

    @GetMapping("/getAllJournals/{userName}")
    public ResponseEntity<List<JournalEntry>> getAllJournalEntriesOfUser(@PathVariable String userName){
        User user = userService.findByUserName(userName);
        if(user != null){
            List<JournalEntry> listOfJournalEntry = user.getJournalEntries();
            if(listOfJournalEntry != null && !listOfJournalEntry.isEmpty()){
                return new ResponseEntity<List<JournalEntry>>(listOfJournalEntry, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/postJournal/{userName}")
    public ResponseEntity<JournalEntry> postJournalEntry(@RequestBody JournalEntry newEntry, @PathVariable String userName){
        try{
            journalEntryService.postJournalEntry(newEntry, userName);
            return new ResponseEntity<JournalEntry>(newEntry, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("getJournalById/{id}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId id){
        Optional<JournalEntry> journalEntry = journalEntryService.getJournalEntryById(id);
        if(journalEntry.isPresent()){
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("deleteJournalById/{userName}/{id}")
    public ResponseEntity<?> deleteJournalById(@PathVariable ObjectId id, @PathVariable String userName){
        journalEntryService.deleteJournalEntryById(id, userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PutMapping("updateJournalById/{userName}/{id}")
    public ResponseEntity<JournalEntry> updateJournalById(@PathVariable ObjectId id, @RequestBody JournalEntry updatedJournal, @PathVariable String userName){
        JournalEntry journalEntry = journalEntryService.getJournalEntryById(id).orElse(null);
        if(journalEntry != null){
            journalEntry.setContent(updatedJournal.getContent() != null && !updatedJournal.getContent().equals("") ? updatedJournal.getContent() : journalEntry.getContent());
            journalEntry.setTitle(updatedJournal.getTitle() != null && !updatedJournal.getTitle().equals("") ? updatedJournal.getTitle() : journalEntry.getTitle());
            journalEntryService.postJournalEntry(journalEntry);
            return new ResponseEntity<>(journalEntry, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
