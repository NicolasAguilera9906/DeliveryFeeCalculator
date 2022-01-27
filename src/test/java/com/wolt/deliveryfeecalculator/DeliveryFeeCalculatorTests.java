package com.wolt.deliveryfeecalculator;

import com.wolt.deliveryfeecalculator.controllers.dto.DeliveryDTO;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;


import java.util.ArrayList;
import java.util.Random;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DeliveryFeeCalculatorTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void feeShouldBe0WhenCartValueIsEqualTo100Euros() throws Exception {
		DeliveryDTO deliveryDTO = createDeliveryDTO(10000, 2235, 4, "2021-10-12T13:00:00Z");
		MvcResult response = mockMvc.perform(post("/deliveries/fees/calculate")
						.contentType(MediaType.APPLICATION_JSON)
						.content(getDeliveryJSON(deliveryDTO)))
				.andExpect(status().isOk())
				.andReturn();
		Integer fee = Integer.valueOf(response.getResponse().getContentAsString());
		double delta = 0.000001d;
		double expectedFee = 0;
		Assert.assertEquals(expectedFee, fee, delta);
	}

	@Test
	void feeShouldBe0WhenCartValueIsGreaterTo100Euros() throws Exception {
		DeliveryDTO deliveryDTO = createDeliveryDTO(10001, 2235, 4, "2021-10-12T13:00:00Z");
		MvcResult response = mockMvc.perform(post("/deliveries/fees/calculate")
						.contentType(MediaType.APPLICATION_JSON)
						.content(getDeliveryJSON(deliveryDTO)))
				.andExpect(status().isOk())
				.andReturn();
		Integer fee = Integer.valueOf(response.getResponse().getContentAsString());
		double delta = 0.000001d;
		double expectedFee = 0;
		Assert.assertEquals(expectedFee, fee, delta);
	}

	@Test
	void feeShouldBe2EurosForTheFirst1000km() throws Exception {
		DeliveryDTO deliveryDTO = createDeliveryDTO(1100, 799, 1, "2021-10-12T13:00:00Z");
		MvcResult response = mockMvc.perform(post("/deliveries/fees/calculate")
						.contentType(MediaType.APPLICATION_JSON)
						.content(getDeliveryJSON(deliveryDTO)))
				.andExpect(status().isOk())
				.andReturn();
		Integer actualFee = Integer.valueOf(response.getResponse().getContentAsString());
		double delta = 0.000001d;
		double expectedFee = 200;
		Assert.assertEquals(expectedFee, actualFee, delta);
	}

	@Test
	void feeShouldIncreaseEvery500MetersIfDistanceGreaterThan1000Meters() throws Exception {
		DeliveryDTO deliveryDTO = createDeliveryDTO(1100, 3400, 1, "2021-10-12T13:00:00Z");
		MvcResult response = mockMvc.perform(post("/deliveries/fees/calculate")
						.contentType(MediaType.APPLICATION_JSON)
						.content(getDeliveryJSON(deliveryDTO)))
				.andExpect(status().isOk())
				.andReturn();
		Integer actualFee = Integer.valueOf(response.getResponse().getContentAsString());
		double delta = 0.000001d;
		double expectedFee = 700;
		Assert.assertEquals(expectedFee, actualFee, delta);
	}

	@Test
	void surchargeShouldBeAddedIfCartValueIsLessThan10Euros() throws Exception {
		DeliveryDTO deliveryDTO = createDeliveryDTO(900, 500, 1, "2021-10-12T13:00:00Z");
		MvcResult response = mockMvc.perform(post("/deliveries/fees/calculate")
						.contentType(MediaType.APPLICATION_JSON)
						.content(getDeliveryJSON(deliveryDTO)))
				.andExpect(status().isOk())
				.andReturn();
		Integer actualFee = Integer.valueOf(response.getResponse().getContentAsString());
		double delta = 0.000001d;
		double expectedCartSurcharge = 100;
		double expectedDistanceFee = 200;
		double expectedFee = expectedCartSurcharge + expectedDistanceFee;
		Assert.assertEquals(expectedFee, actualFee, delta);
	}

	@Test
	void surchargeShouldNotBeAddedIfCartValueIsEqualTo10Euros() throws Exception {
		DeliveryDTO deliveryDTO = createDeliveryDTO(1000, 500, 1, "2021-10-12T13:00:00Z");
		MvcResult response = mockMvc.perform(post("/deliveries/fees/calculate")
						.contentType(MediaType.APPLICATION_JSON)
						.content(getDeliveryJSON(deliveryDTO)))
				.andExpect(status().isOk())
				.andReturn();
		Integer actualFee = Integer.valueOf(response.getResponse().getContentAsString());
		double delta = 0.000001d;
		double expectedDistanceFee = 200;
		double expectedFee = expectedDistanceFee;
		Assert.assertEquals(expectedFee, actualFee, delta);
	}

	@Test
	void surchargeShouldNotBeAddedIfCartValueIsGreaterThan10Euros() throws Exception {
		DeliveryDTO deliveryDTO = createDeliveryDTO(2000, 500, 1, "2021-10-12T13:00:00Z");
		MvcResult response = mockMvc.perform(post("/deliveries/fees/calculate")
						.contentType(MediaType.APPLICATION_JSON)
						.content(getDeliveryJSON(deliveryDTO)))
				.andExpect(status().isOk())
				.andReturn();
		Integer actualFee = Integer.valueOf(response.getResponse().getContentAsString());
		double delta = 0.000001d;
		double expectedDistanceFee = 200;
		double expectedFee = expectedDistanceFee;
		Assert.assertEquals(expectedFee, actualFee, delta);
	}

	@Test
	void surchargeShouldBeAddedIfTheNumberOfItemsIsEqualTo5() throws Exception {
		DeliveryDTO deliveryDTO = createDeliveryDTO(2000, 500, 5, "2021-10-12T13:00:00Z");
		MvcResult response = mockMvc.perform(post("/deliveries/fees/calculate")
						.contentType(MediaType.APPLICATION_JSON)
						.content(getDeliveryJSON(deliveryDTO)))
				.andExpect(status().isOk())
				.andReturn();
		Integer actualFee = Integer.valueOf(response.getResponse().getContentAsString());
		double delta = 0.000001d;
		double expectedDistanceFee = 200;
		double expectedNumberOfItemsSurcharge = (deliveryDTO.getNumberOfItems() - 4) * 50;
		double expectedFee = expectedDistanceFee + expectedNumberOfItemsSurcharge;
		Assert.assertEquals(expectedFee, actualFee, delta);
	}

	@Test
	void surchargeShouldBeAddedIfTheNumberOfItemsIsGreaterThan5() throws Exception {
		DeliveryDTO deliveryDTO = createDeliveryDTO(2000, 500, 12, "2021-10-12T13:00:00Z");
		MvcResult response = mockMvc.perform(post("/deliveries/fees/calculate")
						.contentType(MediaType.APPLICATION_JSON)
						.content(getDeliveryJSON(deliveryDTO)))
				.andExpect(status().isOk())
				.andReturn();
		Integer actualFee = Integer.valueOf(response.getResponse().getContentAsString());
		double delta = 0.000001d;
		double expectedDistanceFee = 200;
		double expectedNumberOfItemsSurcharge = (deliveryDTO.getNumberOfItems() - 4) * 50;
		double expectedFee = expectedDistanceFee + expectedNumberOfItemsSurcharge;
		Assert.assertEquals(expectedFee, actualFee, delta);
	}

	@Test
	void feeShouldNotBeGreaterThan15() throws Exception {
		DeliveryDTO deliveryDTO = createDeliveryDTO(2000, 500, 90, "2021-10-12T13:00:00Z");
		MvcResult response = mockMvc.perform(post("/deliveries/fees/calculate")
						.contentType(MediaType.APPLICATION_JSON)
						.content(getDeliveryJSON(deliveryDTO)))
				.andExpect(status().isInternalServerError())
				.andReturn();
		String responseMessage = response.getResponse().getContentAsString();
		String expectedErrorMessage = "The delivery fee can never be more than 15€, including possible surcharges";
		Assert.assertEquals(expectedErrorMessage, responseMessage);
	}

	@Test
	void feeShouldBeMultipliedByOnePointOneOnTheFridayRushAt15() throws Exception {
		DeliveryDTO deliveryDTO = createDeliveryDTO(2000, 500, 3, "2022-01-28T15:00:00Z");
		MvcResult response = mockMvc.perform(post("/deliveries/fees/calculate")
						.contentType(MediaType.APPLICATION_JSON)
						.content(getDeliveryJSON(deliveryDTO)))
				.andExpect(status().isOk())
				.andReturn();
		Integer actualFee = Integer.valueOf(response.getResponse().getContentAsString());
		double delta = 0.000001d;
		double expectedDistanceFee = 200;
		double expectedFee = expectedDistanceFee * 1.1;
		Assert.assertEquals(expectedFee, actualFee, delta);
	}

	@Test
	void feeShouldBeMultipliedByOnePointOneOnTheFridayRushAt19() throws Exception {
		DeliveryDTO deliveryDTO = createDeliveryDTO(2000, 500, 3, "2022-03-04T19:00:00Z");
		MvcResult response = mockMvc.perform(post("/deliveries/fees/calculate")
						.contentType(MediaType.APPLICATION_JSON)
						.content(getDeliveryJSON(deliveryDTO)))
				.andExpect(status().isOk())
				.andReturn();
		Integer actualFee = Integer.valueOf(response.getResponse().getContentAsString());
		double delta = 0.000001d;
		double expectedDistanceFee = 200;
		double expectedFee = expectedDistanceFee * 1.1;
		Assert.assertEquals(expectedFee, actualFee, delta);
	}

	@Test
	void feeShouldBeMultipliedByOnePointOneOnTheFridayRushBetween13and19() throws Exception {
		DeliveryDTO deliveryDTO = createDeliveryDTO(2000, 500, 3, "2022-05-13T16:55:00Z");
		MvcResult response = mockMvc.perform(post("/deliveries/fees/calculate")
						.contentType(MediaType.APPLICATION_JSON)
						.content(getDeliveryJSON(deliveryDTO)))
				.andExpect(status().isOk())
				.andReturn();
		Integer actualFee = Integer.valueOf(response.getResponse().getContentAsString());
		double delta = 0.000001d;
		double expectedDistanceFee = 200;
		double expectedFee = expectedDistanceFee * 1.1;
		Assert.assertEquals(expectedFee, actualFee, delta);
	}

	@Test
	void feeShouldNotBeCalculatedIfTheValueOfTheCartIsLessThan0() throws Exception {
		DeliveryDTO deliveryDTO = createDeliveryDTO(-1, 500, 3, "2022-05-13T16:55:00Z");
		MvcResult response = mockMvc.perform(post("/deliveries/fees/calculate")
						.contentType(MediaType.APPLICATION_JSON)
						.content(getDeliveryJSON(deliveryDTO)))
				.andExpect(status().isBadRequest())
				.andReturn();
		String responseMessage = response.getResponse().getContentAsString();
		String expectedErrorMessage = "The cart value cannot be less than 0";
		Assert.assertEquals(expectedErrorMessage, responseMessage);
	}

	@Test
	void feeShouldNotBeCalculatedIfTheValueOfTheDistanceIsLessThan0() throws Exception {
		DeliveryDTO deliveryDTO = createDeliveryDTO(12, -76, 3, "2022-05-13T16:55:00Z");
		MvcResult response = mockMvc.perform(post("/deliveries/fees/calculate")
						.contentType(MediaType.APPLICATION_JSON)
						.content(getDeliveryJSON(deliveryDTO)))
				.andExpect(status().isBadRequest())
				.andReturn();
		String responseMessage = response.getResponse().getContentAsString();
		String expectedErrorMessage = "Distance cannot be less than 0";
		Assert.assertEquals(expectedErrorMessage, responseMessage);
	}

	@Test
	void feeShouldNotBeCalculatedIfTheValueOfTheNumberOfItemsIsLessThan0() throws Exception {
		DeliveryDTO deliveryDTO = createDeliveryDTO(12, 1000, -23, "2022-05-13T16:55:00Z");
		MvcResult response = mockMvc.perform(post("/deliveries/fees/calculate")
						.contentType(MediaType.APPLICATION_JSON)
						.content(getDeliveryJSON(deliveryDTO)))
				.andExpect(status().isBadRequest())
				.andReturn();
		String responseMessage = response.getResponse().getContentAsString();
		String expectedErrorMessage = "The number of items cannot be less than 0";
		Assert.assertEquals(expectedErrorMessage, responseMessage);
	}

	@Test
	void feeShouldNotBeCalculatedIfTheDateIsInTheWrongFormat() throws Exception {
		DeliveryDTO deliveryDTO = createDeliveryDTO(12, 1000, 2, "-05-13T16:55:00Z");
		MvcResult response = mockMvc.perform(post("/deliveries/fees/calculate")
						.contentType(MediaType.APPLICATION_JSON)
						.content(getDeliveryJSON(deliveryDTO)))
				.andExpect(status().isBadRequest())
				.andReturn();
		String responseMessage = response.getResponse().getContentAsString();
		String expectedErrorMessage = "Date is in the wrong format";
		Assert.assertEquals(expectedErrorMessage, responseMessage);
	}

	@Test
	void feeShouldNotBeCalculatedIfAnIntegerIsInAnIncorrectFormat() throws Exception {
		mockMvc.perform(post("/deliveries/fees/calculate")
						.contentType(MediaType.APPLICATION_JSON)
						.content(getDeliveryWrongJSON()))
				.andExpect(status().isBadRequest());
	}

	@Test
	void feeShouldNotBeCalculatedWhenAParameterIsNull() throws Exception {
		String deliveryJSON = getDeliveryWrongJSONWithNullParameters();
		mockMvc.perform(post("/deliveries/fees/calculate")
						.contentType(MediaType.APPLICATION_JSON)
						.content(deliveryJSON))
				.andExpect(status().isBadRequest());
	}
	
	private String getDeliveryWrongJSONWithNullParameters() {
		ArrayList parameters = new ArrayList();
		parameters.add(1);
		parameters.add(1000);
		parameters.add(2);
		parameters.add("2022-05-13T16:55:00Z");
		Random rand = new Random();
		int n = rand.nextInt(4);
		parameters.set(n,null);
		return "{\"cart_value\": " + parameters.get(0) + ",\n" +
				"    \"delivery_distance\": " + parameters.get(1) + ",\n" +
				"    \"number_of_items\": " + parameters.get(2) + ",\n" +
				"    \"time\": \"" + parameters.get(3) + "\"\n" +
				"}";
	}



	private String getDeliveryWrongJSON() {
		ArrayList parameters = new ArrayList();
		parameters.add(1);
		parameters.add(1000);
		parameters.add(2);
		parameters.add("2022-05-13T16:55:00Z");
		Random rand = new Random();
		int n = rand.nextInt(4);
		parameters.set(n,"wrong value");
		return "{\"cart_value\": " + parameters.get(0) + ",\n" +
				"    \"delivery_distance\": " + parameters.get(1) + ",\n" +
				"    \"number_of_items\": " + parameters.get(2) + ",\n" +
				"    \"time\": \"" + parameters.get(3) + "\"\n" +
				"}";
	}

	private String getDeliveryJSON(DeliveryDTO deliveryDTO) {
		return "{\"cart_value\": " + deliveryDTO.getCartValue() + ",\n" +
				"    \"delivery_distance\": " + deliveryDTO.getDeliveryDistance() + ",\n" +
				"    \"number_of_items\": " + deliveryDTO.getNumberOfItems() + ",\n" +
				"    \"time\": \"" + deliveryDTO.getTime() + "\"\n" +
				"}";
	}

	private DeliveryDTO createDeliveryDTO(int cartValue, int deliveryDistance, int numberOfItems, String time) {
		DeliveryDTO delivery = new DeliveryDTO(cartValue, deliveryDistance, numberOfItems, time);
		return delivery;
	}

}

