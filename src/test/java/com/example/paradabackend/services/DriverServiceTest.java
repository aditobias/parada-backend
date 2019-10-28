package com.example.paradabackend.services;

import com.example.paradabackend.dtos.DriverCredentials;
import com.example.paradabackend.entities.Driver;
import com.example.paradabackend.repositories.DriverRepository;
import javassist.NotFoundException;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    public void should_post_new_Driver() throws NotFoundException {
        Driver driver = new Driver("driver");
        driver.setPassword("password");
        driver.setFirstName("jed");

        when(driverService.save(driver)).thenReturn(driver);

        Driver foundDriver = driverService.save(driver);

        MatcherAssert.assertThat(driver, is(foundDriver));
    }

    @Test
    public void should_return_driver_profile() throws NotFoundException {
        Driver driver = new Driver("kg96");
        driver.setPassword("password");
        driver.setFirstName("Kenneth");
        driver.setLastName("Garcia");
        driver.setEmail("john.kenneth.garcia@oocl.com");
        driver.setMobileNumber("09123456789");
        driver.setEmailVerificationStatus("True");
        driver.setProfilePicture("www.google.com");
        when(driverRepository.findByUsername("kg96")).thenReturn(driver);

        Driver foundDriver = driverService.findDriverProfile("kg96");

        MatcherAssert.assertThat(driver, is(foundDriver));
    }

    @Test
    public void should_throw_Not_Found_Exception_if_username_is_null() {
        assertThrows(NotFoundException.class, () ->
                driverService.findDriverProfile(null));
    }
}