package com.example.paradabackend.services;

import com.example.paradabackend.dtos.DriverCredentials;
import com.example.paradabackend.entities.Driver;
import com.example.paradabackend.repositories.DriverRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DriverService {
    @Autowired
    private DriverRepository driverRepository;

    public Driver findByUsernameAndPassword(DriverCredentials driverCredentials) {
        Driver foundDriver = driverRepository
                .findByUsernameAndPassword(driverCredentials.getUsername(), driverCredentials.getPassword());

        if (foundDriver == null ) {
            throw new IllegalArgumentException("Wrong username or password");
        }
        return foundDriver;
    }

    public Driver save(Driver driver) throws NotFoundException {
        Driver checkIfUsernameExisting = driverRepository.findByUsername(driver.getUsername());
        if (checkIfUsernameExisting != null) {
            throw new IllegalArgumentException("Username already exist!");
        }
        return driverRepository.save(driver);
    }

    public Driver findDriverProfile(String username) throws NotFoundException {
        Driver foundDriver = driverRepository.findByUsername(username);
        if (foundDriver == null ) {
            throw new NotFoundException("No driver profile.");
        }
        return foundDriver;
    }


    public Driver editDriverProfile(String username, Driver driver) throws NotFoundException {
        Driver foundDriver = driverRepository.findByUsername(username);
        if (foundDriver == null ) {
            throw new NotFoundException("No driver profile.");
        }

        foundDriver.setUsername(driver.getUsername());
        foundDriver.setPassword(driver.getPassword());
        foundDriver.setEmail(driver.getEmail());
        foundDriver.setFirstName(driver.getEmail());
        foundDriver.setLastName(driver.getLastName());
        foundDriver.setMobileNumber(driver.getMobileNumber());
        foundDriver.setProfilePicture(driver.getProfilePicture());

        return driverRepository.save(foundDriver);
    }
}
