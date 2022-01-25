package com.wolt.deliveryfeecalculator.model;

import com.wolt.deliveryfeecalculator.controllers.dto.DeliveryDTO;

public class Delivery {

    private int cartValue;
    private int deliveryDistance;
    private int numberOfItems;
    private String time;

    public Delivery() {
    }

    public Delivery(DeliveryDTO deliveryDTO) {
        this.cartValue = deliveryDTO.getCartValue();
        this.deliveryDistance = deliveryDTO.getDeliveryDistance();
        this.numberOfItems = deliveryDTO.getNumberOfItems();
        this.time = deliveryDTO.getTime();
    }

    public Delivery(int cartValue, int deliveryDistance, int numberOfItems, String time) {
        this.cartValue = cartValue;
        this.deliveryDistance = deliveryDistance;
        this.numberOfItems = numberOfItems;
        this.time = time;
    }

    public int getCartValue() {
        return cartValue;
    }

    public void setCartValue(int cartValue) {
        this.cartValue = cartValue;
    }

    public int getDeliveryDistance() {
        return deliveryDistance;
    }

    public void setDeliveryDistance(int deliveryDistance) {
        this.deliveryDistance = deliveryDistance;
    }

    public int getNumberOfItems() {
        return numberOfItems;
    }

    public void setNumberOfItems(int numberOfItems) {
        this.numberOfItems = numberOfItems;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
