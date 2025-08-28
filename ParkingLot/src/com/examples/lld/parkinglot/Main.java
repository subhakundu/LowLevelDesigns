package com.examples.lld.parkinglot;

import com.examples.lld.parkinglot.billing.BillingEngineFactory;
import com.examples.lld.parkinglot.billing.BillingUtility;
import com.examples.lld.parkinglot.constants.BillingType;
import com.examples.lld.parkinglot.constants.GateType;
import com.examples.lld.parkinglot.constants.VehicleType;
import com.examples.lld.parkinglot.models.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Main {
    private static final AtomicInteger VEHICLE_COUNTER = new AtomicInteger(1);
    private static final Random random = new Random(1000);

    public static void main(String[] args) throws InterruptedException {
	    // write your code here
        Scanner scanner = new Scanner(System.in);
        System.out.println("Stage 1: Creation of Parking Lot");
        System.out.println("Tell me how many floors of parking lot you want: ");
        int floors = scanner.nextInt();
        // Scope of improvment for configuring each parking slot. Different configuration for each
        // floor is not allowed yet.
        System.out.println("Tell me how many parking slot you want in each floor.");
        int slots = scanner.nextInt();
        List<ParkingFloor> parkingFloors = new ArrayList<>(floors);
        for (int i=0; i<floors; i++) {
            parkingFloors.add(createSingleParkingFloor(i, slots));
        }
        ParkingLot parkingLot = new ParkingLot(parkingFloors);
        System.out.println("Tell me how many entry gates you want: ");
        int numberOfEntryGates = scanner.nextInt();
        for (int i=0; i<numberOfEntryGates; i++) {
            Gate gate = new Gate(i+1, GateType.ENTRY, new ArrayList<>());
        }
        System.out.println("Tell me how many exit you want: ");
        int numberOfExitGates = scanner.nextInt();
        for (int i=0; i<numberOfExitGates; i++) {
            Gate gate = new Gate(i+100, GateType.EXIT, new ArrayList<>());
        }

        ExecutorService entryExecutorService = Executors.newFixedThreadPool(numberOfEntryGates);
        ExecutorService exitExecutorService = Executors.newFixedThreadPool(numberOfExitGates);

        for (int i=0; i<50; i++) {
            entryExecutorService.submit(() -> {
                try {
                    String vehiclePlate = "V-" + VEHICLE_COUNTER.getAndIncrement();
                    Vehicle vehicle = new Vehicle(vehiclePlate, VehicleType.SMALL_FOUR_WHEELER_VEHICLE);
                    ParkingSlot parkingSlot = null;
                    for (ParkingFloor parkingFloor: parkingLot.getParkingFloors()) {
                        Ticket ticket = new Ticket(vehicle, System.currentTimeMillis());
                        ticket.setBillingType(BillingType.HOURLY_BILLING);
                        parkingSlot =
                                parkingFloor.assignAvailableParkingSlot(VehicleType.SMALL_FOUR_WHEELER_VEHICLE, ticket);
                        if (parkingSlot != null) {
                            break;
                        }
                    }
                    parkingLot.parkVehicle(parkingSlot);
                    System.out.println("ENTRY: Vehicle " + parkingSlot.getTicket().getVehicle().getVehicleNumber() +
                            " entered and parked at slot: " + parkingSlot.getSlotId());
                    // Sleep a bit to simulate random arrival spacing
                    Thread.sleep(random.nextInt(2000));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        System.out.println("All Vehicles are parked.");
        for (int i = 0; i < 50; i++) { // 50 cars exiting
            exitExecutorService.submit(() -> {
                try {
                    // Random delay before exit
                    Thread.sleep(random.nextInt(3000));

                    // Pick random vehicle from parked list
                    Map<String, ParkingSlot> mapOfAllParkedVehicles = parkingLot.getMapOfAllCarsParked();
                    List<String> keys = new ArrayList<>(mapOfAllParkedVehicles.keySet());
                    if (keys.isEmpty()) return; // nothing to exit

                    String randomKey = keys.get(random.nextInt(keys.size()));
                    Ticket ticket = parkingLot.unparkVehicle(randomKey);
                    String vehicleNumber = ticket.getVehicle().getVehicleNumber();
                    System.out.println("Vehicle: " + ticket.getVehicle().getVehicleNumber() + " is leaving.");
                    ticket.setEndTime(System.currentTimeMillis());
                    assertNotNull(ticket);
                    assertTrue(randomKey.equals(vehicleNumber));
                    if (ticket != null) {
                        BillingUtility billingUtility = BillingEngineFactory.getBillingEngine(ticket.getBillingType());
                        double bill = billingUtility.calculateBill(ticket);
                        System.out.println("EXIT: Vehicle " + randomKey + " exited from slot: " + ticket.getParkingSlot().getSlotId() + ". Total Bill = " + bill);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        entryExecutorService.shutdown();
        exitExecutorService.shutdown();
        entryExecutorService.awaitTermination(30, TimeUnit.SECONDS);
        exitExecutorService.awaitTermination(30, TimeUnit.SECONDS);

        System.out.println("Simulation finished. Vehicles still parked: " + parkingLot.getMapOfAllCarsParked().size());

    }

    private static ParkingFloor createSingleParkingFloor(int floorNumber, int slots) {
        ParkingFloor parkingFloor = new ParkingFloor(floorNumber);
        Map<VehicleType, List<ParkingSlot>> floorMap = new HashMap<>();
        List<ParkingSlot> listOfSlots = new ArrayList<>(slots);
        Map<VehicleType, List<ParkingSlot>> nextAvailableParkingSpotMap = new ConcurrentHashMap<>();
        List<ParkingSlot> listOfNextAvailableParkingSpot = new ArrayList<>();
        for (int j=0; j<slots; j++) {
            ParkingSlot parkingSlot = new ParkingSlot(VehicleType.SMALL_FOUR_WHEELER_VEHICLE);
            parkingSlot.setOccupied(false);
            parkingSlot.setSlotId("Slot:"+floorNumber+":"+j);
            parkingSlot.setParkingFloor(parkingFloor);
            listOfSlots.add(parkingSlot);
            listOfNextAvailableParkingSpot.add(parkingSlot);
        }
        floorMap.put(VehicleType.SMALL_FOUR_WHEELER_VEHICLE, listOfSlots);
        nextAvailableParkingSpotMap.put(VehicleType.SMALL_FOUR_WHEELER_VEHICLE, listOfNextAvailableParkingSpot);
        parkingFloor.setQueueOfAvailableParkingSlot(nextAvailableParkingSpotMap);
        return parkingFloor;
    }
}
