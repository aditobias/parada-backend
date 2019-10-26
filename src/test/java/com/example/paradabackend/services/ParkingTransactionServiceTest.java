package com.example.paradabackend.services;

import com.example.paradabackend.entities.ParkingTransaction;
import com.example.paradabackend.repositories.ParkingTransactionRepository;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ParkingTransactionServiceTest {

    @Autowired
    ParkingTransactionService parkingTransactionService;

    @MockBean
    private ParkingTransactionRepository parkingTransactionRepository;

    @Test
    public void should_add_parking_transaction_when_reservation_confirmed() {
        ParkingTransaction parkingTransaction = new ParkingTransaction("Gray","ParkingLot1","PL1-1A1");

        when(parkingTransactionRepository.save(parkingTransaction)).thenReturn(parkingTransaction);

        ParkingTransaction parkingTransactionAdded = parkingTransactionService.addParkingTransaction(parkingTransaction);

        MatcherAssert.assertThat(parkingTransaction, is(parkingTransactionAdded));
    }
}

