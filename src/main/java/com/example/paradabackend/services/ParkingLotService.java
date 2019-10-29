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
    private static final char[] PARKING_LOT_ROW = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @Autowired
    private ParkingSpaceRepository parkingSpaceRepository;

    public Iterable<ParkingLot> findAllParkingLot(Integer page, Integer pageSize) {
        return parkingLotRepository.findAll(PageRequest.of(page, pageSize));
    }

    public ParkingLot addParkingLot(ParkingLot parkingLot) throws Exception {

        ParkingLot parkingLotName = parkingLotRepository.findByParkingLotName(parkingLot.getParkingLotName());
        if (isNull(parkingLot.getParkingLotName())) {
            throw new IllegalArgumentException("No parking lot name!");
        }

        if (!isNull(parkingLotName)) {
            throw new IllegalArgumentException(parkingLot.getParkingLotName() + " already exists!");
        }

        if (parkingLot.getCapacity() < parkingLot.getMaxSpacePerLevel()) {
            throw new IllegalArgumentException("Max Space Per Level is greater than the Capacity!");
        }

        parkingLot.setAvailableSpaces(parkingLot.getCapacity());
        List<ParkingSpace> parkingSpaceList = generateParkingSpace(parkingLot);
        parkingLot.setAvailableSpaces(parkingLot.getCapacity());
        List<ParkingSpace> newParkingSpaceList = updateParkingSpaceList(parkingLot, parkingSpaceList);
        parkingLot.setParkingSpaceList(newParkingSpaceList);
        return parkingLotRepository.save(parkingLot);
    }

    public List<ParkingSpace> generateParkingSpace(ParkingLot parkingLot) {
        List<ParkingSpace> parkingSpaceList = new ArrayList<>();

        generateTotalParkingSpace(parkingLot, parkingSpaceList);

        generateAdditionalParkingSpace(parkingLot, parkingSpaceList);

        parkingSpaceRepository.saveAll(parkingSpaceList);
        return parkingSpaceList;
    }

    public void generateTotalParkingSpace(ParkingLot parkingLot, List<ParkingSpace> parkingSpaceList) {
        int totalLevel = parkingLot.getCapacity() / parkingLot.getMaxSpacePerLevel();
        int maxSpacePerLevel = parkingLot.getMaxSpacePerLevel();

        for (int level = 1; level <= totalLevel; level++) {
            for (int position = 1; position <= maxSpacePerLevel; position++) {
                ParkingSpace parkingSpace = generateParkingSpace(parkingLot, level, position);
                parkingSpaceList.add(parkingSpace);
            }
        }
    }

    public void generateAdditionalParkingSpace(ParkingLot parkingLot, List<ParkingSpace> parkingSpaceList) {
        int totalLevel = parkingLot.getCapacity() / parkingLot.getMaxSpacePerLevel();
        int extraSpace = parkingLot.getCapacity() % parkingLot.getMaxSpacePerLevel();
        if (extraSpace != 0) {
            for (int position = 1; position <= extraSpace; position++) {
                ParkingSpace parkingSpace = generateParkingSpace(parkingLot, totalLevel + 1, position);
                parkingSpaceList.add(parkingSpace);
            }
        }
    }

    public ParkingSpace generateParkingSpace(ParkingLot parkingLot, int level, int position) {
        if (level > PARKING_LOT_ROW.length) {
            throw new IndexOutOfBoundsException("Parking Level input cannot be greater than 26.");
        }

        ParkingSpace parkingSpace = new ParkingSpace();
        parkingSpace.setParkingLevel(level);
        parkingSpace.setParkingPosition(String.valueOf(PARKING_LOT_ROW[level - 1]) + position);
        parkingSpace.setId(getGeneratedIdForParkingSpace(parkingLot, parkingSpace));
        parkingSpace.setParkingLotName(parkingLot.getParkingLotName());
        parkingSpace.setOccupied(false);

        return parkingSpace;
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
        for (final char id : parkingLot.getParkingLotName().toCharArray())
            if (Character.isUpperCase(id) || Character.isDigit(id))
                parkingLotNameBuilder.append(id);

        return parkingLotNameBuilder;
    }
}
