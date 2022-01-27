package com.wolt.deliveryfeecalculator.controllers.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public class DeliveryDTO {

    private Integer cartValue;

    private Integer deliveryDistance;

    private Integer numberOfItems;

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
    @NotNull(message = "Cannot be null")
    public Integer getCartValue() {
        return cartValue;
    }

    @JsonProperty("delivery_distance")
    @NotNull(message = "Cannot be null")
    public Integer getDeliveryDistance() {
        return deliveryDistance;
    }


    @JsonProperty("number_of_items")
    @NotNull(message = "Cannot be null")
    public Integer getNumberOfItems() {
        return numberOfItems;
    }


    @JsonProperty("time")
    @NotNull(message = "Cannot be null")
    public String getTime() {
        return time;
    }

}
