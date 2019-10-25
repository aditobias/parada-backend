package com.example.paradabackend.controllers;


import com.example.paradabackend.entities.ParkingLot;
import com.example.paradabackend.entities.ParkingSpace;
import com.example.paradabackend.services.ParkingLotService;
import com.example.paradabackend.services.ParkingSpaceService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/parkingLots/{parkingLotName}/parkingSpace")
public class ParkingSpaceController {

    @Autowired
    ParkingSpaceService parkingSpaceService;

    @Autowired
    ParkingLotService parkingLotService;

    @PostMapping(consumes = {"application/json"})
    @ResponseStatus(code = HttpStatus.CREATED)
    public ParkingSpace addNewParkingSpaceToParkingLot(@PathVariable String parkingLotName,
                                                                       @RequestBody ParkingSpace parkingSpace) throws NotFoundException {
        ParkingLot parkingLot = parkingLotService.findSpecificParkingLot(parkingLotName);

        return parkingSpaceService.addNewParkingSpace(parkingLot, parkingSpace);
    }
}
