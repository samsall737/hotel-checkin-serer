package com.nec.hotels.dao;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nec.hotels.entity.SpecialAmenities;

@Repository
public interface SpecialAmenitiesRepository extends JpaRepository<SpecialAmenities, UUID> {

	public List<SpecialAmenities> findAllByActive(Boolean disabled);

	public SpecialAmenities findByAmenity(String textElement);

	public SpecialAmenities findByPackageCode(String packageCode);
	
	public List<SpecialAmenities> findAllByPackageCode(String packageCode);
}
