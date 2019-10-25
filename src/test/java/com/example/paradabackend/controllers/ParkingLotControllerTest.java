package com.example.paradabackend.controllers;

import com.example.paradabackend.entities.ParkingLot;
import com.example.paradabackend.services.ParkingLotService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javassist.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class ParkingLotControllerTest {
    @MockBean
    private ParkingLotService parkingLotService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    private Iterable<ParkingLot> parkingLots = new ArrayList<>();

    private ParkingLot dummyParkingLot(String name) {
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setParkingLotName(name);
        parkingLot.setLocation("Manila");
        parkingLot.setRatePerHour(50);
        parkingLot.setSucceedingHoursRate(15);
        parkingLot.setCapacity(2);

        return parkingLot;
    }

    @Test
    void should_add_parking_lot_when_added_new_detail() throws Exception {
        ParkingLot parkingLot = dummyParkingLot("ParkingLot Test");

        when(parkingLotService.addParkingLot(parkingLot)).thenReturn(parkingLot);

        ResultActions resultOfExecution = mockMvc.perform(post("/parkingLots")
                .content(objectMapper.writeValueAsString(parkingLot))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        resultOfExecution.andExpect(status().isCreated());
    }
}
