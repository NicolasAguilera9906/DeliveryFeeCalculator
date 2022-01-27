package com.wolt.deliveryfeecalculator.controllers.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

/**
 * Data Transfer Object For Delivery Object.
 *
 * @author Nicolás Aguilera Contreras
 */
public class DeliveryDTO {

    private Integer cartValue;
    private Integer deliveryDistance;
    private Integer numberOfItems;
    private String time;


    /**
     * Basic constructor for DeliveryDTO
     */
    public DeliveryDTO() {
    }

    /**
     * Constructor for DeliveryDTO
     * @param cartValue The value of the delivery cart
     * @param deliveryDistance The distance that the delivery has to travel
     * @param numberOfItems The number of items in the delivery
     * @param time The exact time at which the delivery was requested
     */
    public DeliveryDTO(Integer cartValue, Integer deliveryDistance, Integer numberOfItems, String time) {
        this.cartValue = cartValue;
        this.deliveryDistance = deliveryDistance;
        this.numberOfItems = numberOfItems;
        this.time = time;
    }

    /**
     * Returns the value of the delivery cart
     *
     * @return the value of the delivery cart
     */
    @JsonProperty("cart_value")
    @NotNull(message = "Cannot be null")
    public Integer getCartValue() {
        return cartValue;
    }

    /**
     * Returns the distance that the delivery has to travel
     *
     * @return the distance that the delivery has to travel
     */
    @JsonProperty("delivery_distance")
    @NotNull(message = "Cannot be null")
    public Integer getDeliveryDistance() {
        return deliveryDistance;
    }

    /**
     * Returns the number of items in the delivery
     *
     * @return the number of items in the delivery
     */
    @JsonProperty("number_of_items")
    @NotNull(message = "Cannot be null")
    public Integer getNumberOfItems() {
        return numberOfItems;
    }

    /**
     * Returns the exact time at which the address was requested
     *
     * @return The exact time at which the delivery was requested
     */
    @JsonProperty("time")
    @NotNull(message = "Cannot be null")
    public String getTime() {
        return time;
    }

}
