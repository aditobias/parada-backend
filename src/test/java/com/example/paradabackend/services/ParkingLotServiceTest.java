package com.example.paradabackend.services;

import com.example.paradabackend.entities.ParkingLot;
import com.example.paradabackend.repositories.ParkingLotRepository;
import javassist.NotFoundException;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ParkingLotServiceTest {

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
        assertThrows(NotFoundException.class, () ->
                parkingLotService.addParkingLot(null));
    }

    @Test
    void should_show_specific_parking_lot_when_input_parking_lot() throws NotFoundException {
        ParkingLot myParkingLot = dummyParkingLot("ParkingLot Test");

        when(parkingLotRepository.findByParkingLotName("ParkingLot Test")).thenReturn(myParkingLot);

        ParkingLot parkingLotResult = parkingLotService.findSpecificParkingLot("ParkingLot Test");

        assertEquals(parkingLotResult, myParkingLot);
    }

    //  TODO: assertThrows test cases for similar names
//    @Test
//    void should_throw_exception_when_parking_lot_already_exists() throws Exception {
//        ParkingLot myParkingLot = dummyParkingLot("ParkingLot Test");
//
//        assertThrows(IllegalArgumentException.class, () ->
//                parkingLotService.addParkingLot(myParkingLot));
//    }

    @Test
    void should_NOT_show_specific_parking_lot_when_no_input() {
        assertThrows(NotFoundException.class, () ->
                parkingLotService.findSpecificParkingLot(null));
    }


    @Test
    void should_generate_parking_spaces_when_given_parking_lot_capacity() throws Exception {
        ParkingLot myParkingLot = dummyParkingLot("ParkingLot Test");

        when(parkingLotRepository.save(myParkingLot)).thenReturn(myParkingLot);

        ParkingLot foundParkingLot = parkingLotService.addParkingLot(myParkingLot);
        ParkingLot foundParkingLot2 = parkingLotService.addParkingLot(myParkingLot);

        assertThat(Arrays.asList(foundParkingLot, foundParkingLot2), hasSize(myParkingLot.getCapacity()));
    }

    private ParkingLot dummyParkingLot(String name) {
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setParkingLotName(name);
        parkingLot.setLocation("Manila");
        parkingLot.setCapacity(2);
        parkingLot.setMaxSpacePerLevel(1);
        parkingLot.setFlatRate(50);
        parkingLot.setRatePerHour(50);
        parkingLot.setSucceedingHourRate(15);

        return parkingLot;
    }
}