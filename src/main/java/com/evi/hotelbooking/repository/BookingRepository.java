package com.evi.hotelbooking.repository;


import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import org.apache.el.stream.Optional;
import org.hibernate.mapping.Map;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.evi.hotelbooking.model.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long>{

	List<Booking> findByhotel_id(Long hotel_id, Pageable pageable);
	
	@Query(value="select SUM(amount) from booking b where b.booking_hotel_id=?1 and b.currency=?2" ,nativeQuery=true)	
	BigDecimal getSumOfPriceAmountsByHotelId(Long hotel_id, String currency);

	
}

