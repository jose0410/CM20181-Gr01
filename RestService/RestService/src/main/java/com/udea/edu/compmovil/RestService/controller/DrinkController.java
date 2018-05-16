package com.udea.edu.compmovil.RestService.controller;

import com.udea.edu.compmovil.RestService.exception.ResourceNotFoundException;
import com.udea.edu.compmovil.RestService.model.Drink;
import com.udea.edu.compmovil.RestService.model.User;
import com.udea.edu.compmovil.RestService.repository.DrinkRepository;
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
public class DrinkController {

    @Autowired
    DrinkRepository drinkRepository;

    private JSONObject jsonObjectDrink;
    private Drink drink;

    @GetMapping("/allDrinks")
    public String allDrinksView(Map<String, Object> model) {
        model.put("drinks", drinkRepository.findAll());
        return "drinks";
    }

    @GetMapping("/drinks")
    public List<Drink> getAllDrink(){
        return drinkRepository.findAll();
    }

    @PostMapping("/drink")
    public String createDrink(@Valid @RequestBody String drink) throws JSONException {
        jsonObjectDrink = new JSONObject(drink);
        this.drink = new Drink();

        this.drink.setId(parseInt(jsonObjectDrink.getString("id")));
        this.drink.setName(jsonObjectDrink.getString("name"));
        this.drink.setPicture(jsonObjectDrink.getString("picture"));
        this.drink.setPrice(jsonObjectDrink.getString("price"));
        this.drink.setIngredients(jsonObjectDrink.getString("ingredients"));

        this.drinkRepository.save(this.drink);

        return "{'msg':'Drink added'}";
    }

    @GetMapping("/drink/{id}")
    public Drink getDrinkById(@PathVariable(value = "id") Long drinkId) {
        return drinkRepository.findById(drinkId)
                .orElseThrow(() -> new ResourceNotFoundException("Drink", "id", drinkId));
    }

    @PutMapping("/updatedrink/{id}")
    public Drink updateDrink(@PathVariable(value = "id") Long drinkId,
                           @Valid @RequestBody Drink drinkDetails) {

        Drink drinkNote = drinkRepository.findById(drinkId)
                .orElseThrow(() -> new ResourceNotFoundException("Drink", "id", drinkId));

        drinkNote.setName(drinkDetails.getName());
        drinkNote.setPrice(drinkDetails.getPrice());
        drinkNote.setIngredients(drinkDetails.getIngredients());
        drinkNote.setPicture(drinkDetails.getPicture());

        Drink updatedDrink = drinkRepository.save(drinkNote);
        return updatedDrink;
    }

    @DeleteMapping("/deletedrink/{id}")
    public ResponseEntity<?> deleteDrink(@PathVariable(value = "id") Long drinkId) {
        Drink drink = this.drinkRepository.findById(drinkId)
                .orElseThrow(() -> new ResourceNotFoundException("Drink", "id", drinkId));

        this.drinkRepository.delete(drink);

        return ResponseEntity.ok().build();
    }
}
