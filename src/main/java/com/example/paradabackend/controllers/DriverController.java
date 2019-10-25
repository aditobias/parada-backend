package com.example.paradabackend.controllers;

import com.example.paradabackend.dtos.DriverCredentials;
import com.example.paradabackend.entities.Driver;
import com.example.paradabackend.services.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/drivers")
public class DriverController {
    @Autowired
    private DriverService driverService;

    @PostMapping(value = "/credentials", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    public Driver loginByUsernameAndPassword(@RequestBody DriverCredentials driverCredentials) {
        return driverService.findByUsernameAndPassword(driverCredentials);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.CREATED)
    public Driver postNewDriver(@RequestBody Driver driver) {
        return driverService.save(driver);
    }
}
