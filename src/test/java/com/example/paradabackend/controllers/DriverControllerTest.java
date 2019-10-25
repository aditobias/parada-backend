package com.example.paradabackend.controllers;

import com.example.paradabackend.dtos.DriverCredentials;
import com.example.paradabackend.entities.Driver;
import com.example.paradabackend.services.DriverService;
import com.fasterxml.jackson.databind.ObjectMapper;
import javassist.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(DriverController.class)
@ActiveProfiles(profiles = "test")
public class DriverControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private DriverService driverService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void should_get_Driver_by_username_and_password() throws Exception {
        Driver driver = new Driver("driver");
        driver.setPassword("password");
        driver.setFirstName("Gray");

        when(driverService.findByUsernameAndPassword(new DriverCredentials("driver", "password")))
                .thenReturn(driver);

        ResultActions result = mvc.perform(post("/drivers/credentials")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new DriverCredentials("driver", "password")))
        );

        result.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.username", is("driver")))
                .andExpect(jsonPath("$.password", is("password")))
                .andExpect(jsonPath("$.firstName", is("Gray")));
    }

    @Test
    public void should_post_Driver() throws Exception {
        Driver driver = new Driver("driver");
        driver.setPassword("password");
        driver.setFirstName("Gray");

        when(driverService.save(driver)).thenReturn(driver);

        ResultActions result = mvc.perform(post("/drivers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(driver))
        );

        result.andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.username", is("driver")))
                .andExpect(jsonPath("$.password", is("password")))
                .andExpect(jsonPath("$.firstName", is("Gray")))
        ;
    }


    @Test
    public void should_get_a_particular_Driver_profile() throws Exception {
        Driver driver = new Driver("kg96");
        driver.setPassword("password");
        driver.setFirstName("Kenneth");
        driver.setLastName("Garcia");
        driver.setEmail("john.kenneth.garcia@oocl.com");
        driver.setMobileNumber("09123456789");
        driver.setEmailVerificationStatus("True");
        driver.setProfilePicture("www.google.com");


        when(driverService.findDriverProfile("kg96")).thenReturn(driver);

        ResultActions result = mvc.perform(get("/drivers/kg96"));

        result.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.username", is("kg96")))
                .andExpect(jsonPath("$.password", is("password")))
                .andExpect(jsonPath("$.firstName", is("Kenneth")))
        ;
    }

    @Test
    public void should_get_error_not_found_when_a_particular_Driver_profile_is_null() throws Exception {
        Driver driver = new Driver("kg96");
        driver.setPassword("password");
        driver.setFirstName("Kenneth");
        driver.setLastName("Garcia");
        driver.setEmail("john.kenneth.garcia@oocl.com");
        driver.setMobileNumber("09123456789");
        driver.setEmailVerificationStatus("True");
        driver.setProfilePicture("www.google.com");

        doThrow(NotFoundException.class).when(driverService).findDriverProfile("kg96");

        ResultActions result = mvc.perform(get("/drivers/kg96")
                    .contentType(APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(driver))
        );

        result.andExpect(status().isNotFound());
    }

}