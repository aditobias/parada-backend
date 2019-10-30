package com.example.paradabackend.services;

import com.example.paradabackend.dtos.DriverCredentials;
import com.example.paradabackend.entities.Driver;
import com.example.paradabackend.repositories.DriverRepository;
import com.example.paradabackend.repositories.ParkingTransactionRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DriverService {
    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private ParkingTransactionRepository parkingTransactionRepository;

    public Driver findByUsernameAndPassword(DriverCredentials driverCredentials) {
        Driver foundDriver = driverRepository
                .findByUsernameAndPassword(driverCredentials.getUsername(), driverCredentials.getPassword());

        if (foundDriver == null) {
            throw new IllegalArgumentException("Wrong username or password");
        }
        return foundDriver;
    }

    public Driver save(Driver driver) {
        if (isNullOfEmpty(driver.getDriverType())) {
            driver.setDriverType("user");
        }

        driver.setVerified(false);

        requireNotNullOrEmpty(driver.getUsername(), "Username cannot be empty");
        requireNotNullOrEmpty(driver.getPassword(), "Password cannot be empty");
        requireNotNullOrEmpty(driver.getFirstName(), "First name cannot be empty");
        requireNotNullOrEmpty(driver.getLastName(), "Last name cannot be empty");
        requireNotNullOrEmpty(driver.getEmail(), "Email cannot be empty");
        requireNotNullOrEmpty(driver.getMobileNumber(), "Mobile number cannot be empty");
//        requireNotNullOrEmpty(driver.getProfilePicture(), "Profile picture cannot be empty");

        Driver existingDriver = driverRepository.findByUsername(driver.getUsername());
        if (existingDriver != null) {
            throw new IllegalArgumentException("Username already exist!");
        }
        return driverRepository.save(driver);
    }

    private void requireNotNullOrEmpty(String field, String errorMessage) {
        if (isNullOfEmpty(field)) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    private boolean isNullOfEmpty(String field) {
        return field == null || field.isEmpty();
    }

    public Driver findDriverProfile(String username) throws NotFoundException {
        Driver foundDriver = driverRepository.findByUsername(username);
        if (foundDriver == null) {
            throw new NotFoundException("No driver profile.");
        }
        return foundDriver;
    }


    public Driver editDriverProfile(String username, Driver driver) throws NotFoundException {
        Driver existingDriver = driverRepository.findByUsername(username);
        if (existingDriver == null) {
            throw new NotFoundException("No driver profile.");
        }

        existingDriver.setEmail(driver.getEmail());
        existingDriver.setFirstName(driver.getFirstName());
        existingDriver.setLastName(driver.getLastName());
        existingDriver.setMobileNumber(driver.getMobileNumber());
        existingDriver.setProfilePicture(driver.getProfilePicture());

        return driverRepository.save(existingDriver);
    }

    public Driver updateDriverAccessToAdmin(String username) throws NotFoundException {
        Driver existingDriver = driverRepository.findByUsername(username);

        if(existingDriver != null){
            existingDriver.setDriverType("admin");

            return driverRepository.save(existingDriver);
        }
        throw new NotFoundException("No driver profile");
    }

    public Driver updateDriverAccessToUser(String username) throws NotFoundException {
        Driver existingDriver = driverRepository.findByUsername(username);

        if(existingDriver != null){
            existingDriver.setDriverType("user");

            return driverRepository.save(existingDriver);
        }
        throw new NotFoundException("No driver profile");
    }
}
