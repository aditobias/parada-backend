package com.example.paradabackend.controllers;

import com.example.paradabackend.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.IOException;

@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;

    @RequestMapping(value = "/sendemail")
    public String sendEmail() {
        emailService.sendSimpleMessage("m.grayjeanne@gmail.com", "Test", "TestEmail");
        return "Email sent successfully";
    }

}

