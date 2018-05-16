package com.udea.edu.compmovil.RestService.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.Gson;
import com.udea.edu.compmovil.RestService.exception.ResourceNotFoundException;
import com.udea.edu.compmovil.RestService.model.User;
import com.udea.edu.compmovil.RestService.repository.UserRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    UserRepository userRepository;

    private User user;
    private JSONObject jsonObjectUser;

    @GetMapping("/allUsers")
    public String allUsersView(Map<String, Object> model) {
        model.put("users", userRepository.findAll());
        return "users";
    }

    @GetMapping("/users")
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @PostMapping("/user")
    @ResponseBody
    public String createUser(@Valid @RequestBody String user) throws JSONException {
        jsonObjectUser = new JSONObject(user);
        this.user = new User();
        this.user.setName(jsonObjectUser.getString("name"));
        this.user.setPassword(jsonObjectUser.getString("password"));
        this.user.setMail(jsonObjectUser.getString("email"));
        this.user.setPicture(jsonObjectUser.getString("picture"));
        this.user.setState(jsonObjectUser.getString("state"));
        this.user.setUser(jsonObjectUser.getString("user"));
        this.user.setSession(jsonObjectUser.getString("session"));

        this.userRepository.save(this.user);

        return "{'msg':'Registro exitoso'}";
    }

    @GetMapping("/user/{user}")
    public String getUserById(@PathVariable(value = "user") String user) {
        User usr = userRepository.findByPk(user);
        if(usr != null) {
            JSONObject response = new JSONObject();
            try {
                response.put("name", usr.getName());
                response.put("user", usr.getUser());
                response.put("email", usr.getMail());
                response.put("password", usr.getPassword());
                response.put("picture", usr.getPicture());
                response.put("state", usr.getState());
                response.put("session", usr.getSession());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return response.toString();
        }
        return "{'msg':'User/Password is incorrect'}";
    }

    /*@PutMapping("/user/{user}")
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
    }*/

    @DeleteMapping("/user/{user}")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "user") String user) {
        //User userNote = this.userRepository.findByPk(user);

        //this.userRepository.delete(userNote);

        return ResponseEntity.ok().build();
    }
}
