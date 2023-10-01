package com.rubem.oliota.api2023.pt.controller;

import com.rubem.oliota.api2023.pt.model.UserSystem;
import com.rubem.oliota.api2023.pt.service.UserSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing user systems.
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/users")
public class UserSystemController {

    @Autowired
    private UserSystemService userSystemService;

    @GetMapping
    public ResponseEntity<List<UserSystem>> getAllUsers() {
        List<UserSystem> users = userSystemService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserSystem> getUserById(@PathVariable Long id) {
        UserSystem user = userSystemService.getUserById(id);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<UserSystem> createUser(@RequestBody UserSystem user) {
        UserSystem newUser = userSystemService.createUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserSystem> updateUser(@PathVariable Long id, @RequestBody UserSystem updatedUser) {
        UserSystem user = userSystemService.updateUser(id, updatedUser);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        boolean deleted = userSystemService.deleteUser(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
