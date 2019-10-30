package com.example.paradabackend.services;

import com.example.paradabackend.entities.Driver;
import com.example.paradabackend.repositories.DriverRepository;
import javassist.NotFoundException;
import static org.mockito.ArgumentMatchers.any;
import org.apache.el.util.ReflectionUtil;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
public class EmailServiceTest {
    @MockBean
    private DriverRepository driverRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private DriverService driverService;

    @Test
    public void should_throw_exception_on_invalid_user() {
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            emailService.sendEmail(new Driver());
        });

        assertThat(exception.getMessage(), is("No driver profile."));
    }

    @Test
    public void should_return_generatedKey_and_Verified_false() throws NotFoundException {
        String username = "Gray";

        Driver driver = new Driver();
        driver.setUsername(username);
        driver.setVerified(false);
        driver.setEmail("m.grayjeanne@gmail.com");

        when(driverRepository.findByUsername(username)).thenReturn(driver);

        SimpleMailMessage mail = emailService.sendEmail(driver);

        assertThat(mail.getTo().length, is(1));
        assertThat(mail.getTo()[0], is("m.grayjeanne@gmail.com"));
        assertThat(mail.getSubject(), is("Welcome to Parada!"));
        assertThat(mail.getText(), containsString("To confirm your account, please click here : http://10.222.112.155:8080/sendEmail"));
    }

    @Test
    public void should_throw_exception_on_invalid_generation_key() {
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            emailService.updateToIsVerified("invalid");
        });

        assertThat(exception.getMessage(), is("No generated security key."));
    }

    @Test
    public void should_update_to_is_verified() throws NotFoundException {
        UUID uuid = UUID.randomUUID();
        String generatedSecurityKey = uuid.toString();

        Driver foundDriver = new Driver();
        foundDriver.setUsername("gray");
        foundDriver.setVerificationKey(generatedSecurityKey);

        when(driverRepository.findByVerificationKey(generatedSecurityKey)).thenReturn(foundDriver);

        Driver savedDriver = new Driver();
        savedDriver.setUsername("gray");
        savedDriver.setVerificationKey(generatedSecurityKey);
        savedDriver.setVerified(true);

        when(driverRepository.save(any(Driver.class))).thenReturn(savedDriver);

        assertThat(emailService.updateToIsVerified(generatedSecurityKey).getVerified(), is(true));
    }
}
