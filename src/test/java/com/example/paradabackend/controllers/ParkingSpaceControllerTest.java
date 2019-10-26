package com.example.paradabackend.controllers;

import com.example.paradabackend.entities.ParkingLot;
import com.example.paradabackend.entities.ParkingSpace;
import com.example.paradabackend.services.ParkingLotService;
import com.example.paradabackend.services.ParkingSpaceService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
}
