package com.example.paradabackend.services;

import com.example.paradabackend.entities.ParkingLot;
import com.example.paradabackend.entities.ParkingSpace;
import com.example.paradabackend.entities.ParkingTransaction;
import com.example.paradabackend.repositories.ParkingLotRepository;
import com.example.paradabackend.repositories.ParkingSpaceRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

@Service
public class ParkingSpaceService {

    @Autowired
    private ParkingSpaceRepository parkingSpaceRepository;

    @Autowired
    private ParkingLotRepository parkingLotRepository;

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
        List<ParkingSpace> updatedParkingSpaceList = updateParkingSpaceList(parkingLot, parkingSpace);

        Long occupiedParkingSpaces = parkingLot.getParkingSpaceList().stream()
                .filter(ParkingSpace::isOccupied)
                .count();

        parkingLot.setAvailableSpaces(parkingLot.getCapacity() - Math.toIntExact(occupiedParkingSpaces));
        parkingLot.setParkingSpaceList(updatedParkingSpaceList);
        parkingLot.setCapacity(parkingLot.getCapacity() + 1);

        parkingLotRepository.save(parkingLot);
    }

    private List<ParkingSpace> updateParkingSpaceList(ParkingLot parkingLot, ParkingSpace parkingSpace) {
        List<ParkingSpace> updatedParkingSpaceList = new ArrayList<>();
        updatedParkingSpaceList.addAll(parkingLot.getParkingSpaceList());
        updatedParkingSpaceList.add(parkingSpace);

        return updatedParkingSpaceList;
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

    public ParkingSpace updateToIsOccupiedWhenReserved(ParkingSpace parkingSpace) {
        Optional<ParkingSpace> parkingSpaceToOccupy = parkingSpaceRepository.findById(parkingSpace.getId());

        if(parkingSpaceToOccupy.isPresent()){
            parkingSpaceToOccupy.get().setOccupied(true);
            return parkingSpaceRepository.save(parkingSpaceToOccupy.get());
        }

        return null;
    }

    public List<ParkingSpace> findAllByParkingLotName(String parkingLotName) {
        return parkingSpaceRepository.findAllByParkingLotName(parkingLotName);
    }

}
