package com.wolt.deliveryfeecalculator.controllers;

import com.wolt.deliveryfeecalculator.controllers.dto.DeliveryDTO;
import com.wolt.deliveryfeecalculator.exceptions.DeliveryFeeCalculatorException;
import com.wolt.deliveryfeecalculator.model.Delivery;
import com.wolt.deliveryfeecalculator.services.DeliveryServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "api/v1/prices")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,RequestMethod.DELETE})
public class DeliveryController {

    @Autowired
    private DeliveryServices deliveryServices;

    @GetMapping("")
    public ResponseEntity<Object> getDeliveryPrice (
            @Valid @RequestBody DeliveryDTO deliveryDTO) throws DeliveryFeeCalculatorException {
        Delivery delivery = new Delivery(deliveryDTO);
        int price = deliveryServices.getDeliveryFee(delivery);
        return new ResponseEntity<>(price, HttpStatus.OK);
    }
}