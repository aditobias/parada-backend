package com.example.paradabackend.repositories;

import com.example.paradabackend.entities.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRepository extends JpaRepository<Driver, String> {
    Driver findByUsernameAndPassword(String username, String password);

    Driver findByUsername(String username);

}
