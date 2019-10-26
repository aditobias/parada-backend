package com.example.paradabackend.controllers;


import com.example.paradabackend.entities.ParkingLot;
import com.example.paradabackend.entities.ParkingSpace;
import com.example.paradabackend.services.ParkingLotService;
import com.example.paradabackend.services.ParkingSpaceService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parkingLots/{parkingLotName}/parkingSpace")
public class ParkingSpaceController {

    @Autowired
    ParkingSpaceService parkingSpaceService;

    @Autowired
    ParkingLotService parkingLotService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.CREATED)
    public ParkingSpace addNewParkingSpaceToParkingLot(@PathVariable String parkingLotName,
                                                                       @RequestBody ParkingSpace parkingSpace) throws NotFoundException {
        ParkingLot parkingLot = parkingLotService.findSpecificParkingLot(parkingLotName);

        return parkingSpaceService.addNewParkingSpace(parkingLot, parkingSpace);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    public List<ParkingSpace> showAllParkingSpacesByParkingLotName(@PathVariable String parkingLotName) throws NotFoundException {
        return parkingSpaceService.findAllByParkingLotName(parkingLotName);
    }

    @PatchMapping(value="" ,
            consumes = {"application/json"})
    public ParkingSpace updateParkingSpace(@PathVariable String parkingLotName,
                                           @RequestBody ParkingSpace parkingSpace){

        ParkingSpace updateToOccupied = parkingSpaceService.
                updateToIsOccupiedWhenReserved(parkingSpace);
        return updateToOccupied;

    }


}
