package com.udea.edu.compmovil.RestService.controller;

import com.google.gson.Gson;
import com.udea.edu.compmovil.RestService.exception.ResourceNotFoundException;
import com.udea.edu.compmovil.RestService.model.User;
import com.udea.edu.compmovil.RestService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    UserRepository userRepository;


    @GetMapping("/allUsers")
    public String allUsersView(Map<String, Object> model) {
        model.put("users", userRepository.findAll());
        return "users";
    }

    @GetMapping("/users")
    public List<User> getAllUser(){
        return userRepository.findAll();
    }

    @PostMapping("/user")
    public User createUser(@Valid @RequestBody User user) {
        Gson gson = new Gson();
        gson.toJson(this.userRepository.save(user));
        return gson.toJson(user);
    }

    @GetMapping("/user/{user}")
    public User getUserById(@PathVariable(value = "user") String user) {
        return userRepository.findByPk(user);
    }

    @PutMapping("/user/{user}")
    public User updateUser(@PathVariable(value = "user") String user,
                           @Valid @RequestBody User userDetails) {

        User userNote = userRepository.findByPk(user);

        userNote.setName(userDetails.getName());
        userNote.setPassword(userDetails.getPassword());
        userNote.setMail(userDetails.getMail());
        userNote.setState(userDetails.getState());
        userNote.setUser(userDetails.getUser());
        userNote.setSession(userDetails.getSession());
        userNote.setPicture(userDetails.getPicture());

        User updatedUser = userRepository.save(userNote);
        return updatedUser;
    }

    @DeleteMapping("/user/{user}")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "user") String user) {
        User userNote = this.userRepository.findByPk(user);

        this.userRepository.delete(userNote);

        return ResponseEntity.ok().build();
    }
}
