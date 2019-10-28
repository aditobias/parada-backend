package com.example.paradabackend.controllers;

import com.example.paradabackend.entities.ParkingTransaction;
import com.example.paradabackend.services.ParkingTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/transaction")
public class AdminController {

    @Autowired
    ParkingTransactionService parkingTransactionService;

    @GetMapping(path = "/{transactionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    public ParkingTransaction showParkingTransactionById(@PathVariable Long transactionId) {
        return parkingTransactionService.findTransactionById(transactionId);
    }
}
