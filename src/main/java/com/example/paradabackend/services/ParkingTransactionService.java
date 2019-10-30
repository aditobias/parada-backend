package com.example.paradabackend.services;

import com.example.paradabackend.dtos.Receipt;
import com.example.paradabackend.entities.ParkingLot;
import com.example.paradabackend.entities.ParkingSpace;
import com.example.paradabackend.entities.ParkingTransaction;
import com.example.paradabackend.repositories.ParkingLotRepository;
import com.example.paradabackend.repositories.ParkingSpaceRepository;
import com.example.paradabackend.repositories.ParkingTransactionRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

@Service
public class ParkingTransactionService {

    @Autowired
    ParkingTransactionRepository parkingTransactionRepository;

    @Autowired
    ParkingSpaceRepository parkingSpaceRepository;

    @Autowired
    ParkingLotRepository parkingLotRepository;


    public ParkingTransaction addParkingTransaction(String parkingLotName, String parkingSpaceID, ParkingTransaction parkingTransaction) {
        Optional<ParkingSpace> parkingSpaceFound = parkingSpaceRepository.findById(parkingSpaceID);

        if (parkingSpaceFound.isPresent()) {
            parkingTransaction.setParkingLotName(parkingSpaceFound.get().getParkingLotName());
            parkingTransaction.setParkingLevel(parkingSpaceFound.get().getParkingLevel());
            parkingTransaction.setParkingPosition(parkingSpaceFound.get().getParkingPosition());
            ParkingLot parkingLot = parkingLotRepository.findByParkingLotName(parkingLotName);
            parkingTransaction.setPrice(parkingLot.getFlatRate());
            parkingTransaction.setReserveTime(new Timestamp(System.currentTimeMillis()));
            parkingTransaction.setStatus("Reserved");

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

    public Page<ParkingTransaction> findAllTransactions(Integer page, Integer pageSize) {
        return parkingTransactionRepository.findAll(PageRequest.of(page, pageSize));
    }

    public List<ParkingTransaction> findAllByUsername(String username) throws NotFoundException {
        List<ParkingTransaction> getAllDriversTransaction = parkingTransactionRepository.findAllByUsername(username);
        if (getAllDriversTransaction == null) {
            throw new NotFoundException("No transaction found!");
        }
        return getAllDriversTransaction;
    }

    public ParkingTransaction updateSpecificTransactionEnter(Long transactionId) throws NotFoundException {
        ParkingTransaction parkingTransaction = fetchParkingTransaction(transactionId);

        if(!isNull(parkingTransaction)){
            parkingTransaction.setStartTime(new Timestamp(System.currentTimeMillis()));
            parkingTransaction.setStatus("Paid");

            return parkingTransactionRepository.save(parkingTransaction);
        }
        throw new NotFoundException("No transaction found!");
    }

    public ParkingTransaction fetchParkingTransaction(Long transactionId) throws NotFoundException {
        Optional<ParkingTransaction> parkingTransaction = parkingTransactionRepository.findById(transactionId);
        if(parkingTransaction.isPresent()){
            return parkingTransaction.get();
        }

        throw new NotFoundException("No transaction found!");
    }

    public ParkingTransaction updateSpecificTransactionExit(Long transactionId) throws NotFoundException {
        ParkingTransaction parkingTransaction = fetchParkingTransaction(transactionId);
        if(!isNull(parkingTransaction)){
            ParkingSpace parkingSpace = parkingSpaceRepository
                    .findByParkingLotNameAndParkingPosition(parkingTransaction.getParkingLotName()
                            , parkingTransaction.getParkingPosition());
            parkingSpace.setOccupied(false);

            ParkingLot parkingLot = parkingLotRepository.findByParkingLotName(parkingSpace.getParkingLotName());
            Integer newCapacity = parkingLot.getCapacity() + 1;
            parkingLot.setCapacity(newCapacity);
            parkingTransaction.setEndTime(new Timestamp(System.currentTimeMillis()));
            parkingTransaction.setStatus("Closed");

            parkingLotRepository.save(parkingLot);
            parkingSpaceRepository.save(parkingSpace);
            parkingTransactionRepository.save(parkingTransaction);

        }
    return parkingTransaction;
    }

    public ParkingTransaction updateStatusToCancelledWhenCancel(Long transactionId) throws NotFoundException {
        ParkingTransaction parkingTransaction = fetchParkingTransaction(transactionId);
        ParkingSpace parkingSpace = parkingSpaceRepository
                .findByParkingLotNameAndParkingPosition(parkingTransaction.getParkingLotName()
                        , parkingTransaction.getParkingPosition());
        parkingSpace.setOccupied(false);

        ParkingLot parkingLot = parkingLotRepository.findByParkingLotName(parkingSpace.getParkingLotName());
        Integer newAvailableSpaces = parkingLot.getAvailableSpaces() + 1;
        parkingLot.setAvailableSpaces(newAvailableSpaces);
        parkingTransaction.setEndTime(new Timestamp(System.currentTimeMillis()));
        parkingTransaction.setStatus("Cancelled");

        parkingLotRepository.save(parkingLot);
        parkingSpaceRepository.save(parkingSpace);

        return parkingTransactionRepository.save(parkingTransaction);

    }
}
