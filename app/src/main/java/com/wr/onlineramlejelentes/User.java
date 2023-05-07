package com.wr.onlineramlejelentes;

public class User {

    private String customerCode;
    private String email;
    private String name;

    private String documentRefId;

    public User() {
    }

    public User(String customerCode, String email, String name) {
        this.customerCode = customerCode;
        this.email = email;
        this.name = name;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDocumentRefId() {
        return documentRefId;
    }

    public void setDocumentRefId(String documentRefId) {
        this.documentRefId = documentRefId;
    }
}
