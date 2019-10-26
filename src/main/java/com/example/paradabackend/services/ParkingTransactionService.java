package com.example.paradabackend.services;

import com.example.paradabackend.entities.ParkingLot;
import com.example.paradabackend.entities.ParkingSpace;
import com.example.paradabackend.entities.ParkingTransaction;
import com.example.paradabackend.repositories.ParkingLotRepository;
import com.example.paradabackend.repositories.ParkingSpaceRepository;
import com.example.paradabackend.repositories.ParkingTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;

@Service
public class ParkingTransactionService {

    @Autowired
    ParkingTransactionRepository parkingTransactionRepository;

    @Autowired
    ParkingSpaceRepository parkingSpaceRepository;

    @Autowired
    ParkingLotRepository parkingLotRepository;

    public ParkingTransaction addParkingTransaction( String parkingLotName ,String parkingSpaceID , ParkingTransaction parkingTransaction ) {

        Optional<ParkingSpace> parkingSpaceFound = parkingSpaceRepository.findById(parkingSpaceID);

        if(parkingSpaceFound.isPresent()) {
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


    public ParkingTransaction findTransactionById(long id) {
        Optional<ParkingTransaction> parkingTransaction = parkingTransactionRepository.findById(id);
        if ( !parkingTransaction.isPresent()) {
            throw new IllegalArgumentException("No transaction found!");
        }
        return parkingTransaction.get();
    }
}
