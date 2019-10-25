package com.example.paradabackend.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ParkingLot {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String parkingLotName;
    private String location;
    private Integer capacity;
    private Integer flatRate;
    private Integer ratePerHour;
    private Integer succeedingHourRate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getParkingLotName() {
        return parkingLotName;
    }

    public void setParkingLotName(String parkingLotName) {
        this.parkingLotName = parkingLotName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getFlatRate() {
        return flatRate;
    }

    public void setFlatRate(Integer flatRate) {
        this.flatRate = flatRate;
    }

    public Integer getRatePerHour() {
        return ratePerHour;
    }

    public void setRatePerHour(Integer ratePerHour) {
        this.ratePerHour = ratePerHour;
    }

    public Integer getSucceedingHourRate() {
        return succeedingHourRate;
    }

    public void setSucceedingHoursRate(Integer succeedingHourRate) {
        this.succeedingHourRate = succeedingHourRate;
    }
}
