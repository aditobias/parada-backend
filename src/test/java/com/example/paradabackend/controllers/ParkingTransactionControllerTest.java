package com.example.paradabackend.controllers;

import com.example.paradabackend.dtos.Receipt;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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

        when(parkingTransactionService.addParkingTransaction(eq("parkingLot1"), eq("PL1-1A1"), any())).
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

    @Test
    void should_get_all_transactions() throws Exception {
        List<ParkingTransaction> listOfTransactions = Arrays.asList(
                new ParkingTransaction("Gray", "ParkingLot1", "1A1"),
                new ParkingTransaction("Jeanne", "ParkingLot2", "1A2")
        );

        PageImpl<ParkingTransaction> parkingTransactions = new PageImpl<>(listOfTransactions);
        when(parkingTransactionService.findAllTransactions(0, 5)).thenReturn(parkingTransactions);

        ResultActions result = mvc.perform(get("/parkingLots/parkingLot1/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(listOfTransactions)));

        result.andExpect(status().isOk())

        ;
    }

    @Test
    void should_get_transaction_and_return_receipt_when_id_is_specified() throws Exception {
        ParkingTransaction parkingTransaction = new ParkingTransaction("Gray", "ParkingLot1", "1A1");
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setParkingLotName("ParkingLot1");
        ParkingSpace parkingSpace = new ParkingSpace();
        parkingSpace.setId("PL1-1A1");

        Receipt receipt = new Receipt();
        receipt.setParkingTransaction(parkingTransaction);

        when(parkingTransactionService.createReceiptFromTransactionId(1L)).thenReturn(receipt);

        ResultActions result = mvc.perform(get("/parkingLots/parkingLot1/transactions/{transactionId}/receipt", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(receipt)));

        result.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.parkingTransaction.username", is("Gray")))
                .andExpect(jsonPath("$.parkingTransaction.parkingLotName", is("ParkingLot1")))
                .andExpect(jsonPath("$.parkingTransaction.parkingPosition", is("1A1")))
        ;
    }

    @Test
    void should_update_status_to_cancelled() throws Exception {

        ParkingTransaction parkingTransaction = new ParkingTransaction("Gray", "ParkingLot1", "1A1");
        parkingTransaction.setStatus("Cancelled");

        when(parkingTransactionService.updateStatusToCancelledWhenCancel(1L)).thenReturn(parkingTransaction);

        ResultActions result = mvc.perform(patch("/parkingLots/ParkingLot1/transactions/{transactionId}/cancel", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(parkingTransaction)));

        result.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.username", is("Gray")))
                .andExpect(jsonPath("$.parkingLotName", is("ParkingLot1")))
                .andExpect(jsonPath("$.parkingPosition", is("1A1")))
                .andExpect(jsonPath("$.status", is("Cancelled")));
    }
}
