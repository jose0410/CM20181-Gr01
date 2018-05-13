package com.udea.edu.compmovil.RestService.controller;

import com.udea.edu.compmovil.RestService.exception.ResourceNotFoundException;
import com.udea.edu.compmovil.RestService.model.Dish;
import com.udea.edu.compmovil.RestService.repository.DishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class DishController {
    @Autowired
    DishRepository dishRepository;


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
    public Dish createDish(@Valid @RequestBody Dish dish) {
        return this.dishRepository.save(dish);
    }

    @GetMapping("/dish/{id}")
    public Dish getDishById(@PathVariable(value = "id") Long dishId) {
        return dishRepository.findById(dishId)
                .orElseThrow(() -> new ResourceNotFoundException("Dish", "id", dishId));
    }

    @PutMapping("/dish/{id}")
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

    @DeleteMapping("/dish/{id}")
    public ResponseEntity<?> deleteDish(@PathVariable(value = "id") Long dishId) {
        Dish dish = this.dishRepository.findById(dishId)
                .orElseThrow(() -> new ResourceNotFoundException("Dish", "id", dishId));

        this.dishRepository.delete(dish);

        return ResponseEntity.ok().build();
    }
}
