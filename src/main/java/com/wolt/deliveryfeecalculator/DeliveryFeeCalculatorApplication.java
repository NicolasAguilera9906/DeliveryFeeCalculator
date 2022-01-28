package com.wolt.deliveryfeecalculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main class of the DeliveryFeeCalculator App
 *
 * @author Nicolás Aguilera Contreras
 */
@SpringBootApplication
public class DeliveryFeeCalculatorApplication {

	/**
	 * Main Function That Runs The DeliveryFeeCalculator App
	 *
	 * @param args The extra arguments for the app, do not have functionality.
	 */
	public static void main(String[] args) {
		SpringApplication.run(DeliveryFeeCalculatorApplication.class, args);
	}

}
