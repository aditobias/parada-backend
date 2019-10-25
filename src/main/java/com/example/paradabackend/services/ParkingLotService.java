package com.example.paradabackend.services;

import com.example.paradabackend.entities.ParkingLot;
import com.example.paradabackend.repositories.ParkingLotRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

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
}
