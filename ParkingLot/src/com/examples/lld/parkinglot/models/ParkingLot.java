package com.examples.lld.parkinglot.models;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ParkingLot {
    private List<ParkingFloor> parkingFloors;
    private Map<String, ParkingSlot> mapOfAllCarsParked;


    public List<ParkingFloor> getParkingFloors() {
        return parkingFloors;
    }

    public ParkingLot(List<ParkingFloor> parkingFloors) {
        this.parkingFloors = parkingFloors;
        mapOfAllCarsParked = new ConcurrentHashMap<>();
    }

    public Map<String, ParkingSlot> getMapOfAllCarsParked() {
        return mapOfAllCarsParked;
    }

    public Ticket unparkVehicle(String vehicleNumber) {
        if (!mapOfAllCarsParked.containsKey(vehicleNumber)) {
            return null;
        }
        ParkingSlot parkingSlot = mapOfAllCarsParked.remove(vehicleNumber);
        Ticket ticket = parkingSlot.getTicket();
        if (!parkingSlot.cleanUpParkingLot()) {
            System.out.println("Vehicle " + vehicleNumber + " is unparked from slot: " + parkingSlot.getSlotId());
        }
        assertFalse(mapOfAllCarsParked.containsKey(vehicleNumber));
        return ticket;
    }

    public boolean parkVehicle(ParkingSlot parkingSlot) {
        if (!parkingSlot.isOccupied()) {
            return false;
        }
        String vehicleNumber = parkingSlot.getTicket().getVehicle().getVehicleNumber();
        mapOfAllCarsParked.put(vehicleNumber, parkingSlot);
        assertTrue(mapOfAllCarsParked.containsKey(vehicleNumber));
        assertTrue(mapOfAllCarsParked.get(vehicleNumber)
                .getTicket().getParkingSlot().getSlotId()==parkingSlot.getSlotId());
        return true;
    }
}
