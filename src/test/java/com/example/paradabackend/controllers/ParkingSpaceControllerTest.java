package com.example.paradabackend.controllers;

import com.example.paradabackend.entities.ParkingLot;
import com.example.paradabackend.entities.ParkingSpace;
import com.example.paradabackend.services.ParkingLotService;
import com.example.paradabackend.services.ParkingSpaceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ParkingSpaceController.class)
@ActiveProfiles(profiles = "test")
public class ParkingSpaceControllerTest {

    @MockBean
    private ParkingSpaceService parkingSpaceService;

    @MockBean
    ParkingLotService parkingLotService;

    @Autowired
    private MockMvc mvc;

    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    private ParkingLot parkingLot;

    private ParkingSpace dummyParkingSpace(String parkingLotName) {
        ParkingSpace parkingSpace = new ParkingSpace();
        parkingSpace.setParkingLotName(parkingLotName);
        parkingSpace.setParkingLevel(1);
        parkingSpace.setOccupied(false);

        return parkingSpace;
    }

    @Test
    void should_add_parking_space_when_new_input() throws Exception {
        ParkingSpace parkingSpace = dummyParkingSpace("ParkingLotTest");

        when(parkingLotService.findSpecificParkingLot("ParkingLostTest")).thenReturn(parkingLot);
        when(parkingSpaceService.addNewParkingSpace(any(), any())).thenReturn(parkingSpace);

        ResultActions resultOfExecution = mvc.perform(post("/parkingLots/{parkingLotName}/parkingSpace",
                "ParkingLostTest")
                .content(objectMapper.writeValueAsString(parkingSpace))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        resultOfExecution.andExpect(status().isCreated());
    }

    @Test
    void should_show_all_parking_spaces_when_in_specific_parking_lot() throws Exception {
        List<ParkingSpace> parkingSpace = Arrays.asList(
                dummyParkingSpace("ParkingLotTest1"),
                dummyParkingSpace("ParkingLotTest1")
        );

        when(parkingSpaceService.findAllByParkingLotName("ParkingLotTest")).thenReturn(parkingSpace);

        ResultActions resultOfExecution = mvc.perform(get("/parkingLots/{parkingLotName}/parkingSpace",
                "ParkingLostTest")
                .content(objectMapper.writeValueAsString(parkingSpace))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        resultOfExecution.andExpect(status().isOk());
    }

    @Test
    void should_update_specific_parking_lot_space_when_parking_lot_space_is_occupied() throws Exception {
        ParkingSpace parkingSpace = new ParkingSpace();
        parkingSpace.setOccupied(true);
        parkingSpace.setId("parkingSpaceID");

        ParkingLot parkingLot = new ParkingLot();

        ParkingSpace oldParkingSpace = new ParkingSpace();
        when(parkingSpaceService.updateToIsOccupiedWhenReserved(any()))
                .thenReturn(parkingSpace);

        ResultActions resultOfExecution = mvc.perform(patch("/parkingLots/parkingLot/parkingSpace")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(oldParkingSpace)));
        resultOfExecution.andExpect(status().isOk())
                .andExpect(jsonPath("$.occupied", is(true)));
    }

    @Test
    void should_update_start_time_of_parking_space_given_paid() throws Exception {
        ParkingSpace parkingSpace = new ParkingSpace();
        parkingSpace.setStartTime(new Timestamp(System.currentTimeMillis()));

        when(parkingSpaceService.updateStartTimeWhenPaid(any())).thenReturn(parkingSpace);

        ParkingSpace oldParkingSpace = new ParkingSpace();

        ResultActions resultOfExecution = mvc.perform(patch("/parkingLots/parkingLot/parkingSpace/{parkingSpaceId}/enter", "PL10-1A1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(oldParkingSpace)));

        resultOfExecution.andExpect(status().isOk())
                            .andExpect(jsonPath("$.startTime", is(parkingSpace.getStartTime().getTime())));

    }
}
