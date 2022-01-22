package com.wolt.deliveryfeecalculator.services;

import com.wolt.deliveryfeecalculator.model.Delivery;
import org.springframework.beans.factory.annotation.Autowired;

public interface DeliveryServices {


    public int getDeliveryPrice(Delivery delivery) throws Exception;

}
