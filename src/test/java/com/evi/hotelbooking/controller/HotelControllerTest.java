package com.evi.hotelbooking.controller;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.evi.hotelbooking.model.Hotel;
import com.evi.hotelbooking.repository.HotelRepository;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class HotelControllerTest {

	@Autowired
	private WebApplicationContext context;

	private MockMvc mvc;

	
	@MockBean
	HotelRepository hotelRepo;

	
	@Before
	public void setUp() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
	}

	
	@Test
	public void testCreateHotel() throws Exception {
		Long id = (long) 1;
		String hotelName = "Hilton";
		String address = "NeverLand";
		Integer rating = 5;

		mvc.perform(post("/hotels").param("id", Long.toString(id)).param("name", hotelName).param("address", address)
				.param("rating", Integer.toString(rating)));
		;

		Optional<Hotel> hotel = hotelRepo.findById(id);

		Assert.assertTrue(hotel != null);
	}

	@Test
	public void testdeleteHotel() throws Exception {
		Long id = (long) 1;

		mvc.perform(delete("/hotels")
				.param("id", Long.toString(id)));;

		Optional<Hotel> hotel = hotelRepo.findById(id);

		Assert.assertTrue(hotel.isPresent() == false);
	}
}
