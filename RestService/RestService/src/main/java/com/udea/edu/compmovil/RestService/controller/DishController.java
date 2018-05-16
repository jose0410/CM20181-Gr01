package com.udea.edu.compmovil.RestService.controller;

import com.udea.edu.compmovil.RestService.exception.ResourceNotFoundException;
import com.udea.edu.compmovil.RestService.model.Dish;
import com.udea.edu.compmovil.RestService.model.Drink;
import com.udea.edu.compmovil.RestService.repository.DishRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseInt;

@RestController
@RequestMapping("/api")
public class DishController {
    @Autowired
    DishRepository dishRepository;

    private Dish dish;
    private JSONObject jsonObjectDish;


    @GetMapping("/allDishes")
    public String allDishsView(Map<String, Object> model) {
        model.put("dishes", dishRepository.findAll());
        return "dishes";
    }

    @GetMapping("/dishes")
    public List<Dish> getAllDish(){
        return dishRepository.findAll();
    }

    @PostMapping("/dish")
    public String createDish(@Valid @RequestBody String dish) throws JSONException {
        jsonObjectDish = new JSONObject(dish);
        this.dish = new Dish();

        this.dish.setId(parseInt(jsonObjectDish.getString("id")));
        this.dish.setName(jsonObjectDish.getString("name"));
        this.dish.setPicture(jsonObjectDish.getString("picture"));
        this.dish.setPrice(jsonObjectDish.getString("price"));
        this.dish.setDuration(jsonObjectDish.getString("duration"));
        this.dish.setType(jsonObjectDish.getString("type"));
        this.dish.setIngredients(jsonObjectDish.getString("ingredients"));

        this.dishRepository.save(this.dish);

        return "{'msg':'Dish added'}";
    }

    @GetMapping("/dish/{id}")
    public Dish getDishById(@PathVariable(value = "id") Long dishId) {
        return dishRepository.findById(dishId)
                .orElseThrow(() -> new ResourceNotFoundException("Dish", "id", dishId));
    }

    @PutMapping("/updatedish/{id}")
    public Dish updateDish(@PathVariable(value = "id") Long dishId,
                             @Valid @RequestBody Dish dishDetails) {

        Dish dishNote = dishRepository.findById(dishId)
                .orElseThrow(() -> new ResourceNotFoundException("Dish", "id", dishId));

        dishNote.setName(dishDetails.getName());
        dishNote.setDuration(dishDetails.getDuration());
        dishNote.setType(dishDetails.getType());
        dishNote.setPrice(dishDetails.getPrice());
        dishNote.setIngredients(dishDetails.getIngredients());
        dishNote.setPicture(dishDetails.getPicture());

        Dish updatedDish = dishRepository.save(dishNote);
        return updatedDish;
    }

    @DeleteMapping("/deletedish/{id}")
    public ResponseEntity<?> deleteDish(@PathVariable(value = "id") Long dishId) {
        Dish dish = this.dishRepository.findById(dishId)
                .orElseThrow(() -> new ResourceNotFoundException("Dish", "id", dishId));

        this.dishRepository.delete(dish);

        return ResponseEntity.ok().build();
    }
}
