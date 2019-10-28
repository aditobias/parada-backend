package com.example.paradabackend.services;

import com.example.paradabackend.dtos.DriverCredentials;
import com.example.paradabackend.entities.Driver;
import com.example.paradabackend.entities.ParkingTransaction;
import com.example.paradabackend.repositories.DriverRepository;
import com.example.paradabackend.repositories.ParkingTransactionRepository;
import javassist.NotFoundException;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class DriverServiceTest {

    @Autowired
    private DriverService driverService;

    @MockBean
    private DriverRepository driverRepository;

    @MockBean
    private ParkingTransactionRepository parkingTransactionRepository;

    @MockBean
    private ParkingTransactionService parkingTransactionService;

    @Test
    public void should_find_Driver_by_username_and_password() {
        Driver driver = new Driver("driver");
        driver.setPassword("password");
        driver.setFirstName("jed");

        when(driverRepository.findByUsernameAndPassword("driver", "password")).thenReturn(driver);

        Driver foundDriver = driverService.findByUsernameAndPassword(new DriverCredentials("driver", "password"));

        assertThat(driver, is(foundDriver));
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

        assertThat(driver, is(foundDriver));
    }

    @Test
    public void should_throw_exception_when_username_is_empty() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                driverService.save(new Driver()));
        assertThat(exception.getMessage(), is("Username cannot be empty"));
    }

    @Test
    public void should_throw_exception_when_password_is_empty() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                driverService.save(new Driver("with username")));
        assertThat(exception.getMessage(), is("Password cannot be empty"));
    }

    @Test
    public void should_throw_exception_when_driver_already_exists() {
        Driver myDriver = new Driver("zk");
        myDriver.setPassword("zk");
        Driver myDriver2 = new Driver("zk");
        myDriver2.setPassword("zk");

        when(driverRepository.findByUsername("zk")).thenReturn(myDriver);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                driverService.save(myDriver2));
        assertThat(exception.getMessage(), is("Username already exist!"));
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

        assertThat(driver, is(foundDriver));
    }

    @Test
    public void should_throw_Not_Found_Exception_if_username_is_null() {
        assertThrows(NotFoundException.class, () ->
                driverService.findDriverProfile(null));
    }

    @Test
    public void should_update_driver_profile_when_user_update_details() throws NotFoundException {
        Driver existingDriver = new Driver("kg96");
        existingDriver.setPassword("password");
        existingDriver.setFirstName("Kenneth");
        existingDriver.setLastName("Garcia");
        existingDriver.setEmail("john.kenneth.garcia@oocl.com");
        existingDriver.setMobileNumber("09123456789");
        existingDriver.setEmailVerificationStatus("True");
        existingDriver.setProfilePicture("www.google.com");

        when(driverRepository.findByUsername("kg96")).thenReturn(existingDriver);

        Driver updatedDriver = new Driver("kg96");
        updatedDriver.setPassword("password123");
        updatedDriver.setFirstName("Ken");
        updatedDriver.setLastName("Gar");
        updatedDriver.setEmail("john@oocl.com");
        updatedDriver.setMobileNumber("09123456780");
        updatedDriver.setEmailVerificationStatus("True");
        updatedDriver.setProfilePicture("www.googles.com");

        when(driverRepository.save(existingDriver)).thenReturn(updatedDriver);

        Driver expectedResult = driverService.editDriverProfile("kg96", existingDriver);

        assertThat(updatedDriver, is(expectedResult));
    }

    @Test
    void should_throw_error_in_update_profile_when_user_is_not_found() {
        assertThrows(NotFoundException.class, () ->
                driverService.editDriverProfile("zk", new Driver(null)));
    }
}