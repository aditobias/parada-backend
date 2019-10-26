package com.example.paradabackend.services;

import com.example.paradabackend.entities.Driver;
import com.example.paradabackend.entities.ParkingTransaction;
import com.example.paradabackend.repositories.ParkingTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParkingTransactionService {

    @Autowired
    ParkingTransactionRepository parkingTransactionRepository;

    public ParkingTransaction addParkingTransaction(ParkingTransaction parkingTransaction) {
        return parkingTransactionRepository.save(parkingTransaction);
    }
}
