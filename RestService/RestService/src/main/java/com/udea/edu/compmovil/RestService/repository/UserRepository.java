package com.udea.edu.compmovil.RestService.repository;

import com.udea.edu.compmovil.RestService.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM user WHERE user = ?1 ", nativeQuery = true)
    User findByUser(String user);
}
