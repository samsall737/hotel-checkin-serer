package com.nec.hotels.dao;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nec.hotels.entity.Guest;

@Repository
public interface GuestRepository extends JpaRepository<Guest, UUID> {

	List<Guest> findAllByIsFrro(boolean is_frro);
	
	List<Guest> findByPmsGuestId(String guestId);
	
	// @Modifying
//	 @Query("UPDATE guest SET regcard_url = :url where booking_id= :booking_id AND guest_type= :guest_type")
	// public void updateRegcardUrl(@Param("url") String regCardUrl, @Param("booking_id") String bookingId, @Param("guest_type") GuestType guestType);
	 

}
