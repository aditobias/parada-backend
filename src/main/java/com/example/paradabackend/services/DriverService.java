package com.example.paradabackend.services;

import com.example.paradabackend.entities.Driver;
import com.example.paradabackend.repositories.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DriverService {
    @Autowired
    private DriverRepository driverRepository;

    public Driver findByUserNameAndPassword(String userName, String password) {
        Driver foundDriver = driverRepository.findByUserNameAndPassword(userName, password);
        if (foundDriver == null ) {
            throw new IllegalArgumentException("Wrong username or password");
        }
        return foundDriver;
    }
}
