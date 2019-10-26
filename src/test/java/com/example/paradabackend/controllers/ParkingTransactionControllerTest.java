package com.example.paradabackend.controllers;

import com.example.paradabackend.entities.ParkingLot;
import com.example.paradabackend.entities.ParkingSpace;
import com.example.paradabackend.entities.ParkingTransaction;
import com.example.paradabackend.services.ParkingTransactionService;
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

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ParkingTransactionController.class)
@ActiveProfiles(profiles = "test")
public class ParkingTransactionControllerTest {

    @MockBean
    private ParkingTransactionService parkingTransactionService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void should_post_parking_transaction() throws Exception {
        ParkingTransaction parkingTransaction = new ParkingTransaction();
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setParkingLotName("parkingLot1");
        ParkingSpace parkingSpace = new ParkingSpace();
        parkingSpace.setId("PL1-1A1");

        parkingTransaction.setParkingLotName(parkingLot.getParkingLotName());

        when(parkingTransactionService.addParkingTransaction(eq("parkingLot1") , eq("PL1-1A1") , any())).
                thenReturn(parkingTransaction);

        ResultActions result = mvc.perform(post("/parkingLots/parkingLot1/transactions/parkingSpace/PL1-1A1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(parkingTransaction))
        );

        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.parkingLotName", is("parkingLot1")));
    }

    @Test
    void should_get_transaction_when_id_is_specified() throws Exception {
        ParkingTransaction parkingTransaction = new ParkingTransaction();
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setParkingLotName("parkingLot1");
        ParkingSpace parkingSpace = new ParkingSpace();
        parkingSpace.setId("PL1-1A1");
        when(parkingTransactionService.findTransactionById(1L)).thenReturn(parkingTransaction);

        ResultActions result = mvc.perform(get("/parkingLots/parkingLot1/transactions/{transacionId}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(parkingTransaction)));

        result.andExpect(status().isOk());
    }
}
