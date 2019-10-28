package com.example.paradabackend.services;

import com.example.paradabackend.dtos.Receipt;
import com.example.paradabackend.entities.ParkingLot;
import com.example.paradabackend.entities.ParkingSpace;
import com.example.paradabackend.entities.ParkingTransaction;
import com.example.paradabackend.repositories.ParkingLotRepository;
import com.example.paradabackend.repositories.ParkingSpaceRepository;
import com.example.paradabackend.repositories.ParkingTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class ParkingTransactionService {

    @Autowired
    ParkingTransactionRepository parkingTransactionRepository;

    @Autowired
    ParkingSpaceRepository parkingSpaceRepository;

    @Autowired
    ParkingLotRepository parkingLotRepository;


    public ParkingTransaction addParkingTransaction(String parkingLotName, String parkingSpaceId) {

        Optional<ParkingSpace> parkingSpaceFound = parkingSpaceRepository.findById(parkingSpaceId);

        if (parkingSpaceFound.isPresent()) {
            ParkingTransaction parkingTransaction = new ParkingTransaction();
            parkingTransaction.setParkingLotName(parkingSpaceFound.get().getParkingLotName());
            parkingTransaction.setParkingLevel(parkingSpaceFound.get().getParkingLevel());
            parkingTransaction.setParkingPosition(parkingSpaceFound.get().getParkingPosition());
            parkingTransaction.setOccupied(parkingSpaceFound.get().isOccupied());

            ParkingLot parkingLot = parkingLotRepository.findByParkingLotName(parkingLotName);
            parkingTransaction.setPrice(parkingLot.getFlatRate());
            parkingTransaction.setVoided("NotVoided");
            parkingTransaction.setCreationDateTime(new Timestamp(System.currentTimeMillis()));

            return parkingTransactionRepository.save(parkingTransaction);
        }
        return null;
    }


    public Receipt createReceiptFromTransactionId(long id) {
        ParkingTransaction transactionById = findTransactionById(id);
        Receipt receipt = new Receipt();
        receipt.setParkingTransaction(transactionById);
        return receipt;
    }

    public ParkingTransaction findTransactionById(long id) {
        Optional<ParkingTransaction> parkingTransaction = parkingTransactionRepository.findById(id);
        if (!parkingTransaction.isPresent()) {
            throw new IllegalArgumentException("No transaction found!");
        }
        return parkingTransaction.get();
    }

    public Page<ParkingTransaction> findAllTransactions(Integer page , Integer pageSize) {
        return parkingTransactionRepository.findAll(PageRequest.of(page , pageSize));
    }
}
