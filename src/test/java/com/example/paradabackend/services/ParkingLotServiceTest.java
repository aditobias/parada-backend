package com.example.paradabackend.services;

import com.example.paradabackend.entities.ParkingLot;
import com.example.paradabackend.entities.ParkingSpace;
import com.example.paradabackend.repositories.ParkingLotRepository;
import javassist.NotFoundException;
import org.h2.index.Index;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ParkingLotServiceTest {

    private static final int MAX_PARKING_ROW = 26;
    private static final int POSITION = 9;

    @Autowired
    private ParkingLotService parkingLotService;

    @MockBean
    private ParkingLotRepository parkingLotRepository;

    @Test
    void should_show_all_parking_lot_when_in_home_page() {
        ParkingLot myParkingLot = dummyParkingLot("ParkingLot Test");

        List<ParkingLot> parkingLotList = Collections.singletonList(myParkingLot);
        Page<ParkingLot> parkingLotPage = new PageImpl<>(parkingLotList);

        when(parkingLotRepository.findAll(any(PageRequest.class)))
                .thenReturn(parkingLotPage);

        Iterable<ParkingLot> resultingParkingLotIterable = parkingLotService.findAllParkingLot(0, 2);

        assertThat(resultingParkingLotIterable, Matchers.contains(myParkingLot));
    }

    @Test
    void should_add_parking_lot_when_new_details() throws Exception {
        ParkingLot myParkingLot = dummyParkingLot("ParkingLot Test");

        when(parkingLotRepository.save(myParkingLot)).thenReturn(myParkingLot);

        ParkingLot parkingLotResult = parkingLotService.addParkingLot(myParkingLot);

        assertEquals(parkingLotResult, myParkingLot);
    }

    @Test
    void should_NOT_add_parking_lot_when_null_details() {
        assertThrows(IllegalArgumentException.class, () ->
                parkingLotService.addParkingLot(new ParkingLot()));
    }

    @Test
    void should_show_specific_parking_lot_when_input_parking_lot() throws NotFoundException {
        ParkingLot myParkingLot = dummyParkingLot("ParkingLot Test");

        when(parkingLotRepository.findByParkingLotName("ParkingLot Test")).thenReturn(myParkingLot);

        ParkingLot parkingLotResult = parkingLotService.findSpecificParkingLot("ParkingLot Test");

        assertEquals(parkingLotResult, myParkingLot);
    }

    @Test
    void should_throw_exception_when_parking_lot_already_exists() throws Exception {
        String parkingLotName = "ParkingLot Test";
        ParkingLot myParkingLot = dummyParkingLot(parkingLotName);
        ParkingLot myParkingLot2 = dummyParkingLot(parkingLotName);

        when(parkingLotRepository.findByParkingLotName(parkingLotName)).thenReturn(myParkingLot);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                parkingLotService.addParkingLot(myParkingLot2));

        assertThat(exception.getMessage(), is(parkingLotName + " already exists!"));
    }

    @Test
    void should_throw_exception_when_parking_space_is_greater_than_capacity() {
        ParkingLot myParkingLot = dummyParkingLot("ParkingLot Test");
        myParkingLot.setCapacity(10);
        myParkingLot.setMaxSpacePerLevel(20);

        when(parkingLotRepository.save(myParkingLot)).thenReturn(myParkingLot);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                parkingLotService.addParkingLot(myParkingLot));

        assertThat(exception.getMessage(), is("Max Space Per Level is greater than the Capacity!"));
    }

    @Test
    void should_NOT_show_specific_parking_lot_when_no_input() {
        assertThrows(NotFoundException.class, () ->
                parkingLotService.findSpecificParkingLot(null));
    }

    @Test
    void should_generate_parking_spaces_when_given_parking_lot_capacity() throws Exception {
        ParkingLot myParkingLot = dummyParkingLot("ParkingLot Test");
        myParkingLot.setCapacity(2);
        myParkingLot.setMaxSpacePerLevel(1);

        when(parkingLotRepository.save(myParkingLot)).thenReturn(myParkingLot);

        ParkingLot foundParkingLot = parkingLotService.addParkingLot(myParkingLot);
        ParkingLot foundParkingLot2 = parkingLotService.addParkingLot(myParkingLot);

        assertThat(Arrays.asList(foundParkingLot, foundParkingLot2), hasSize(myParkingLot.getCapacity()));
    }

    @Test
    void should_generate_parking_space_when_max_space_per_level_is_10_and_capacity_is_10() {
        ParkingLot myParkingLot = dummyParkingLot("ParkingLot Test");
        myParkingLot.setCapacity(5);
        myParkingLot.setAvailableSpaces(myParkingLot.getCapacity());
        myParkingLot.setMaxSpacePerLevel(5);

        when(parkingLotRepository.saveAll(any(List.class))).thenReturn(Collections.singletonList(myParkingLot));

        List<ParkingSpace> parkingSpaceList = parkingLotService.generateParkingSpace(myParkingLot);

        List<String> parkingPositionList = parkingSpaceList.stream()
                .map(ParkingSpace::getParkingPosition)
                .collect(Collectors.toList());

        assertThat(parkingPositionList, is(Arrays.asList(
                "A1", "A2", "A3", "A4", "A5")));
    }

    @Test
    void should_generate_parking_space_when_max_space_per_level_is_5_and_capacity_is_10() {
        ParkingLot myParkingLot = dummyParkingLot("ParkingLot Test");
        myParkingLot.setCapacity(10);
        myParkingLot.setAvailableSpaces(myParkingLot.getCapacity());
        myParkingLot.setMaxSpacePerLevel(5);

        when(parkingLotRepository.saveAll(any(List.class))).thenReturn(Collections.singletonList(myParkingLot));

        List<ParkingSpace> parkingSpaceList = parkingLotService.generateParkingSpace(myParkingLot);

        List<String> parkingPositionList = parkingSpaceList.stream()
                .map(ParkingSpace::getParkingPosition)
                .collect(Collectors.toList());

        assertThat(parkingPositionList, is(Arrays.asList(
                "A1", "A2", "A3", "A4", "A5",
                "B1", "B2", "B3", "B4", "B5")));
    }

    @Test
    void should_generate_parking_space_when_max_space_per_level_is_3_and_capacity_is_10() {
        ParkingLot myParkingLot = dummyParkingLot("ParkingLot Test");
        myParkingLot.setCapacity(10);
        myParkingLot.setAvailableSpaces(myParkingLot.getCapacity());
        myParkingLot.setMaxSpacePerLevel(3);

        when(parkingLotRepository.saveAll(any(List.class))).thenReturn(Collections.singletonList(myParkingLot));

        List<ParkingSpace> parkingSpaceList = parkingLotService.generateParkingSpace(myParkingLot);

        List<String> parkingPositionList = parkingSpaceList.stream()
                .map(ParkingSpace::getParkingPosition)
                .collect(Collectors.toList());

        assertThat(parkingPositionList, is(Arrays.asList(
                "A1", "A2", "A3",
                "B1", "B2", "B3",
                "C1", "C2", "C3",
                "D1")));
    }

    @Test
    void should_generate_parking_space() {
        String parkingLotName = "ParkingLot Test";

        ParkingLot myParkingLot = new ParkingLot();
        myParkingLot.setParkingLotName(parkingLotName);

        ParkingSpace actualParkingSpace = parkingLotService.generateParkingSpace(myParkingLot, MAX_PARKING_ROW, 9);

        ParkingSpace expectedParkingSpace = new ParkingSpace();
        expectedParkingSpace.setId("PLT-26Z9");
        expectedParkingSpace.setParkingLotName(parkingLotName);
        expectedParkingSpace.setParkingPosition("Z9");
        expectedParkingSpace.setParkingLevel(26);
        expectedParkingSpace.setOccupied(false);

        assertThat(actualParkingSpace, is(expectedParkingSpace));
    }

    @Test
    void should_throw_exception_generate_parking_space_when_max_row_is_greater_than_26() {
        String parkingLotName = "ParkingLot Test";

        ParkingLot myParkingLot = new ParkingLot();
        myParkingLot.setParkingLotName(parkingLotName);
        myParkingLot.setMaxSpacePerLevel(26);

        IndexOutOfBoundsException exception = assertThrows(IndexOutOfBoundsException.class, () ->
                parkingLotService.generateParkingSpace(myParkingLot, 27, 9));

        assertThat(exception.getMessage(), is("Parking Level input cannot be greater than 26."));
    }

    //TODO
//    @Test
//    void should_generate_total_parking_space() {
//        ParkingLot myParkingLot = dummyParkingLot("ParkingLot Test");
//        myParkingLot.setCapacity(1);
//        myParkingLot.setMaxSpacePerLevel(1);
//        List<ParkingSpace> parkingSpaceList = new ArrayList<>();
//
//        parkingLotService.generateTotalParkingSpace(myParkingLot, parkingSpaceList);
//
//        assertThat(parkingSpaceList, is(Arrays.asList(
//            "PLT-1A1", "ParkingLot Test", 1, false
//        )));
//    }

    private ParkingLot dummyParkingLot(String parkingLotName) {
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setParkingLotName(parkingLotName);
        parkingLot.setLocation("Manila");
        parkingLot.setCapacity(5);
        parkingLot.setMaxSpacePerLevel(5);
        parkingLot.setFlatRate(50);
        parkingLot.setRatePerHour(50);
        parkingLot.setSucceedingHourRate(15);

        return parkingLot;
    }
}