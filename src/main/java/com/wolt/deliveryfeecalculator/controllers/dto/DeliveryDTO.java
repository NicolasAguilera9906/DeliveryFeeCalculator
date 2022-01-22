package com.wolt.deliveryfeecalculator.controllers.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeliveryDTO {

    private int cartValue;
    private int deliveryDistance;
    private int numberOfItems;
    private String time;

    public DeliveryDTO() {
    }

    public DeliveryDTO(int cartValue, int deliveryDistance, int numberOfItems, String time) {
        this.cartValue = cartValue;
        this.deliveryDistance = deliveryDistance;
        this.numberOfItems = numberOfItems;
        this.time = time;
    }

    @JsonProperty("cart_value")
    public int getCartValue() {
        return cartValue;
    }

    public void setCartValue(int cartValue) {
        this.cartValue = cartValue;
    }

    @JsonProperty("delivery_distance")
    public int getDeliveryDistance() {
        return deliveryDistance;
    }

    public void setDeliveryDistance(int deliveryDistance) {
        this.deliveryDistance = deliveryDistance;
    }

    @JsonProperty("number_of_items")
    public int getNumberOfItems() {
        return numberOfItems;
    }

    public void setNumberOfItems(int numberOfItems) {
        this.numberOfItems = numberOfItems;
    }

    @JsonProperty("time")
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
