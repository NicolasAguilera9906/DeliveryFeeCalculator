package com.wolt.deliveryfeecalculator.controllers;

import com.wolt.deliveryfeecalculator.controllers.dto.DeliveryDTO;
import com.wolt.deliveryfeecalculator.model.Delivery;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/v1/prices")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,RequestMethod.DELETE})
public class PriceController {


    @GetMapping("")
    public ResponseEntity<Object> getDeliveryPrice (
            @RequestBody DeliveryDTO deliveryDTO)
    {
        Delivery delivery = new Delivery(deliveryDTO);
        return new ResponseEntity<>(deliveryDTO, HttpStatus.OK);
    }


}