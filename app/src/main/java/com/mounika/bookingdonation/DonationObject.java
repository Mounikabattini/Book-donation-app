package com.mounika.bookingdonation;

public class DonationObject {
    String donorName;
    String amount;
    String location;

    public void setDonorName(String donorName) {
        this.donorName = donorName;
    }

    public void setAmount(String amount) {
        this.amount = amount;
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

    String time;
}
