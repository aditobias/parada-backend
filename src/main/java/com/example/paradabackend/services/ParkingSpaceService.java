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

        buildParkingSpaceContent(parkingLot, parkingSpace);
        parkingSpaceRepository.save(parkingSpace);

        updateParkingLot(parkingLot, parkingSpace);

        return parkingSpace;
    }

    private void updateParkingLot(ParkingLot parkingLot, ParkingSpace parkingSpace) {
        parkingLot.getParkingSpaceList().add(parkingSpace);
        parkingLot.setCapacity(parkingLot.getCapacity() + 1);
        parkingLotRepository.save(parkingLot);
    }

    private void buildParkingSpaceContent(ParkingLot parkingLot, ParkingSpace parkingSpace) {
        parkingSpace.setParkingLotName(parkingLot.getParkingLotName());
        parkingSpace.setId(getGeneratedIdForParkingSpace(parkingLot, parkingSpace));
        parkingSpace.setOccupied(false);
    }

    private String getGeneratedIdForParkingSpace(ParkingLot parkingLot, ParkingSpace parkingSpace) {
        StringBuilder parkingLotNameBuilder = getParkingLotNameBuilder(parkingLot);

        return parkingLotNameBuilder.toString() + '-' +  parkingSpace.getParkingLevel() + parkingSpace.getParkingPosition();
    }

    private StringBuilder getParkingLotNameBuilder(ParkingLot parkingLot) {
        StringBuilder parkingLotNameBuilder = new StringBuilder();
        for(final char c : parkingLot.getParkingLotName().toCharArray())
            if(Character.isUpperCase(c) || Character.isDigit(c))
                parkingLotNameBuilder.append(c);

        return parkingLotNameBuilder;
    }
}
