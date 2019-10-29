package com.example.paradabackend.services;

import com.example.paradabackend.entities.Driver;
import com.example.paradabackend.repositories.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private DriverRepository driverRepository;

    public void sendEmail ( Driver driver) {
        SimpleMailMessage message = new SimpleMailMessage();
        UUID uuid = UUID.randomUUID();
        String generatedSecurityKey = uuid.toString();

        Driver foundDriver = new Driver();
        driverRepository.findByUsername(driver.getUsername());

        if(foundDriver != null) {
            foundDriver.setUsername(driver.getUsername());
            foundDriver.setVerificationKey(generatedSecurityKey);
            foundDriver.setVerified(false);
            driverRepository.save(foundDriver);

            message.setTo(driver.getEmail());
            message.setSubject("Welcome to Parada!");
            message.setText( "Please use this key for verification! "
                    + generatedSecurityKey);

            emailSender.send(message);
        }
    }
}