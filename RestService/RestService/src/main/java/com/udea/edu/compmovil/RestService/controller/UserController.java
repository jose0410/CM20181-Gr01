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
import java.util.Random;

import static java.lang.Integer.parseInt;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    UserRepository userRepository;

    private User user;
    private JSONObject jsonObjectUser;
    private Random randomGenerator = new Random();

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

        this.user.setId(randomGenerator.nextInt(1000000000));
        this.user.setName(jsonObjectUser.getString("name"));
        this.user.setPassword(jsonObjectUser.getString("password"));
        this.user.setMail(jsonObjectUser.getString("email"));
        this.user.setPicture(jsonObjectUser.getString("picture"));
        this.user.setState(jsonObjectUser.getString("state"));
        this.user.setUser(jsonObjectUser.getString("user"));
        this.user.setSession(jsonObjectUser.getString("session"));

        this.userRepository.save(this.user);

        return "{'msg':'Register successful'}";
    }

    @GetMapping("/user/{user}")
    public String getUserById(@PathVariable(value = "user") String user) {
        User usr = userRepository.findByUser(user);
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

    @PutMapping("/updateuser/{user}")
    public String updateUser(@PathVariable(value = "user") String user,
                           @Valid @RequestBody String details) throws JSONException {

        User userNote = userRepository.findByUser(user);
        JSONObject userDetails = new JSONObject(details);

        userNote.setName(userDetails.getString("name"));
        userNote.setPassword(userDetails.getString("password"));
        userNote.setMail(userDetails.getString("email"));
        userNote.setState(userDetails.getString("state"));
        userNote.setUser(userDetails.getString("user"));
        userNote.setSession(userDetails.getString("session"));
        userNote.setPicture(userDetails.getString("picture"));

        this.userRepository.save(userNote);

        return "{'msg':'Update successful'}";
    }

    @DeleteMapping("/deleteuser/{user}")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "user") String user) {
        User userNote = this.userRepository.findByUser(user);

        this.userRepository.delete(userNote);

        return ResponseEntity.ok().build();
    }
}
