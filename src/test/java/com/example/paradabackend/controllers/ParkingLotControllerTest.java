package com.example.paradabackend.controllers;

import com.example.paradabackend.entities.ParkingLot;
import com.example.paradabackend.services.ParkingLotService;
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
import org.springframework.util.LinkedMultiValueMap;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ParkingLotController.class)
@ActiveProfiles(profiles = "test")
public class ParkingLotControllerTest {
    @MockBean
    private ParkingLotService parkingLotService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    List<ParkingLot> parkingLotList = new ArrayList<>();

    private ParkingLot dummyParkingLot(String name) {
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setParkingLotName(name);
        parkingLot.setLocation("Manila");
        parkingLot.setRatePerHour(50);
        parkingLot.setSucceedingHourRate(15);
        parkingLot.setMaxSpacePerLevel(1);
        parkingLot.setCapacity(2);

        return parkingLot;
    }

    @Test
    void should_add_parking_lot_when_added_new_detail() throws Exception {
        ParkingLot parkingLot = dummyParkingLot("ParkingLot Test");

        when(parkingLotService.addParkingLot(parkingLot)).thenReturn(parkingLot);

        ResultActions resultOfExecution = mvc.perform(post("/parkingLots")
                .content(objectMapper.writeValueAsString(parkingLot))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        resultOfExecution.andExpect(status().isCreated());
    }

    @Test
    void should_show_all_parking_lot_when_in_home_page() throws Exception {
        ParkingLot myParkingLot = dummyParkingLot("ParkingLotTest");

        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("page", "0");
        requestParams.add("pageSize", "2");

        parkingLotList.add(myParkingLot);

        when(parkingLotService.findAllParkingLot(anyInt(), anyInt())).thenReturn(parkingLotList);

        ResultActions resultOfExecution = mvc.perform(get("/parkingLots")
                .params(requestParams));

        resultOfExecution.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].parkingLotName", is("ParkingLotTest")));
    }

    @Test
    public void should_show_specific_parking_lot() throws Exception {
        ParkingLot myParkingLot = dummyParkingLot("ParkingLot Test");

        when(parkingLotService.findSpecificParkingLot("ParkingLot Test"))
                .thenReturn(myParkingLot);

        ResultActions result = mvc.perform(get("/parkingLots/{parkingLotName}", "ParkingLot Test"));

        result.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.parkingLotName", is("ParkingLot Test")));
    }

}
