package com.example.paradabackend.services;

import com.example.paradabackend.dtos.DriverCredentials;
import com.example.paradabackend.entities.Driver;
import static org.junit.jupiter.api.Assertions.assertThrows;
import com.example.paradabackend.repositories.DriverRepository;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@SpringBootTest
public class DriverServiceTest {
    @Autowired
    private DriverService driverService;

    @MockBean
    private DriverRepository driverRepository;

    @Test
    public void should_find_Driver_by_username_and_password() {
        Driver driver = new Driver("driver");
        driver.setPassword("password");
        driver.setFirstName("jed");

        when(driverRepository.findByUsernameAndPassword("driver", "password")).thenReturn(driver);

        Driver foundDriver = driverService.findByUsernameAndPassword(new DriverCredentials("driver", "password"));

        MatcherAssert.assertThat(driver, is(foundDriver));
    }

    @Test
    public void should_throw_Exception_if_invalid_login_credentials() {
        assertThrows(IllegalArgumentException.class, () ->
                driverService.findByUsernameAndPassword(new DriverCredentials("invalid", "invalid")));
    }

    @Test
    public void should_post_new_Driver() {
        Driver driver = new Driver("driver");
        driver.setPassword("password");
        driver.setFirstName("jed");

        when(driverRepository.save(driver)).thenReturn(driver);

        Driver foundDriver = driverService.save(driver);

        MatcherAssert.assertThat(driver, is(foundDriver));
    }
}