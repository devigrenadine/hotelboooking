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
import org.springframework.web.bind.annotation.RestController;

import com.evi.hotelbooking.exception.ResourceNotFoundException;
import com.evi.hotelbooking.model.Hotel;
import com.evi.hotelbooking.repository.HotelRepository;

import java.math.BigDecimal;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class HotelController {

	private static final Logger LOG = LoggerFactory.getLogger(HotelController.class);

	@Autowired
	HotelRepository hotelRepository;

	@GetMapping("/hotels")
	public Page<Hotel> getAllHotels(Pageable pageable) {
		return hotelRepository.findAll(pageable);
	}

	@GetMapping("/hotels/bookings/{surname}")
	public Page<Hotel> getAllHotelsBySurname(@PathVariable(value = "surname") String surname, Pageable pageable) {
		return hotelRepository.getHotels(surname, pageable);
	}
	
	@PostMapping("/hotels")
	public Hotel createHotel(@Valid @RequestBody Hotel hotel) {
		return hotelRepository.save(hotel);
	}

	@PutMapping("/hotels/{hotel_id}")
	public Hotel updateHotel(@PathVariable Long hotel_id, @Valid @RequestBody Hotel hotelRequest) {
		return hotelRepository.findById(hotel_id).map(hotel -> {
			hotel.setName(hotelRequest.getName());
			hotel.setAddress(hotelRequest.getAddress());
			hotel.setRating(hotelRequest.getRating());
			return hotelRepository.save(hotel);
		}).orElseThrow(() -> new ResourceNotFoundException("hotel_id " + hotel_id + " not found"));
	}

	@DeleteMapping("/hotels/{hotel_id}")
	public ResponseEntity<?> deleteHotel(@PathVariable Long hotel_id) {
		return hotelRepository.findById(hotel_id).map(hotel -> {
			hotelRepository.delete(hotel);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException("hotel_id " + hotel_id + " not found"));
	}

}
