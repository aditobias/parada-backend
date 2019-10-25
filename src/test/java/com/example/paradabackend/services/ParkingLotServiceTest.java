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

import java.util.Collections;
import java.util.List;

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
    void should_add_parking_lot_when_new_details() throws NotFoundException {
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

    private ParkingLot dummyParkingLot(String name) {
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setParkingLotName(name);
        parkingLot.setLocation("Manila");
        parkingLot.setCapacity(2);
        parkingLot.setFlatRate(50);
        parkingLot.setRatePerHour(50);
        parkingLot.setSucceedingHoursRate(15);

        return parkingLot;
    }
}