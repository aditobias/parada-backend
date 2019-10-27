package com.example.paradabackend.services;

import com.example.paradabackend.entities.ParkingLot;
import com.example.paradabackend.entities.ParkingSpace;
import com.example.paradabackend.repositories.ParkingLotRepository;
import com.example.paradabackend.repositories.ParkingSpaceRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

@Service
public class ParkingLotService {

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @Autowired
    private ParkingSpaceRepository parkingSpaceRepository;


    public Iterable<ParkingLot> findAllParkingLot(Integer page, Integer pageSize) {
        return parkingLotRepository.findAll(PageRequest.of(page, pageSize));
    }

    public ParkingLot addParkingLot(ParkingLot parkingLot) throws Exception {
        if (isNull(parkingLot)) {
            throw new NotFoundException("Please complete all fields.");
        }

        ParkingLot parkingLotName = parkingLotRepository.findByParkingLotName(parkingLot.getParkingLotName());

        if (!isNull(parkingLotName)) {
            throw new IllegalArgumentException(parkingLot.getParkingLotName() + " already exists!");
        }

        Integer capacity = parkingLot.getCapacity();
        List<ParkingSpace> parkingSpaceList = new ArrayList<>();

        generateParkingSpace(parkingLot, capacity, parkingSpaceList);

        List<ParkingSpace> newParkingSpaceList = updateParkingSpaceList(parkingLot, parkingSpaceList);
        parkingLot.setParkingSpaceList(newParkingSpaceList);
        return parkingLotRepository.save(parkingLot);
    }

    private void generateParkingSpace(ParkingLot parkingLot, Integer capacity, List<ParkingSpace> parkingSpaceList) {
        for (int i = 0; i < capacity; i++) {
            ParkingSpace parkingSpace = new ParkingSpace();
            parkingSpace.setParkingLevel(1);
            parkingSpace.setParkingPosition("A1");
            parkingSpace.setId(getGeneratedIdForParkingSpace(parkingLot, parkingSpace));
            parkingSpace.setParkingLotName(parkingLot.getParkingLotName());
            parkingSpace.setOccupied(false);

            parkingSpaceList.add(parkingSpace);
            parkingSpaceRepository.save(parkingSpace);
        }
    }

    public ParkingLot findSpecificParkingLot(String parkingLotName) throws NotFoundException {

        ParkingLot parkingLot = parkingLotRepository.findByParkingLotName(parkingLotName);

        if (isNull(parkingLot)) {
            throw new NotFoundException("Parking Lot not found!");
        }

        return parkingLot;

    }

    private List<ParkingSpace> updateParkingSpaceList(ParkingLot parkingLot, List<ParkingSpace> parkingSpaceList) {
        List<ParkingSpace> updatedParkingSpaceList = new ArrayList<>();
        updatedParkingSpaceList.addAll(parkingLot.getParkingSpaceList());
        updatedParkingSpaceList.addAll(parkingSpaceList);

        return updatedParkingSpaceList;
    }

    private String getGeneratedIdForParkingSpace(ParkingLot parkingLot, ParkingSpace parkingSpace) {
        StringBuilder parkingLotNameBuilder = getParkingLotNameBuilder(parkingLot);

        return parkingLotNameBuilder.toString() + '-' + parkingSpace.getParkingLevel() + parkingSpace.getParkingPosition();
    }

    private StringBuilder getParkingLotNameBuilder(ParkingLot parkingLot) {
        StringBuilder parkingLotNameBuilder = new StringBuilder();
        for (final char c : parkingLot.getParkingLotName().toCharArray())
            if (Character.isUpperCase(c) || Character.isDigit(c))
                parkingLotNameBuilder.append(c);

        return parkingLotNameBuilder;
    }
}
