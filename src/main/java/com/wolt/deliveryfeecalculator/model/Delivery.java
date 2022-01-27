package com.wolt.deliveryfeecalculator.model;

import com.wolt.deliveryfeecalculator.controllers.dto.DeliveryDTO;

/**
 * Class For The Deliveries On DeliveryFeeCalculator App.
 *
 * @author Error Nicolás Aguilera Contreras
 */
public class Delivery {

    private int cartValue;
    private int deliveryDistance;
    private int numberOfItems;
    private String time;

    /**
     * Basic Constructor For Delivery.
     */
    public Delivery() {
    }

    /**
     * Constructor for Delivery
     *
     * @param deliveryDTO Data Transfer Object For Delivery Object
     */
    public Delivery(DeliveryDTO deliveryDTO) {
        this.cartValue = deliveryDTO.getCartValue();
        this.deliveryDistance = deliveryDTO.getDeliveryDistance();
        this.numberOfItems = deliveryDTO.getNumberOfItems();
        this.time = deliveryDTO.getTime();
    }

    /**
     * Constructor for Delivery
     *
     * @param cartValue The value of the delivery cart
     * @param deliveryDistance The distance that the delivery has to travel
     * @param numberOfItems The number of items in the delivery
     * @param time The exact time at which the delivery was requested
     */
    public Delivery(int cartValue, int deliveryDistance, int numberOfItems, String time) {
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
    public int getCartValue() {
        return cartValue;
    }

    /**
     * Returns the distance that the delivery has to travel
     *
     * @return the distance that the delivery has to travel
     */
    public int getDeliveryDistance() {
        return deliveryDistance;
    }

    /**
     * Returns the number of items in the delivery
     *
     * @return the number of items in the delivery
     */
    public int getNumberOfItems() {
        return numberOfItems;
    }

    /**
     * Returns the exact time at which the address was requested
     *
     * @return The exact time at which the delivery was requested
     */
    public String getTime() {
        return time;
    }

}
