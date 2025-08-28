package com.examples.lld.parkinglot.billing;

import com.examples.lld.parkinglot.models.Ticket;

public interface BillingUtility {
    double calculateBill(Ticket ticket);
}
