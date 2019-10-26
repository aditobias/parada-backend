package com.example.paradabackend.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class ParkingTransaction {

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String parkingLotName;
    private Integer parkingLevel;
    private String parkingPosition;
    private Boolean isOccupied;
    private Integer price;
    private String voided;
    private Timestamp creationDateTime;

    public ParkingTransaction() {
    }

    private Timestamp closedDateTime;
    private String isPaid;

    public ParkingTransaction(String username, String parkingLotName, String parkingPosition) {
        this.username = username;
        this.parkingLotName = parkingLotName;
        this.parkingPosition = parkingPosition;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getParkingLotName() {
        return parkingLotName;
    }

    public void setParkingLotName(String parkingLotName) {
        this.parkingLotName = parkingLotName;
    }

    public Integer getParkingLevel() {
        return parkingLevel;
    }

    public void setParkingLevel(Integer parkingLevel) {
        this.parkingLevel = parkingLevel;
    }

    public String getParkingPosition() {
        return parkingPosition;
    }

    public void setParkingPosition(String parkingPosition) {
        this.parkingPosition = parkingPosition;
    }

    public Boolean getOccupied() {
        return isOccupied;
    }

    public void setOccupied(Boolean occupied) {
        isOccupied = occupied;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getVoided() {
        return voided;
    }

    public void setVoided(String voided) {
        this.voided = voided;
    }

    public Timestamp getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(Timestamp creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public Timestamp getClosedDateTime() {
        return closedDateTime;
    }

    public void setClosedDateTime(Timestamp closedDateTime) {
        this.closedDateTime = closedDateTime;
    }

    public String getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(String isPaid) {
        this.isPaid = isPaid;
    }
}
