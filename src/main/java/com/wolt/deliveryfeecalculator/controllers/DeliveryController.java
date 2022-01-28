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
import java.util.HashMap;
import java.util.Map;


/**
 * REST API Controller for Deliveries
 */
@RestController
@RequestMapping(value = "deliveries/fees")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,RequestMethod.DELETE})
public class DeliveryController {

    @Autowired
    private DeliveryServices deliveryServices;

    /**
     * Calculates the fee of a delivery
     *
     * @param deliveryDTO The Delivery information
     * @return A Response Entity with the Response Status
     * @throws DeliveryFeeCalculatorException when something fails
     */
    @PostMapping("/calculate")
    public ResponseEntity<Map<String, Integer>> getDeliveryPrice (
            @Valid @RequestBody DeliveryDTO deliveryDTO) throws DeliveryFeeCalculatorException {
        Delivery delivery = new Delivery(deliveryDTO);
        int fee = deliveryServices.getDeliveryFee(delivery);
        Map<String, Integer> resp = new HashMap();
        resp.put("delivery_fee", fee);
        return new ResponseEntity<Map<String, Integer>>(resp, HttpStatus.OK);
    }
}