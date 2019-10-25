package com.example.paradabackend.services;

import com.example.paradabackend.entities.Driver;
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
    public void should_find_Driver_by_userName_and_password() {
        Driver driver = new Driver("driver");
        driver.setPassword("password");
        driver.setFirstName("jed");
        when(driverRepository.findByUserNameAndPassword("driver", "password")).thenReturn(driver);

        Driver foundDriver = driverService.findByUserNameAndPassword("driver", "password");

        MatcherAssert.assertThat(driver, is(foundDriver));
    }
}