package com.example.paradabackend.services;

import com.example.paradabackend.entities.Driver;
import com.example.paradabackend.repositories.DriverRepository;
import javassist.NotFoundException;
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

    @Autowired
    private DriverService driverService;

    public SimpleMailMessage sendEmail(Driver driver) throws NotFoundException {
        UUID uuid = UUID.randomUUID();
        String generatedSecurityKey = uuid.toString();

        Driver foundDriver = driverService.findDriverProfile(driver.getUsername());
        foundDriver.setVerificationKey(generatedSecurityKey);
        foundDriver.setVerified(false);
        driverRepository.save(foundDriver);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(driver.getEmail());
        message.setSubject("Welcome to Parada!");
        message.setText("To confirm your account, please click here : "
                + "http://10.222.112.155:8080/sendEmail/" + generatedSecurityKey);
        emailSender.send(message);

        return message;
    }

    public Driver updateToIsVerified(String generatedKey) throws NotFoundException {
        Driver foundDriver = driverRepository.findByVerificationKey(generatedKey);
        if (foundDriver == null) {
            throw new NotFoundException("No generated security key.");
        }
        foundDriver.setVerified(true);
        return driverRepository.save(foundDriver);
    }
}