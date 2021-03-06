package com.example.paradabackend.controllers;

import com.example.paradabackend.dtos.Receipt;
import com.example.paradabackend.entities.ParkingTransaction;
import com.example.paradabackend.services.ParkingTransactionService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/parkingLots/{parkingLotName}/transactions")
public class ParkingTransactionController {

    @Autowired
    ParkingTransactionService parkingTransactionService;

    @PostMapping(path = "/parkingSpace/{parkingSpaceId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.CREATED)
    public ParkingTransaction addNewParkingTransaction(@PathVariable String parkingLotName,
                                                       @PathVariable String parkingSpaceId,
                                                       @RequestBody ParkingTransaction parkingTransaction) throws NotFoundException {
        return parkingTransactionService.addParkingTransaction(parkingLotName, parkingSpaceId, parkingTransaction);

    }

    @GetMapping(path = "/{transactionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    public ParkingTransaction showParkingTransactionById(@PathVariable Long transactionId) {
        return parkingTransactionService.findTransactionById(transactionId);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    public Iterable<ParkingTransaction> showAllParkingTransactions(@RequestParam(defaultValue = "0", required = false) Integer page,
                                                                   @RequestParam(defaultValue = "10", required = false) Integer pageSize) {
        return parkingTransactionService.findAllTransactions(page, pageSize);
    }

    @GetMapping(path = "/{transactionId}/receipt", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    public Receipt generateReceiptGivenTransactionId(@PathVariable Long transactionId) {
        return parkingTransactionService.createReceiptFromTransactionId(transactionId);
    }

    @PatchMapping(path = "/{transactionId}/cancel", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    public ParkingTransaction updateStatusToCancelled(@PathVariable Long transactionId) throws NotFoundException {
        return parkingTransactionService.updateStatusToCancelledWhenCancel(transactionId);
    }


}
