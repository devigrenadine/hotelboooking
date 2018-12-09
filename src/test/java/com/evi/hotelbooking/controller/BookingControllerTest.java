package com.evi.hotelbooking.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.math.BigDecimal;
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

import com.evi.hotelbooking.model.Booking;
import com.evi.hotelbooking.model.Hotel;
import com.evi.hotelbooking.repository.BookingRepository;
import com.evi.hotelbooking.repository.HotelRepository;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class BookingControllerTest {

	@Autowired
	private WebApplicationContext context;

	private MockMvc mvc;

	
	@MockBean
	BookingRepository bookRepo;

	
	@Before
	public void setUp() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
	}
	

	@Test
	public void testCreateBooking() throws Exception {
		Long id = (long) 1;
		String customerName = "Batida";
		String customerSurname = "deCoco";
		Integer numberOfPax = 6;
		double amount = 123.56;
		String currency = "Eur";

		mvc.perform(post("/hotels/1/bookings")
				.param("id", Long.toString(id))
				.param("customerName", customerName)
				.param("customerSurname", customerSurname)
				.param("numberOfPax", Integer.toString(numberOfPax))
				.param("amount", Double.toString(amount))
				.param("currency", currency));;					
						
		
		Optional<Booking> booking = bookRepo.findById(id);

		Assert.assertTrue(booking != null);
	}
	
	
	@Test
	public void testdeleteBooking() throws Exception {
		Long id = (long) 1;

		mvc.perform(delete("/hotels/1/bookings/")
				.param("id", Long.toString(id)));;

		Optional<Booking> booking = bookRepo.findById(id);

		Assert.assertTrue(booking.isPresent() == false);
	}
}