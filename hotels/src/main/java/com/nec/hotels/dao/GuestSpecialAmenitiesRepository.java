package com.nec.hotels.dao;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nec.hotels.entity.GuestSpecialAmenities;

@Repository
public interface GuestSpecialAmenitiesRepository extends JpaRepository<GuestSpecialAmenities, UUID> {


}
