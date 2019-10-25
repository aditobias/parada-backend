package com.example.paradabackend.controllers;

import com.example.paradabackend.entities.Driver;
import com.example.paradabackend.services.DriverService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
        when(driverService.findByUsernameAndPassword("driver", "password")).thenReturn(driver);

        ResultActions result = mvc.perform(get("/drivers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(driver))
        );

        result.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.username", is("driver")))
                .andExpect(jsonPath("$.password", is("password")));
    }
}