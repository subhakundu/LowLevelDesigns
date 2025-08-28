package com.examples.lld.parkinglot.billing;

import com.examples.lld.parkinglot.constants.BillingType;

public class BillingEngineFactory {

    public static BillingUtility getBillingEngine(BillingType billingType) {
        switch (billingType) {
            case HOURLY_BILLING -> {
                return HourlyBilling.getHourlyBilling();
            }
        }
        return null;
    }
}
