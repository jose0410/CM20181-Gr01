package com.udea.edu.compmovil.RestService.repository;

import com.udea.edu.compmovil.RestService.model.Drink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DrinkRepository extends JpaRepository<Drink, Long> {
}
