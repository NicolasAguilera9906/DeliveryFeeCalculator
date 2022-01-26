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


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DeliveryFeeCalculatorTests{

	@Autowired
	private MockMvc mockMvc;

	@Test
	void feeShouldBe0WhenCartValueIsEqualTo100Euros() throws Exception {
		DeliveryDTO deliveryDTO = createDeliveryDTO(10000,2235,4,"2021-10-12T13:00:00Z");
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
		DeliveryDTO deliveryDTO = createDeliveryDTO(10001,2235,4,"2021-10-12T13:00:00Z");
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
		DeliveryDTO deliveryDTO = createDeliveryDTO(1100,799,1,"2021-10-12T13:00:00Z");
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
		DeliveryDTO deliveryDTO = createDeliveryDTO(1100,3400,1,"2021-10-12T13:00:00Z");
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
	void surchargeShouldBeAddedIfCartValueIsLessThan10Euros () throws Exception {
		DeliveryDTO deliveryDTO = createDeliveryDTO(900,500,1,"2021-10-12T13:00:00Z");
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
	void surchargeShouldNotBeAddedIfCartValueIsEqualTo10Euros () throws Exception {
		DeliveryDTO deliveryDTO = createDeliveryDTO(1000,500,1,"2021-10-12T13:00:00Z");
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
	void surchargeShouldNotBeAddedIfCartValueIsGreaterThan10Euros () throws Exception {
		DeliveryDTO deliveryDTO = createDeliveryDTO(2000,500,1,"2021-10-12T13:00:00Z");
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
		DeliveryDTO deliveryDTO = createDeliveryDTO(2000,500,5,"2021-10-12T13:00:00Z");
		MvcResult response = mockMvc.perform(post("/deliveries/fees/calculate")
						.contentType(MediaType.APPLICATION_JSON)
						.content(getDeliveryJSON(deliveryDTO)))
				.andExpect(status().isOk())
				.andReturn();
		Integer actualFee = Integer.valueOf(response.getResponse().getContentAsString());
		double delta = 0.000001d;
		double expectedDistanceFee = 200;
		double expectedNumberOfItemsSurcharge = (deliveryDTO.getNumberOfItems()-4)*50;
		double expectedFee = expectedDistanceFee + expectedNumberOfItemsSurcharge;
		Assert.assertEquals(expectedFee, actualFee, delta);
	}

	@Test
	void surchargeShouldBeAddedIfTheNumberOfItemsIsGreaterThan5() throws Exception {
		DeliveryDTO deliveryDTO = createDeliveryDTO(2000,500,12,"2021-10-12T13:00:00Z");
		MvcResult response = mockMvc.perform(post("/deliveries/fees/calculate")
						.contentType(MediaType.APPLICATION_JSON)
						.content(getDeliveryJSON(deliveryDTO)))
				.andExpect(status().isOk())
				.andReturn();
		Integer actualFee = Integer.valueOf(response.getResponse().getContentAsString());
		double delta = 0.000001d;
		double expectedDistanceFee = 200;
		double expectedNumberOfItemsSurcharge = (deliveryDTO.getNumberOfItems()-4)*50;
		double expectedFee = expectedDistanceFee + expectedNumberOfItemsSurcharge;
		Assert.assertEquals(expectedFee, actualFee, delta);
	}

	@Test
	void feeShouldNotBeGreaterThan15() throws Exception {
		DeliveryDTO deliveryDTO = createDeliveryDTO(2000,500,90,"2021-10-12T13:00:00Z");
		MvcResult response = mockMvc.perform(post("/deliveries/fees/calculate")
						.contentType(MediaType.APPLICATION_JSON)
						.content(getDeliveryJSON(deliveryDTO)))
				.andExpect(status().isInternalServerError())
				.andReturn();
		String responseMessage = response.getResponse().getContentAsString();
		String expectedErrorMessage = "The delivery fee can never be more than 15€, including possible surcharges";
		Assert.assertEquals(expectedErrorMessage,responseMessage);
	}

	@Test
	void feeShouldBeMultipliedByOnePointOneOnTheFridayRushAt15() throws Exception {
		DeliveryDTO deliveryDTO = createDeliveryDTO(2000,500,3,"2022-01-28T15:00:00Z");
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
		DeliveryDTO deliveryDTO = createDeliveryDTO(2000,500,3,"2022-03-04T19:00:00Z");
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
		DeliveryDTO deliveryDTO = createDeliveryDTO(2000,500,3,"2022-05-13T16:55:00Z");
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

	private String getDeliveryJSON(DeliveryDTO deliveryDTO) {
		return "{\"cart_value\": " + deliveryDTO.getCartValue() + ",\n" +
				"    \"delivery_distance\": " + deliveryDTO.getDeliveryDistance() + ",\n" +
				"    \"number_of_items\": " + deliveryDTO.getNumberOfItems() + ",\n" +
				"    \"time\": \""+deliveryDTO.getTime()+ "\"\n" +
				"}";
	}

	private DeliveryDTO createDeliveryDTO(int cartValue, int deliveryDistance, int numberOfItems, String time){
		DeliveryDTO delivery = new DeliveryDTO(cartValue,deliveryDistance,numberOfItems,time);
		return delivery;
	}

	/*@Test
	void shouldNotCreateABookingBeforeToday() throws Exception {
		String email = "book2@gmail.com";
		String department = "Arboleda2";
		CreateUserDTO user = new CreateUserDTO(email, "Armando", "12345", "Masculino", "password");
		createUser(user);
		CreatePlaceDTO place = new CreatePlaceDTO("Finca pepe", department, "Fruta2", "direccion", "finca linda", "hola.png", 3, 2, 1);
		createPlace(place, email);
		String token = loginUser(email, user.getPassword());
		String placeId = getPlaceId(department);
		BookDTO bookDTO = new BookDTO(getDate(0), getDate(4), placeId);
		MvcResult result = mockMvc.perform(post("/api/v1/books")
						.header("Authorization", token)
						.header("X-Email", email)
						.contentType(MediaType.APPLICATION_JSON)
						.content(getBookJSON(bookDTO)))
				.andExpect(status().isConflict())
				.andReturn();
		String responseBody = result.getResponse().getContentAsString();
		Assertions.assertEquals("The Booking can not be made for a date prior to today's date", responseBody);
	}

	@Test
	void shouldNotCreateABookingWithBadDates() throws Exception {
		String email = "book3@gmail.com";
		String department = "Arboleda3";
		CreateUserDTO user = new CreateUserDTO(email, "Armando", "12345", "Masculino", "password");
		createUser(user);
		CreatePlaceDTO place = new CreatePlaceDTO("Finca pepe", department, "Fruta3", "direccion", "finca linda", "hola.png", 3, 2, 1);
		createPlace(place, email);
		String token = loginUser(email, user.getPassword());
		String placeId = getPlaceId(department);
		BookDTO bookDTO = new BookDTO(getDate(4), getDate(3), placeId);
		MvcResult result = mockMvc.perform(post("/api/v1/books")
						.header("Authorization", token)
						.header("X-Email", email)
						.contentType(MediaType.APPLICATION_JSON)
						.content(getBookJSON(bookDTO)))
				.andExpect(status().isConflict())
				.andReturn();
		String responseBody = result.getResponse().getContentAsString();
		Assertions.assertEquals("The End date of the booking must be after the starting date", responseBody);
	}

	@Test
	void shouldNotCreateABookingWithANullPlaceId() throws Exception {
		String email = "book4@gmail.com";
		CreateUserDTO user = new CreateUserDTO(email, "Armando", "12345", "Masculino", "password");
		createUser(user);
		String token = loginUser(email, user.getPassword());
		BookDTO bookDTO = new BookDTO();
		bookDTO.setId("");
		bookDTO.getId();
		bookDTO.setUserId(email);
		bookDTO.setInitialDate(getDate(3));
		bookDTO.setFinalDate(getDate(5));
		MvcResult result = mockMvc.perform(post("/api/v1/books")
						.header("Authorization", token)
						.header("X-Email", email)
						.contentType(MediaType.APPLICATION_JSON)
						.content(getBookJSON(bookDTO)))
				.andExpect(status().isBadRequest())
				.andReturn();
		String responseBody = result.getResponse().getContentAsString();
		Assertions.assertEquals("The booking has to specify the place Id", responseBody);
	}

	@Test
	void shouldNotCreateABookingWithANonExistentUser() throws Exception {
		String email = "book5@gmail.com";
		String department = "Arboleda5";
		CreateUserDTO user = new CreateUserDTO(email, "Armando", "12345", "Masculino", "password");
		createUser(user);
		CreatePlaceDTO place = new CreatePlaceDTO("Finca pepe", department, "Fruta5", "direccion", "finca linda", "hola.png", 3, 2, 1);
		createPlace(place, email);
		String token = loginUser(email, user.getPassword());
		String placeId = getPlaceId(department);
		BookDTO bookDTO = new BookDTO(getDate(3), getDate(5), placeId);
		MvcResult result = mockMvc.perform(post("/api/v1/books")
						.header("Authorization", token)
						.header("X-Email", "NoExiste")
						.contentType(MediaType.APPLICATION_JSON)
						.content(getBookJSON(bookDTO)))
				.andExpect(status().isNotFound())
				.andReturn();
		String responseBody = result.getResponse().getContentAsString();
		Assertions.assertEquals("There is no user with the email address NoExiste", responseBody);
	}

	@Test
	void shouldNotCreateABookingWithANonExistentPlace() throws Exception {
		String email = "book6@gmail.com";
		String department = "Arboleda6";
		CreateUserDTO user = new CreateUserDTO(email, "Armando", "12345", "Masculino", "password");
		createUser(user);
		CreatePlaceDTO place = new CreatePlaceDTO("Finca pepe", department, "Fruta6", "direccion", "finca linda", "hola.png", 3, 2, 1);
		createPlace(place, email);
		String token = loginUser(email, user.getPassword());
		BookDTO bookDTO = new BookDTO(getDate(3), getDate(5), "1");
		MvcResult result = mockMvc.perform(post("/api/v1/books")
						.header("Authorization", token)
						.header("X-Email", email)
						.contentType(MediaType.APPLICATION_JSON)
						.content(getBookJSON(bookDTO)))
				.andExpect(status().isNotFound())
				.andReturn();
		String responseBody = result.getResponse().getContentAsString();
		Assertions.assertEquals("There is no place with the id 1", responseBody);
	}

	@Test
	void shouldNotCreateABookingWithConflictDates() throws Exception {
		String email = "book7@gmail.com";
		String department = "Arboleda7";
		CreateUserDTO user = new CreateUserDTO(email, "Armando", "12345", "Masculino", "password");
		createUser(user);
		CreatePlaceDTO place = new CreatePlaceDTO("Finca pepe", department, "Fruta7", "direccion", "finca linda", "hola.png", 3, 2, 1);
		createPlace(place, email);
		String token = loginUser(email, user.getPassword());
		String placeId = getPlaceId(department);
		BookDTO bookDTO = new BookDTO(getDate(2), getDate(4), placeId);
		mockMvc.perform(post("/api/v1/books")
						.header("Authorization", token)
						.header("X-Email", email)
						.contentType(MediaType.APPLICATION_JSON)
						.content(getBookJSON(bookDTO)))
				.andExpect(status().isCreated());
		bookDTO.setInitialDate(getDate(6));
		bookDTO.setFinalDate(getDate(8));
		mockMvc.perform(post("/api/v1/books")
						.header("Authorization", token)
						.header("X-Email", email)
						.contentType(MediaType.APPLICATION_JSON)
						.content(getBookJSON(bookDTO)))
				.andExpect(status().isCreated());
		MvcResult result = mockMvc.perform(post("/api/v1/books")
						.header("Authorization", token)
						.header("X-Email", email)
						.contentType(MediaType.APPLICATION_JSON)
						.content(getBookJSON(bookDTO)))
				.andExpect(status().isConflict())
				.andReturn();
		String responseBody = result.getResponse().getContentAsString();
		Assertions.assertEquals(EnvirifyPersistenceException.DATE_INTERVAL_ERROR, responseBody);
	}

	@Test
	void shouldDeleteABooking() throws Exception {
		String email = "arbol@gmail.com";
		String department = "Sauzalito";
		CreateUserDTO user = new CreateUserDTO(email, "Armando", "12345", "Masculino", "password");
		createUser(user);
		CreatePlaceDTO place = new CreatePlaceDTO("Finca pepe", department, "Rama", "direccion", "finca linda", "hola.png", 3, 2, 1);
		createPlace(place, email);
		String token = loginUser(email, user.getPassword());
		String placeId = getPlaceId(department);
		makeBook(placeId, token, email);
		MvcResult result = mockMvc.perform(get("/api/v1/users/" + email + "/bookings"))
				.andExpect(status().isAccepted())
				.andReturn();
		String bodyResult = result.getResponse().getContentAsString();
		JSONArray array = new JSONArray(bodyResult);
		Assertions.assertEquals(1, array.length());
		BookPlaceDTO book = gson.fromJson(array.getJSONObject(0).toString(), BookPlaceDTO.class);
		String bookId = book.getId();
		mockMvc.perform(delete("/api/v1/books/" + bookId)
						.header("Authorization", token)
						.header("X-Email", email))
				.andExpect(status().isOk());
		result = mockMvc.perform(get("/api/v1/users/" + email + "/bookings"))
				.andExpect(status().isNotFound())
				.andReturn();
		bodyResult = result.getResponse().getContentAsString();
		Assertions.assertEquals("There user with the email address " + email + " don't have bookings", bodyResult);
	}

	@Test
	void shouldNotDeleteABookingWithANonExistentUser() throws Exception {
		String email = "arbol2@gmail.com";
		CreateUserDTO user = new CreateUserDTO(email, "Armando", "12345", "Masculino", "password");
		createUser(user);
		String token = loginUser(email, user.getPassword());
		MvcResult result = mockMvc.perform(delete("/api/v1/books/1")
						.header("Authorization", token)
						.header("X-Email", "noexiste@gmail.com"))
				.andExpect(status().isNotFound())
				.andReturn();
		String bodyResult = result.getResponse().getContentAsString();
		Assertions.assertEquals("There is no user with the email address noexiste@gmail.com", bodyResult);
	}

	@Test
	void shouldNotDeleteABookingOfAUserUserWithoutBookings() throws Exception {
		String email = "arbol3@gmail.com";
		CreateUserDTO user = new CreateUserDTO(email, "Armando", "12345", "Masculino", "password");
		createUser(user);
		String token = loginUser(email, user.getPassword());
		MvcResult result = mockMvc.perform(delete("/api/v1/books/1")
						.header("Authorization", token)
						.header("X-Email", email))
				.andExpect(status().isNotFound())
				.andReturn();
		String bodyResult = result.getResponse().getContentAsString();
		Assertions.assertEquals("This user do not have bookings", bodyResult);
	}

	@Test
	void shouldNotDeleteABookingThatIsNotFromThatUser() throws Exception {
		String email = "arbol4@gmail.com";
		String department = "Sauzalito4";
		CreateUserDTO user = new CreateUserDTO(email, "Armando", "12345", "Masculino", "password");
		createUser(user);
		CreatePlaceDTO place = new CreatePlaceDTO("Finca pepe", department, "Rama4", "direccion", "finca linda", "hola.png", 3, 2, 1);
		createPlace(place, email);
		String token = loginUser(email, user.getPassword());
		String placeId = getPlaceId(department);
		makeBook(placeId, token, email);
		MvcResult result = mockMvc.perform(delete("/api/v1/books/1")
						.header("Authorization", token)
						.header("X-Email", email))
				.andExpect(status().isNotFound())
				.andReturn();
		String bodyResult = result.getResponse().getContentAsString();
		Assertions.assertEquals("This user do not have a booking with the id 1", bodyResult);
	}

	private void makeBook(String placeId, String token, String email) throws Exception {
		BookDTO bookDTO = new BookDTO(getDate(2), getDate(4), placeId);
		mockMvc.perform(post("/api/v1/books")
						.header("Authorization", token)
						.header("X-Email", email)
						.contentType(MediaType.APPLICATION_JSON)
						.content(getBookJSON(bookDTO)))
				.andExpect(status().isCreated());
	}

	private String getBookJSON(BookDTO bookDTO) {
		String placeIdLine = "null";
		if (bookDTO.getPlaceId() != null) {
			placeIdLine = "\"" + bookDTO.getPlaceId() + "\"";
		}
		return "{\"initialDate\":\"" + formatter.format(bookDTO.getInitialDate()) + "\",\n" +
				"    \"finalDate\": \"" + formatter.format(bookDTO.getFinalDate()) + "\",\n" +
				"    \"placeId\": " + placeIdLine + "\n" +
				"}";
	}

	private Date getDate(int days) {
		Date actualDate = new Date();
		return new Date(actualDate.getTime() + (days * DAY_IN_MILLISECONDS));
	}

	private String getPlaceId(String department) throws Exception {
		MvcResult result = mockMvc.perform(get("/api/v1/places?search=" + department))
				.andExpect(status().isOk())
				.andReturn();
		String bodyResult = result.getResponse().getContentAsString();
		JSONObject object = new JSONArray(bodyResult).getJSONObject(0);
		CreatePlaceDTO placeDTO = gson.fromJson(object.toString(), CreatePlaceDTO.class);
		return placeDTO.getId();
	}

	private String loginUser(String email, String password) throws Exception {
		LoginDTO loginDTO = new LoginDTO(email, password);
		MvcResult result = mockMvc.perform(post("/api/v1/users/login")
						.contentType(MediaType.APPLICATION_JSON)
						.content(gson.toJson(loginDTO)))
				.andExpect(status().isAccepted())
				.andReturn();
		String bodyResult = result.getResponse().getContentAsString();
		JwtResponse response = gson.fromJson(bodyResult, JwtResponse.class);
		return "Bearer " + response.getJwt();
	}

	private void createUser(CreateUserDTO userDTO) throws Exception {
		mockMvc.perform(post("/api/v1/users")
						.contentType(MediaType.APPLICATION_JSON)
						.content(gson.toJson(userDTO)))
				.andExpect(status().isCreated());
	}

	private void createPlace(CreatePlaceDTO placeDTO, String userEmail) throws Exception {
		mockMvc.perform(post("/api/v1/places").header("X-Email", userEmail)
						.contentType(MediaType.APPLICATION_JSON)
						.content(gson.toJson(placeDTO)))
				.andExpect(status().isCreated());
	}*/
}