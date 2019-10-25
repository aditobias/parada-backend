package com.example.paradabackend.services;

import com.example.paradabackend.entities.ParkingLot;
import com.example.paradabackend.repositories.ParkingLotRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Service
public class ParkingLotService {

    @Autowired
    ParkingLotRepository parkingLotRepository;

    public Iterable<ParkingLot> findAllParkingLot(Integer page, Integer pageSize) {
        return parkingLotRepository.findAll(PageRequest.of(page, pageSize));
    }

    public ParkingLot addParkingLot(ParkingLot parkingLot) throws NotFoundException {
        if (parkingLot == null) {
            throw new NotFoundException("Please complete all fields.");
        }

        return parkingLotRepository.save(parkingLot);
    }

    public ParkingLot findSpecificParkingLot(String parkingLotName) throws NotFoundException {

        ParkingLot parkingLot = parkingLotRepository.findByParkingLotName(parkingLotName);

        if (isNull(parkingLot)) {
            throw new NotFoundException("Parking Lot not found!");
        }

        return parkingLot;

    }
}
