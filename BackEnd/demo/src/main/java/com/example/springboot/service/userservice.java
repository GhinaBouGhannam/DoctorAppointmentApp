package com.example.springboot.service;

import com.example.springboot.controllers.ValidationHandler;
import com.example.springboot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.example.springboot.repository.userRepository;
import org.w3c.dom.html.HTMLTableCaptionElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class userservice {

        @Autowired
        private userRepository userRepository;

        public User createUser(User user) {
           if( findByUserName(user.getName())){
               throw new IllegalArgumentException();
           }
            return userRepository.save(user);
        }

    public User updateUser(Long id, User userDetails) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            if( countUserName(userDetails.getName(),1)){
                throw new IllegalArgumentException();
            }
            User user = userOptional.get();
            user.setName(userDetails.getName());
            user.setPassword(userDetails.getPassword());
            user.setPhone_number(userDetails.getPhone_number());
            return userRepository.save(user);
        } else {
            return null;
        }
    }
    public User updateUsername(Long id, User userDetails) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            if( countUserName(userDetails.getName(),0)){
                throw new IllegalArgumentException();
            }
            User user = userOptional.get();
            user.setName(userDetails.getName());
            user.setPassword(userDetails.getPassword());
            user.setPhone_number(userDetails.getPhone_number());
            return userRepository.save(user);
        } else {
            return null;
        }
    }

    public User deleteUser(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            userRepository.delete(user);
        }
        return userOptional.get();
    }

    public boolean findByUserName(String name){
         if(userRepository.findByUserName(name).isPresent())
             return true;
         return false;
    }

    public boolean countUserName(String name,int x){
           int i= userRepository.countByUserName(name);
            return i>x;
    }


    public User authenticate(String user_name, String password) {
        Optional<User> userOptional = userRepository.findByUserNameAndPassword(user_name, password);
        return userOptional.orElse(null);
    }

    public User getUser(Long id) {
            return userRepository.getUserById(id);
    }
}
