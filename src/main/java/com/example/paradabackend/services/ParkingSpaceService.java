package com.example.paradabackend.services;

import com.example.paradabackend.entities.ParkingLot;
import com.example.paradabackend.entities.ParkingSpace;
import com.example.paradabackend.repositories.ParkingLotRepository;
import com.example.paradabackend.repositories.ParkingSpaceRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Service
public class ParkingSpaceService {

    @Autowired
    ParkingSpaceRepository parkingSpaceRepository;

    @Autowired
    ParkingLotRepository parkingLotRepository;

    public ParkingSpace addNewParkingSpace(ParkingLot parkingLot, ParkingSpace parkingSpace) throws NotFoundException {

        if (isNull(parkingSpace) || isNull(parkingLot)) {
            throw new NotFoundException("Parking Lot not found!");
        }

        parkingSpaceRepository.save(parkingSpace);
        parkingLot.getParkingSpaceList().add(parkingSpace);
        parkingLot.setCapacity(parkingLot.getCapacity() - 1);
        parkingLotRepository.save(parkingLot);

        return parkingSpace;
    }
}
