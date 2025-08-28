# 🅿️ Parking Lot System

A simple **Parking Lot Management System** written in Java.  
It simulates entry/exit of vehicles, allocation of parking slots, billing, and re-use of freed slots.

---

## 🚀 Features
- Multi-floor parking lot support
- Configurable number of slots per floor
- Multiple entry and exit gates
- Vehicle entry/exit logging
- Automatic allocation of the **nearest free slot**
- Reuse of freed slots
- Billing calculation based on parking duration

---

## ⚙️ How It Works
1. **Initialization**
    - Define number of floors
    - Define number of slots per floor
    - Define number of entry/exit gates

2. **Vehicle Entry**
    - Vehicle is assigned the **first available slot** (`floor:slot`).
    - System prints an `ENTRY` log.

3. **Vehicle Exit**
    - Vehicle vacates slot.
    - System prints an `EXIT` log with **total bill amount**.
    - Freed slots become available for future vehicles.

---

## 🖥️ Sample Run

```text
Tell me how many floors of parking lot you want: 
2
Tell me how many parking slot you want in each floor.
10
Tell me how many entry gates you want: 
2
Tell me how many exit you want: 
1
All Vehicles are parked.
ENTRY: Vehicle V-2 entered and parked at slot: Slot:0:0
ENTRY: Vehicle V-1 entered and parked at slot: Slot:0:1
Vehicle: V-2 is leaving.
EXIT: Vehicle V-2 exited from slot: Slot:0:0. Total Bill = 10.0
...
EXIT: Vehicle V-11 exited from slot: Slot:0:0. Total Bill = 25.0
```

## How to run?
```
javac ParkingLot.java
java ParkingLot
```
