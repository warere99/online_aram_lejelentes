package com.wr.onlineramlejelentes;

public class PlaceOfUsage {
    private String customerCode;
    private String placeOfUsageIdentifier;
    private String placeOfUsageAddress;

    public PlaceOfUsage() {
    }

    public PlaceOfUsage(String placeOfUsageIdentifier, String placeOfUsageAddress) {
        this.placeOfUsageIdentifier = placeOfUsageIdentifier;
        this.placeOfUsageAddress = placeOfUsageAddress;
    }
    public PlaceOfUsage(String customer_code, String placeOfUsageIdentifier, String placeOfUsageAddress) {
        this.customerCode = customer_code;
        this.placeOfUsageIdentifier = placeOfUsageIdentifier;
        this.placeOfUsageAddress = placeOfUsageAddress;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public String getPlaceOfUsageIdentifier() {
        return this.placeOfUsageIdentifier;
    }

    public String getPlaceOfUsageAddress() {
        return this.placeOfUsageAddress;
    }
}
