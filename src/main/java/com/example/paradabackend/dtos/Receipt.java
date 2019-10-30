package com.example.paradabackend.dtos;


import com.example.paradabackend.entities.ParkingTransaction;

public class Receipt {

    private ParkingTransaction parkingTransaction;

    public void setParkingTransaction(ParkingTransaction parkingTransaction) {
        this.parkingTransaction = parkingTransaction;
    }

    public ParkingTransaction getParkingTransaction() {
        return parkingTransaction;
    }
}
