package com.wolt.deliveryfeecalculator.services;

import com.wolt.deliveryfeecalculator.exceptions.DeliveryFeeCalculatorException;
import com.wolt.deliveryfeecalculator.model.Delivery;

public interface DeliveryServices {

    public int getDeliveryFee(Delivery delivery) throws DeliveryFeeCalculatorException;

}
