package com.pwc.offering.demo.controller;

import com.pwc.offering.demo.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/candidates")
public class CandiatesController{

    //Logging
//    Logger logger = LoggerFactory.getLogger(CandiatesController.class);

    //Replaced by DB
    static List<Candidate> allCandidates = new ArrayList<>();

    @GetMapping("/all")
    public ResponseEntity<?> GetAllCandidates(){
        return ResponseEntity.ok(allCandidates);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> GetCandidateById(@PathVariable("id") String id){
        allCandidates.add(new Candidate(id));
        return ResponseEntity.ok(allCandidates);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addCandidate(@RequestBody Candidate candidate){
        allCandidates.add(candidate);
        return ResponseEntity.status(HttpStatus.CREATED).body("Candidate with Id:"+candidate.getId()+" created successfully");
    }

    @PutMapping("/edit")
    public ResponseEntity<?> updatedCandidate(@RequestBody Candidate candidate){
        Candidate current = allCandidates.stream().filter(p -> p.getId().equalsIgnoreCase(candidate.getId())).findFirst().orElse(null);
        if(current == null)
            return ResponseEntity.badRequest().body("no candidate exists with the provided id");
        else {
            allCandidates.remove(current);
            allCandidates.add(candidate);
            return ResponseEntity.ok().body("Candidate details updated successfully");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCandidateById(@PathVariable("id") String id){
        Candidate current = allCandidates.stream().filter(p -> p.getId().equalsIgnoreCase(id)).findFirst().orElse(null);
        if(current == null)
            return ResponseEntity.badRequest().body("no candidate exists with the provided id");
        else {
            allCandidates.remove(current);
            return ResponseEntity.ok().body("Candidate deleted successfully");
        }
    }

}
