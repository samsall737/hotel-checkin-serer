package com.nec.hotels.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nec.hotels.entity.Booking;
import com.nec.hotels.enums.CheckinStatus;
import com.nec.hotels.enums.PreCheckinStatus;


@Repository
public interface BookingRepository extends JpaRepository<Booking, String> {

	 List<Optional<Booking>> findAllBookingByArrivalTimeGreaterThanAndArrivalTimeLessThanOrderByArrivalTimeAsc(
			long startDate, long endDate, Pageable pageable);
	 
	 List<Optional<Booking>> findAllBookingByHotelIdAndArrivalTimeGreaterThanAndArrivalTimeLessThanOrderByArrivalTimeAsc(
				String hotelId, long startDate, long endDate, Pageable pageable);
	 
	 List<Optional<Booking>> findAllBookingByArrivalTimeGreaterThanAndArrivalTimeLessThanOrderByArrivalTimeDesc(
				long startDate, long endDate, Pageable pageable);
		 
	 List<Optional<Booking>> findAllBookingByHotelIdAndArrivalTimeGreaterThanAndArrivalTimeLessThanOrderByArrivalTimeDesc(
					String hotelId, long startDate, long endDate, Pageable pageable);

	 int countBookingByHotelIdAndPreCheckinStatusAndArrivalTimeGreaterThanAndArrivalTimeLessThan(UUID hoteId, PreCheckinStatus failure,
																							 Long startDate, Long endDate);
	 Integer countBookingByArrivalTimeGreaterThanAndArrivalTimeLessThan(Long startDate, Long endDate);

	 List<Booking> findAllBookingByArrivalTimeGreaterThanAndArrivalTimeLessThan(long startDate, long endDate);

	 Integer countBookingByHotelIdAndCheckinStatusAndArrivalTimeGreaterThanAndArrivalTimeLessThan(UUID hotelId,CheckinStatus success,
																							  Long startDate, Long endDate);

	Optional<Booking> findByConfirmationNumber(String confirmationNumber);
}
