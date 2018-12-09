package com.evi.hotelbooking.repository;

import java.math.BigDecimal;
import java.util.Optional;

import org.hibernate.mapping.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.evi.hotelbooking.model.Hotel;

@Repository
public interface HotelRepository extends JpaRepository<Hotel,Long> {

	@Query(value="select * from hotel left join booking on hotel.id = booking.booking_hotel_id where customer_surname=?1" ,nativeQuery=true)
	Page<Hotel> getHotels(String surname, Pageable pageable);

	
}
