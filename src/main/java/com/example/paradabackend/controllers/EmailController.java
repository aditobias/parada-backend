package com.example.paradabackend.controllers;

import com.example.paradabackend.entities.Driver;
import com.example.paradabackend.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/sendEmail")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PatchMapping(consumes = {"application/json"})
    @ResponseStatus(code = HttpStatus.OK)
    public String sendEmail(@RequestBody Driver driver) {
        emailService.sendEmail( driver);
        return "Email sent successfully";
    }

}

