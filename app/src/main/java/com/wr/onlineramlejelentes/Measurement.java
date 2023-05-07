package com.wr.onlineramlejelentes;

import java.util.Date;

public class Measurement {

    private String documentId ;
    private String placeOfUsageIdentifier;
    private String measurementDate;
    private int measurementValue;

    public Measurement() {
    }

    public Measurement(String placeOfUsageIdentifier, String measurementDate, int measurementValue) {
        this.placeOfUsageIdentifier = placeOfUsageIdentifier;
        this.measurementDate = measurementDate;
        this.measurementValue = measurementValue;
    }

    public String getPlaceOfUsageIdentifier() {
        return placeOfUsageIdentifier;
    }

    public void setPlaceOfUsageIdentifier(String placeOfUsageIdentifier) {
        this.placeOfUsageIdentifier = placeOfUsageIdentifier;
    }

    public String getMeasurementDate() {
        return measurementDate;
    }

    public void setMeasurementDate(String measurementDate) {
        this.measurementDate = measurementDate;
    }

    public int getMeasurementValue() {
        return measurementValue;
    }

    public void setMeasurementValue(int measurementValue) {
        this.measurementValue = measurementValue;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getDocumentId() {
        return documentId;
    }
}
