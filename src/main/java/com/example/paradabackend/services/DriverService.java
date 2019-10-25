package com.example.paradabackend.services;

import com.example.paradabackend.entities.Driver;
import com.example.paradabackend.repositories.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DriverService {
    @Autowired
    private DriverRepository driverRepository;

    public Driver findByUsernameAndPassword(String username, String password) {
        Driver foundDriver = driverRepository.findByUsernameAndPassword(username, password);
        if (foundDriver == null ) {
            throw new IllegalArgumentException("Wrong username or password");
        }
        return foundDriver;
    }

    public Driver save(Driver driver) {
        return driverRepository.save(driver);
    }
}
