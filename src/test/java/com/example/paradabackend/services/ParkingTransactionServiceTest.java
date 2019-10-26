package com.example.paradabackend.services;

import com.example.paradabackend.entities.ParkingLot;
import com.example.paradabackend.entities.ParkingSpace;
import com.example.paradabackend.entities.ParkingTransaction;
import com.example.paradabackend.repositories.ParkingLotRepository;
import com.example.paradabackend.repositories.ParkingSpaceRepository;
import com.example.paradabackend.repositories.ParkingTransactionRepository;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
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
        when(parkingTransactionRepository.save(parkingTransaction)).thenReturn(parkingTransaction);

        ParkingTransaction parkingTransactionAdded =
                parkingTransactionService.addParkingTransaction("ParkingLot1" , parkingSpaceID, parkingTransaction);

        assertThat(parkingTransaction, is(parkingTransactionAdded));
    }

    @Test
    public void should_find_transaction_by_id () {
        ParkingTransaction parkingTransaction = new ParkingTransaction("Gray","ParkingLot1","1A1");
        parkingTransaction.setId(1L);

        when(parkingTransactionRepository.findById(1L)).thenReturn(Optional.of(parkingTransaction));

        ParkingTransaction foundTransaction = parkingTransactionService.findTransactionById(1L);

        MatcherAssert.assertThat(parkingTransaction, is(foundTransaction));

    }

    @Test
    public void should_return_all_parking_transactions () {
        List<ParkingTransaction> listOfTransactions = Arrays.asList(
                new ParkingTransaction("Gray","ParkingLot1","1A1"),
                new ParkingTransaction("Jeanne","ParkingLot2","1A2")
        );

        when(parkingTransactionRepository.findAll()).thenReturn(listOfTransactions);

        List<ParkingTransaction>  foundTransaction = parkingTransactionService.findAllTransactions(listOfTransactions);

        MatcherAssert.assertThat(listOfTransactions, is(foundTransaction));

    }
}

