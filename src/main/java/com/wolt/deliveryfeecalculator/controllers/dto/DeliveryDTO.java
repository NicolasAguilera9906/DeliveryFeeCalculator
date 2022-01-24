package com.wolt.deliveryfeecalculator.controllers.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public class DeliveryDTO {


    @NotNull
    private Integer cartValue;

    @NotNull
    private Integer deliveryDistance;

    @NotNull
    private Integer numberOfItems;

    @NotNull
    private String time;

    public DeliveryDTO() {
    }

    public DeliveryDTO(Integer cartValue, Integer deliveryDistance, Integer numberOfItems, String time) {
        this.cartValue = cartValue;
        this.deliveryDistance = deliveryDistance;
        this.numberOfItems = numberOfItems;
        this.time = time;
    }

    @JsonProperty("cart_value")
    public Integer getCartValue() {
        return cartValue;
    }

    public void setCartValue(int cartValue) {
        this.cartValue = cartValue;
    }

    @JsonProperty("delivery_distance")
    public Integer getDeliveryDistance() {
        return deliveryDistance;
    }

    public void setDeliveryDistance(int deliveryDistance) {
        this.deliveryDistance = deliveryDistance;
    }

    @JsonProperty("number_of_items")
    public Integer getNumberOfItems() {
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
