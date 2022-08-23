package com.mounika.bookingdonation;

public class DonationObject {
    String donorName;
    String amount;
    String location;
    String PhoneNumber;
    String Title;
    String charityName;
    public DonationObject() {

    }

    public void setDonorName(String donorName) {
        this.donorName = donorName;
    }
    public void setDonarPhoneNummber(String PhoneNumber) {
        this.PhoneNumber = PhoneNumber;
    } public void setTitle(String Title) {
        this.Title = Title;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
    public void setCharityName(String charityName) {
        this.charityName = charityName;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDonorName() {
        return donorName;
    }

    public String getAmount() {
        return amount;
    }

    public String getLocation() {
        return location;
    }

    public String getTime() {
        return time;
    }
    public String getCharityName() {
        return charityName;
    }
    public String getTitle() {
        return Title;
    }public String getPhoneNumber() {
        return PhoneNumber;
    }

    String time;
}
