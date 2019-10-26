package com.example.paradabackend.services;

import com.example.paradabackend.entities.ParkingLot;
import com.example.paradabackend.entities.ParkingSpace;
import com.example.paradabackend.repositories.ParkingLotRepository;
import com.example.paradabackend.repositories.ParkingSpaceRepository;
import javassist.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class ParkingSpaceServiceTest {

    @Autowired
    ParkingSpaceService parkingSpaceService;

    @MockBean
    ParkingSpaceRepository parkingSpaceRepository;

    @MockBean
    ParkingLotRepository parkingLotRepository;

    private ParkingSpace dummyParkingSpace(String id, String parkingLotName) {
        ParkingSpace parkingSpace = new ParkingSpace();
        parkingSpace.setId(id);
        parkingSpace.setParkingLotName(parkingLotName);
        parkingSpace.setParkingLevel(1);
        parkingSpace.setOccupied(false);

        return parkingSpace;
    }

    private ParkingLot dummyParkingLot(String name) {
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setParkingLotName(name);
        parkingLot.setLocation("Manila");
        parkingLot.setCapacity(2);
        parkingLot.setFlatRate(50);
        parkingLot.setRatePerHour(50);
        parkingLot.setSucceedingHourRate(15);

        return parkingLot;
    }

    @Test
    void should_add_new_parking_space_when_added_new_detail() throws NotFoundException {
        ParkingSpace myParkingSpace = dummyParkingSpace("PLT-1A3", "1");
        ParkingLot myParkingLot = dummyParkingLot("ParkingLot Test");
        myParkingLot.setParkingSpaceList(Collections.singletonList(myParkingSpace));

        when(parkingSpaceRepository.save(myParkingSpace)).thenReturn(myParkingSpace);
        when(parkingLotRepository.save(any())).thenReturn(null);
        ParkingSpace parkingSpaceResult = parkingSpaceService.addNewParkingSpace(myParkingLot, myParkingSpace);

        assertEquals(parkingSpaceResult, myParkingSpace);
    }

    @Test
    void should_NOT_add_new_parking_space_when_added_new_detail() throws NotFoundException {
        assertThrows(NotFoundException.class, () ->
                parkingSpaceService.addNewParkingSpace(null, null));
    }
}