package com.example.springboot.controllers;


import com.example.springboot.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.example.springboot.service.userservice;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/healthub/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {
    @Autowired
    private userservice userService;
    private String userName;
    private String password;

    @PostMapping("/add")
   public ResponseEntity<?> createUser(@Valid @RequestBody User user) {
        User savedUser;
        try{
       savedUser = userService.createUser(user);}
        catch(IllegalArgumentException e ){
            return new ResponseEntity<>("Username already exists",HttpStatus.BAD_REQUEST);
        }
       return new ResponseEntity<> (savedUser, HttpStatus.CREATED);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<User> authenticate( @RequestBody User user) {
        this.userName = user.getName();
        this.password = user.getPassword();
        User user1 = userService.authenticate(userName, password);
        return ResponseEntity.ok(user1);
    }
      @PutMapping("/{id}")
        public ResponseEntity<Object> updateUser(@PathVariable Long id, @Valid @RequestBody User userDetails) {
          User updatedUser;
          try{
              updatedUser = userService.updateUser(id, userDetails);}
          catch(IllegalArgumentException e ){
              return new ResponseEntity<>("Username already exists",HttpStatus.BAD_REQUEST);
          }
            return ResponseEntity.ok(updatedUser);
        }
    @PutMapping("/username/{id}")
    public ResponseEntity<Object> updateUsername(@PathVariable Long id, @Valid @RequestBody User userDetails) {
        User updatedUser;
        try{
            updatedUser = userService.updateUsername(id, userDetails);}
        catch(IllegalArgumentException e ){
            return new ResponseEntity<>("Username already exists",HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(updatedUser);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Object> getUser(@PathVariable Long id) {
        User updatedUser = userService.getUser(id);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
        public ResponseEntity<User> deleteUser(@PathVariable Long id) {
           User user= userService.deleteUser(id);
            return ResponseEntity.ok(user);
        }
    }
