package com.example.paradabackend.services;

import com.example.paradabackend.entities.ParkingLot;
import com.example.paradabackend.entities.ParkingSpace;
import com.example.paradabackend.repositories.ParkingLotRepository;
import com.example.paradabackend.repositories.ParkingSpaceRepository;
import javassist.NotFoundException;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
class ParkingSpaceServiceTest {

    @Autowired
    private ParkingSpaceService parkingSpaceService;

    @MockBean
    private ParkingSpaceRepository parkingSpaceRepository;

    @MockBean
    private ParkingLotRepository parkingLotRepository;


    private ParkingSpace dummyParkingSpace(String id, String parkingLotName) {
        ParkingSpace parkingSpace = new ParkingSpace();
        parkingSpace.setId(id);
        parkingSpace.setParkingLotName(parkingLotName);
        parkingSpace.setParkingLevel(1);
        parkingSpace.setParkingPosition("A1");
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
        ParkingSpace myParkingSpace = dummyParkingSpace("PLT-1A1", "1");
        ParkingLot myParkingLot = dummyParkingLot("ParkingLot Test");
        myParkingLot.setParkingSpaceList(Collections.singletonList(myParkingSpace));

        when(parkingSpaceRepository.save(myParkingSpace)).thenReturn(myParkingSpace);
        when(parkingLotRepository.save(any())).thenReturn(null);
        ParkingSpace parkingSpaceResult = parkingSpaceService.addNewParkingSpace(myParkingLot, myParkingSpace);

        assertEquals(parkingSpaceResult, myParkingSpace);
    }

    @Test
    void should_NOT_add_new_parking_space_when_added_new_detail() {
        assertThrows(NotFoundException.class, () ->
                parkingSpaceService.addNewParkingSpace(null, null));
    }

    @Test
    void should_find_all_parking_spaces_for_a_particular_parking_lot() {
        String parkingLotName = "ParkingLot Test";
        List<ParkingSpace> parkingSpaceList = Arrays.asList(
                new ParkingSpace(),
                new ParkingSpace(),
                new ParkingSpace()
        );
        when(parkingSpaceRepository.findAllByParkingLotName(parkingLotName)).thenReturn(parkingSpaceList);

        List<ParkingSpace> resultingParkingSpaceList = parkingSpaceService.findAllByParkingLotName(parkingLotName);

        assertThat(resultingParkingSpaceList, hasSize(parkingSpaceList.size()));
    }

    @Test
    public void should_update_startTime_when_Payment_is_confirmed () {

        String parkingSpaceID = "PA-1A1";
        ParkingSpace parkingSpace = new ParkingSpace();
        parkingSpace.setId(parkingSpaceID);
        parkingSpace.setStartTime(new Timestamp(System.currentTimeMillis()));

        ParkingSpace updatedParkingSpace = new ParkingSpace();
        updatedParkingSpace.setId("PA-1A1");
        when(parkingSpaceRepository.findById(eq("PA-1A1"))).thenReturn(Optional.of(parkingSpace));
        when(parkingSpaceRepository.save(any())).thenReturn(updatedParkingSpace);

        assertThat(updatedParkingSpace, is(parkingSpaceService.updateStartTimeWhenPaid(parkingSpaceID)));
    }
}