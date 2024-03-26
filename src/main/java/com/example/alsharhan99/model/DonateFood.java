package com.example.alsharhan99.model;

public class DonateFood {

    private String pickup;
    private String foodItem;
    private String description;
    private String preferredTime;
    private String pickUpDay;

    public DonateFood() {
    }

    public DonateFood(String pickup, String foodItem, String description, String preferredTime, String pickUpDay) {
        this.pickup = pickup;
        this.foodItem = foodItem;
        this.description = description;
        this.preferredTime = preferredTime;
        this.pickUpDay = pickUpDay;
    }

    public String getPickup() {
        return pickup;
    }

    public void setPickup(String pickup) {
        this.pickup = pickup;
    }

    public String getFoodItem() {
        return foodItem;
    }

    public void setFoodItem(String foodItem) {
        this.foodItem = foodItem;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPreferredTime() {
        return preferredTime;
    }

    public void setPreferredTime(String preferredTime) {
        this.preferredTime = preferredTime;
    }

    public String getPickUpDay() {
        return pickUpDay;
    }

    public void setPickUpDay(String pickUpDay) {
        this.pickUpDay = pickUpDay;
    }
}
