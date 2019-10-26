package com.example.paradabackend.controllers;

import com.example.paradabackend.entities.ParkingLot;
import com.example.paradabackend.entities.ParkingSpace;
import com.example.paradabackend.entities.ParkingTransaction;
import com.example.paradabackend.services.ParkingTransactionService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/parkingLots/{parkingLotName}/parkingSpace/{parkingSpaceId}")
public class ParkingTransactionController {

    @Autowired
    ParkingTransactionService parkingTransactionService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.CREATED)
    public ParkingTransaction addNewParkingTransaction(@PathVariable String parkingLotName,
                                                       @PathVariable String parkingSpaceId,
                                                       @RequestBody ParkingTransaction parkingTransaction) throws NotFoundException {
        return parkingTransactionService.addParkingTransaction( parkingLotName , parkingSpaceId , parkingTransaction );

    }

}
