package com.nec.hotels.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nec.hotels.dao.GuestRepository;
import com.nec.hotels.entity.Guest;

@Service
public class GuestService {
	
	private static final Logger LOGGER  = LoggerFactory.getLogger(GuestService.class);
	
	private final GuestRepository guestRepository;
	
	@Autowired
	public GuestService(final GuestRepository guestRepository) {
		this.guestRepository = guestRepository;
	}
	
	public List<Guest> getAllByIsFRROGuests(boolean isFrro) {
		LOGGER.info("==================getAllByIsFrro============== {} ", isFrro);
		return guestRepository.findAllByIsFrro(isFrro);
	}
	
	public Guest updateIsFrro(String id, Boolean isFrro) {
		LOGGER.info("==================updateIsFrro============== {} | {} ", id, isFrro);
		Guest guest = null;
		if (id != null) {
			Optional<Guest> optinalPrimaryGuest = guestRepository.findById(UUID.fromString(id));
			if (optinalPrimaryGuest.isPresent() && Objects.nonNull(optinalPrimaryGuest.get())) {
				Guest primaryGuest = optinalPrimaryGuest.get();
				primaryGuest.setFRRO(isFrro);
				guest = guestRepository.save(primaryGuest);
			} 
		}
		return guest;
	}

}
