package com.example.paradabackend.services;

import com.example.paradabackend.dtos.DriverCredentials;
import com.example.paradabackend.entities.Driver;
import com.example.paradabackend.repositories.DriverRepository;
import com.example.paradabackend.repositories.ParkingTransactionRepository;
import javassist.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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
        Driver driver = createDriver();
        driver.setUsername("driver");
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
    public void should_post_new_Driver() {
        Driver driver = createDriver();

        when(driverService.save(driver)).thenReturn(driver);

        Driver foundDriver = driverService.save(driver);

        assertThat(driver, is(foundDriver));
    }

    @Test
    public void should_throw_exception_when_username_is_empty() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Driver driver = createDriver();
            driver.setUsername("");
            driverService.save(driver);
        });
        assertThat(exception.getMessage(), is("Username cannot be empty"));
    }

    @Test
    public void should_throw_exception_when_password_is_empty() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
        {
            Driver driver = createDriver();
            driver.setPassword("");
            driverService.save(driver);
        });
        assertThat(exception.getMessage(), is("Password cannot be empty"));
    }

    @Test
    public void should_throw_exception_when_FirstName_is_empty() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
        {
            Driver driver = createDriver();
            driver.setFirstName("");
            driverService.save(driver);
        });
        assertThat(exception.getMessage(), is("First name cannot be empty"));
    }

    @Test
    public void should_throw_exception_when_LastName_is_empty() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
        {
            Driver driver = createDriver();
            driver.setLastName("");
            driverService.save(driver);
        });
        assertThat(exception.getMessage(), is("Last name cannot be empty"));
    }

    @Test
    public void should_throw_exception_when_Email_is_empty() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
        {
            Driver driver = createDriver();
            driver.setEmail("");
            driverService.save(driver);
        });
        assertThat(exception.getMessage(), is("Email cannot be empty"));
    }

    @Test
    public void should_throw_exception_when_MobileNumber_is_empty() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
        {
            Driver driver = createDriver();
            driver.setMobileNumber("");
            driverService.save(driver);
        });
        assertThat(exception.getMessage(), is("Mobile number cannot be empty"));
    }

    @Test
    public void should_set_default_value_to_driverType_and_verified_when_empty() {
        Driver driverWithDefaults = createDriver();
        driverWithDefaults.setDriverType("user");
        driverWithDefaults.setVerified(false);
        when(driverRepository.save(any(Driver.class))).thenReturn(driverWithDefaults);

        Driver savedDriver = driverService.save(createDriver());
        assertThat(savedDriver.getDriverType(), is("user"));
        assertThat(savedDriver.getVerified(), is(false));
    }

    @Test
    public void should_not_update_driverType_when_has_existing_value() {
        String driverType = "admin";

        Driver driver = createDriver();
        driver.setDriverType(driverType);

        when(driverRepository.save(any(Driver.class))).thenReturn(driver);

        Driver savedDriver = driverService.save(createDriver());

        assertThat(savedDriver.getDriverType(), is(driverType));
    }

    @Test
    public void should_throw_exception_when_driver_already_exists() {
        Driver myDriver = createDriver();
        myDriver.setUsername("zk");
        Driver myDriver2 = createDriver();
        myDriver2.setUsername("zk");

        when(driverRepository.findByUsername("zk")).thenReturn(myDriver);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                driverService.save(myDriver2));
        assertThat(exception.getMessage(), is("Username already exist!"));
    }

    @Test
    public void should_return_driver_profile() throws NotFoundException {
        Driver driver = createDriver();

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
        Driver existingDriver = createDriver();

        when(driverRepository.findByUsername("kg96")).thenReturn(existingDriver);

        Driver updatedDriver = new Driver("kg96");
        updatedDriver.setPassword("password123");
        updatedDriver.setFirstName("Ken");
        updatedDriver.setLastName("Gar");
        updatedDriver.setEmail("john@oocl.com");
        updatedDriver.setMobileNumber("09123456780");
        updatedDriver.setProfilePicture("www.googles.com");

        when(driverRepository.save(existingDriver)).thenReturn(updatedDriver);

        Driver expectedResult = driverService.editDriverProfile("kg96", existingDriver);

        assertThat(updatedDriver, is(expectedResult));
    }

    @Test
    public void should_throw_error_in_update_profile_when_user_is_not_found() {
        assertThrows(NotFoundException.class, () ->
                driverService.editDriverProfile("zk", new Driver(null)));
    }

    public Driver createDriver() {
        Driver driver = new Driver("kg96");
        driver.setPassword("password");
        driver.setFirstName("Kenneth");
        driver.setLastName("Garcia");
        driver.setEmail("john.kenneth.garcia@oocl.com");
        driver.setMobileNumber("09123456789");
        driver.setProfilePicture("www.google.com");

        driver.setDriverType("user");

        return driver;
    }
}
