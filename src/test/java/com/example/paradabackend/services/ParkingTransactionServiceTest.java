package com.example.paradabackend.services;

import com.example.paradabackend.dtos.Receipt;
import com.example.paradabackend.entities.ParkingLot;
import com.example.paradabackend.entities.ParkingSpace;
import com.example.paradabackend.entities.ParkingTransaction;
import com.example.paradabackend.repositories.ParkingLotRepository;
import com.example.paradabackend.repositories.ParkingSpaceRepository;
import com.example.paradabackend.repositories.ParkingTransactionRepository;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ParkingTransactionServiceTest {

    @Autowired
    ParkingTransactionService parkingTransactionService;

    @MockBean
    private ParkingTransactionRepository parkingTransactionRepository;

    @MockBean
    private ParkingSpaceRepository parkingSpaceRepository;

    @MockBean
    private ParkingLotRepository parkingLotRepository;

    @Test
    public void should_add_parking_transaction_when_reservation_confirmed() {
        ParkingTransaction parkingTransaction = new ParkingTransaction("Gray","ParkingLot1","1A1");
        String parkingSpaceID = "PA-1A1";

        ParkingSpace parkingSpace = new ParkingSpace();
        parkingSpace.setId(parkingSpaceID);
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setParkingLotName("ParkingLot1");

        when(parkingSpaceRepository.findById(parkingSpaceID)).thenReturn(Optional.of(parkingSpace));
        when(parkingLotRepository.findByParkingLotName(parkingLot.getParkingLotName())).thenReturn(parkingLot);
        when(parkingTransactionRepository.save(eq(parkingTransaction))).thenReturn(parkingTransaction);
        ParkingTransaction parkingTransactionAdded =
                parkingTransactionService.addParkingTransaction("ParkingLot1" , parkingSpaceID, parkingTransaction);
        ParkingSpace newParkingSpace = new ParkingSpace();
        newParkingSpace.setId(parkingSpaceID);
        ParkingLot newParkingLot = new ParkingLot();
        newParkingLot.setParkingLotName("ParkingLot1");
        parkingTransactionAdded.setUsername("Gray");
        parkingTransactionAdded.setParkingPosition("1A1");

        assertThat(parkingTransactionAdded, is(parkingTransaction));
    }

    @Test
    public void should_find_transaction_by_id () {
        ParkingTransaction parkingTransaction = new ParkingTransaction("Gray","ParkingLot1","1A1");
        parkingTransaction.setId(1L);

        when(parkingTransactionRepository.findById(1L)).thenReturn(Optional.of(parkingTransaction));

        ParkingTransaction foundTransaction = parkingTransactionService.findTransactionById(1L);

        assertThat(parkingTransaction, is(foundTransaction));
    }

    @Test
    public void should_return_all_parking_transactions () {
        List<ParkingTransaction> listOfTransactions = Arrays.asList(
                new ParkingTransaction("Gray","ParkingLot1","1A1"),
                new ParkingTransaction("Jeanne","ParkingLot2","1A2")
        );

        PageImpl<ParkingTransaction> parkingTransactions = new PageImpl<>(listOfTransactions);
        when(parkingTransactionRepository.findAll(any(PageRequest.class))).thenReturn(parkingTransactions);

        Page<ParkingTransaction> transactionPage = parkingTransactionService.findAllTransactions(0,5);

        assertThat(transactionPage.getContent(), is(listOfTransactions));
    }

    @Test
    public void should_create_receipt_given_transaction_id() {
        ParkingTransaction parkingTransaction = new ParkingTransaction("Gray","ParkingLot1","1A1");

        when(parkingTransactionRepository.findById(1L)).thenReturn(Optional.of(parkingTransaction));

        Receipt receipt =
                parkingTransactionService.createReceiptFromTransactionId(1L);

        assertThat(receipt.getParkingTransaction(), is(parkingTransaction));
    }

    @Test
    public void should_throw_IllegalArgumentException_when_create_receipt_given_invalid_transaction_id() {
        when(parkingTransactionRepository.findById(1L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            parkingTransactionService.createReceiptFromTransactionId(1L);
        });

        assertThat(exception.getMessage(), is("No transaction found!"));
    }
}

