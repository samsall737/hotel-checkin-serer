package com.nec.hotels.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nec.hotels.entity.Guest;
import com.nec.hotels.service.GuestService;

@RestController
@RequestMapping("/api/v1/guest")
public class GuestController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GuestController.class);
	
	private final GuestService guestService;
	
	@Autowired 
	public GuestController(final GuestService guestService) {
		this.guestService = guestService;
	}
	
	
	@GetMapping
	public List<Guest> getAllGuest(@RequestParam(required = false) Boolean isFRRO) {
		LOGGER.info("==================getAllBooking============== {}", isFRRO);
		return guestService.getAllByIsFRROGuests(isFRRO); 
	}

	
	@PatchMapping("/{id}")
	public Guest updateGuest(@PathVariable String id, @RequestParam Boolean isFrro) {
		LOGGER.info("==================updateGuest============== {} | {}", isFrro, id);
		return guestService.updateIsFrro(id, isFrro);
	}
	 
}
