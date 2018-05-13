package com.udea.edu.compmovil.RestService.repository;

import com.udea.edu.compmovil.RestService.model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DishRepository extends JpaRepository<Dish, Long> {
}
