package com.wolt.deliveryfeecalculator.controllers;

import com.wolt.deliveryfeecalculator.controllers.dto.DeliveryDTO;
import com.wolt.deliveryfeecalculator.model.Delivery;
import com.wolt.deliveryfeecalculator.services.DeliveryServices;
import com.wolt.deliveryfeecalculator.services.PriceServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/v1/prices")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,RequestMethod.DELETE})
public class DeliveryController {

    @Autowired
    private DeliveryServices deliveryServices;


    @GetMapping("")
    public ResponseEntity<Object> getDeliveryPrice (
            @RequestBody DeliveryDTO deliveryDTO) throws Exception {

        Delivery delivery = new Delivery(deliveryDTO);
        int price = deliveryServices.getDeliveryPrice(delivery);
        return new ResponseEntity<>(price, HttpStatus.OK);
    }
}