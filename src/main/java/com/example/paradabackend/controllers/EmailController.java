package com.example.paradabackend.controllers;

import com.example.paradabackend.entities.Driver;
import com.example.paradabackend.services.EmailService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/sendEmail")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PatchMapping(value = "/driver/{driverUserName}")
    @ResponseStatus(code = HttpStatus.OK)
    public void sendEmail(@PathVariable String driverUserName) throws NotFoundException {
        emailService.sendEmail(driverUserName);
    }

    @RequestMapping(value = "/{generatedKey}")
    @ResponseStatus(code = HttpStatus.OK)
    public String updateToIsVerified(@PathVariable String generatedKey) throws NotFoundException {
        emailService.updateToIsVerified(generatedKey);
        return "Email sent successfully";
    }

}

