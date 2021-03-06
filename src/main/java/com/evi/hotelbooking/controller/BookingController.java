package com.evi.hotelbooking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.evi.hotelbooking.exception.ResourceNotFoundException;
import com.evi.hotelbooking.model.Booking;
import com.evi.hotelbooking.model.Hotel;
import com.evi.hotelbooking.repository.BookingRepository;
import com.evi.hotelbooking.repository.HotelRepository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class BookingController {

	private static final Logger LOG = LoggerFactory.getLogger(BookingController.class);

	private static final BigDecimal conversionRateUSDtoEUR = new BigDecimal(0.88);
	private static final BigDecimal conversionRateGBPtoEUR = new BigDecimal(0.88);

	@Autowired
	private BookingRepository bookingRepository;

	@Autowired
	private HotelController hotelController;

	@Autowired
	private HotelRepository hotelRepository;

	@GetMapping("/hotels/{hotel_id}/bookings")
	public List<Booking> getAllBookingsByHotelId(@PathVariable(value = "hotel_id") Long hotel_id, Pageable pageable) {
		return bookingRepository.findByhotel_id(hotel_id, pageable);
	}

	@GetMapping("/hotels/{hotel_id}/bookings/sum/")
	public BigDecimal getSumPriceAmountsByHotel(@PathVariable Long hotel_id, Pageable pageable) {
		List<Booking> bookings = getAllBookingsByHotelId(hotel_id, pageable);
		BigDecimal sumAmount, euSum = new BigDecimal(0);
		int usdSum = 0;
		
		for (Booking booking : bookings) {
			if (booking.getCurrency().equals("USD")) {
				Money convertedToEUR = booking.getMoney().convertedTo(CurrencyUnit.EUR, conversionRateUSDtoEUR,
						RoundingMode.HALF_UP);
				usdSum = usdSum + convertedToEUR.getAmountMajorInt();
				
			} else {
				euSum = booking.getAmount().add(euSum);
			}
		}
		sumAmount = new BigDecimal(usdSum).add(euSum);
		return sumAmount;
	}

	@PostMapping(value = "/hotels/{hotel_id}/bookings")
	public Optional<Object> createBooking(@PathVariable(value = "hotel_id") Long hotel_id,
			@Valid @RequestBody Booking booking) {
		if (!hotelRepository.existsById(hotel_id)) {
			Hotel newhotel = new Hotel();
			newhotel.setId(hotel_id);
			hotelRepository.save(newhotel);
		}
		{
			return hotelRepository.findById(hotel_id).map(hotel -> {
				booking.setHotel(hotel);
				return bookingRepository.save(booking);
			});
		}
	}

	@PutMapping("/hotels/{hotel_id}/bookings/{booking_id}")
	public Booking updateBooking(@PathVariable(value = "hotel_id") Long hotel_id,
			@PathVariable(value = "booking_id") Long booking_id, @Valid @RequestBody Booking bookingRequest) {
		if (!hotelRepository.existsById(hotel_id)) {
			throw new ResourceNotFoundException("hotel_id " + hotel_id + " not found");
		}
		return bookingRepository.findById(booking_id).map(booking -> {
			booking.setCustomerName(bookingRequest.getCustomerName());
			booking.setCustomerSurname(bookingRequest.getCustomerSurname());
			booking.setNumberOfPax(bookingRequest.getNumberOfPax());
			return bookingRepository.save(booking);
		}).orElseThrow(() -> new ResourceNotFoundException("booking_id " + booking_id + "not found"));
	}

	@DeleteMapping("/hotels/{hotel_id}/bookings/{booking_id}")
	public ResponseEntity<?> deleteBooking(@PathVariable(value = "hotel_id") Long hotel_id,
			@PathVariable(value = "booking_id") Long booking_id) {
		if (!hotelRepository.existsById(hotel_id)) {
			throw new ResourceNotFoundException("hotel_id " + hotel_id + " not found");
		}

		return bookingRepository.findById(booking_id).map(booking -> {
			bookingRepository.delete(booking);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException("BookingId " + booking_id + " not found"));
	}
}
