package com.example.paradabackend.controllers;

import com.example.paradabackend.entities.ParkingTransaction;
import com.example.paradabackend.services.ParkingTransactionService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    ParkingTransactionService parkingTransactionService;

    @GetMapping(path = "/transaction/{transactionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    public ParkingTransaction showParkingTransactionById(@PathVariable Long transactionId) {
        return parkingTransactionService.findTransactionById(transactionId);
    }

    @PatchMapping(path = "/transaction/{transactionId}/enter", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    public ParkingTransaction updateTransactionWhenEnter(@PathVariable Long transactionId) throws NotFoundException {
       return parkingTransactionService.updateSpecificTransactionEnter(transactionId);
    }

    @PatchMapping(path = "/transaction/{transactionId}/exit", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    public ParkingTransaction updateTransactionWhenExit(@PathVariable Long transactionId) throws NotFoundException {
        return parkingTransactionService.updateSpecificTransactionExit(transactionId);
    }
}
