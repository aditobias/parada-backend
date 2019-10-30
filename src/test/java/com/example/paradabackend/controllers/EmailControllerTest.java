package com.example.paradabackend.controllers;

import com.example.paradabackend.entities.Driver;
import com.example.paradabackend.services.DriverService;
import com.example.paradabackend.services.EmailService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(EmailController.class)
@ActiveProfiles(profiles = "test")
public class EmailControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EmailService emailService;

    @MockBean
    private DriverService driverService;

    @Test
    public void should_send_email() throws Exception {
        String driverName = "grayjeanne";
        Driver driver = new Driver(driverName);

        when(driverService.findDriverProfile(driverName)).thenReturn(driver);

        SimpleMailMessage message = new SimpleMailMessage();
        when(emailService.sendEmail(any())).thenReturn(message);

        ResultActions result = mvc.perform(patch("/sendEmail")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(driver)));

        result.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$", is("Email sent successfully")))
        ;
    }

    @Test
    public void should_update_to_id_verified() throws Exception {
        String driverName = "grayjeanne";
        Driver driver = new Driver(driverName);

        SimpleMailMessage message = new SimpleMailMessage();
        when(emailService.updateToIsVerified(any())).thenReturn(driver);

        UUID uuid = UUID.randomUUID();
        String generatedSecurityKey = uuid.toString();

        ResultActions result = mvc.perform(get("/sendEmail/{generatedKey}", generatedSecurityKey)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(driver)));

        result.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$", is("Email sent successfully")))
        ;
    }



}
