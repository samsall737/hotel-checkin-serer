package com.nec.hotels.service;

import java.text.ParseException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nec.hotels.dao.BookingRepository;
import com.nec.hotels.dao.SpecialAmenitiesRepository;
import com.nec.hotels.entity.SpecialAmenities;
import com.nec.hotels.enums.CheckinStatus;
import com.nec.hotels.enums.DocumentTypes;
import com.nec.hotels.enums.PreCheckinStatus;
import com.nec.hotels.model.AdminStats;
import com.nec.hotels.utils.Constants;
import com.nec.hotels.utils.DateUtils;
import com.nec.master.model.Hotel;
import com.nec.pms.model.packages.Package;
import com.nec.pms.model.packages.PackageList;

@Service
public class HotelService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BookingService.class);
	
	private final BookingRepository bookingRepository;
	private final SpecialAmenitiesRepository specialAmenitiesRepo;
	private final PMSService pmsService;
	
	
	@Autowired
	public HotelService(final BookingRepository bookingRepository, 
			final SpecialAmenitiesRepository specialAmenitiesRepo, final PMSService pmsService) {
		this.bookingRepository = bookingRepository;
		this.specialAmenitiesRepo = specialAmenitiesRepo;
		this.pmsService = pmsService;
	}
	
	
	/**
	 * 
	 * @param startDate
	 * @param endDate
	 * @param hotelId
	 * @return
	 */
	public AdminStats getBookingStats(Long startDate, Long endDate, UUID hotelId) {
		LOGGER.info("==================getBookingStats============== {} | {}", startDate, endDate);
		
		if (Objects.isNull(startDate) || Objects.isNull(endDate)) {
			try {
				startDate = DateUtils.getTodayStartDate();
			} catch (ParseException e) {
				LOGGER.error(e.getMessage());
			}
			endDate = startDate + Constants.MILLIS_IN_A_DAY;
		}
		AdminStats stats = new AdminStats();
		//Model move
		stats.setCheckinSuccess(
				bookingRepository.countBookingByHotelIdAndCheckinStatusAndArrivalTimeGreaterThanAndArrivalTimeLessThan(hotelId,
						CheckinStatus.SUCCESS, startDate, endDate));
		stats.setPreCheckinFailure(
				bookingRepository.countBookingByHotelIdAndPreCheckinStatusAndArrivalTimeGreaterThanAndArrivalTimeLessThan(
						hotelId, PreCheckinStatus.FAILURE, startDate, endDate));
		stats.setPreCheckinSuccess(
				bookingRepository.countBookingByHotelIdAndPreCheckinStatusAndArrivalTimeGreaterThanAndArrivalTimeLessThan(hotelId,
						PreCheckinStatus.SUCCESS, startDate, endDate));
		stats.setPreCheckinAttempted(
				bookingRepository.countBookingByHotelIdAndPreCheckinStatusAndArrivalTimeGreaterThanAndArrivalTimeLessThan(
						hotelId, PreCheckinStatus.ATTEMPTED, startDate, endDate));
		stats.setTotalGuest(bookingRepository.countBookingByArrivalTimeGreaterThanAndArrivalTimeLessThan(startDate, endDate));
		return stats;
	}
	
	/**
	 * 
	 * @return
	 */
	public DocumentTypes[] getDocumentTypes() {
		return DocumentTypes.values();
	}
	
	/**
	 * 
	 * @param ids
	 * @return
	 */
	public List<SpecialAmenities> updateAmenity(List<String> ids) {
		for (SpecialAmenities spetialAminites : specialAmenitiesRepo.findAll()) {
			spetialAminites.setActive(false);
			specialAmenitiesRepo.save(spetialAminites);
		}
		for (String id : ids) {
			Optional<SpecialAmenities> spetialAminites = specialAmenitiesRepo.findById(UUID.fromString(id));
			if (spetialAminites.isPresent()) {
				spetialAminites.get().setActive(true);
				specialAmenitiesRepo.save(spetialAminites.get());
			}
		}

		return specialAmenitiesRepo.findAll();
	}
	
	/**
	 * 
	 * @param specialAmenity
	 * @return
	 */
	public SpecialAmenities addSpecialAmenity(SpecialAmenities specialAmenity) {
		return specialAmenitiesRepo.save(specialAmenity);
	}
	
	/**
	 * 
	 * @param specialAmenityId
	 */
	public void deleteSpecialAmenities(UUID specialAmenityId) {
		Optional<SpecialAmenities> specialAmenity = specialAmenitiesRepo.findById(specialAmenityId);
		if (!specialAmenity.isPresent()) {
			return;
		}
		specialAmenity.get().setActive(true);
		specialAmenitiesRepo.save(specialAmenity.get());
	}
	
	
	/**
	 * 
	 * @param hotel
	 * @return
	 */
	public List<SpecialAmenities> getSpecialAmenities(Hotel hotel) {
		Optional<PackageList> packageLists = pmsService.getPackageList(hotel);
		if (packageLists.isPresent()) {
			if(Objects.nonNull(packageLists.get().getData().getPackages()))
			for (Package package1 : packageLists.get().getData().getPackages().getPackage()) {
				SpecialAmenities specialAmenities = new SpecialAmenities();
				SpecialAmenities amenity = specialAmenitiesRepo.findByPackageCode(package1.getAttributes().getPackageCode());
				if (Objects.nonNull(amenity)) {
					specialAmenities.setId(amenity.getId());
					specialAmenities.setPmsActive(false);
				}
				specialAmenities.setActive(Objects.nonNull(amenity) ? amenity.isActive() : false);
				specialAmenities.setAmenity(package1.getDescription().getText().getTextElement());
				specialAmenities.setRate(Integer.parseInt(package1.getPackageDetails().getAmount().getValue()));
				specialAmenities.setEndDate(
						DateUtils.getMillisFromTimestamp(package1.getPackageDetails().getEndDate().getValue()));
				specialAmenities.setPackageCode(package1.getAttributes().getPackageCode());
				specialAmenities.setStartDate(
						DateUtils.getMillisFromTimestamp(package1.getPackageDetails().getStartDate().getValue()));
				specialAmenities
						.setCurrencyCode(package1.getPackageDetails().getAmount().getAttributes().getCurrencyCode());
				specialAmenities.setHotelId(hotel.getId());
				specialAmenitiesRepo.save(specialAmenities);
			}
			handlePMSDeactivate(packageLists.get(), hotel.getHotelCode());
		}
		
		return specialAmenitiesRepo.findAll();
	}
	
	
	private void handlePMSDeactivate(PackageList packagelist, String packageCode) {
		List<SpecialAmenities> list = specialAmenitiesRepo.findAllByPackageCode(packageCode);
		boolean isDeactivateByPMS = true;
		for(SpecialAmenities specialAmenities : list) {
			for(Package package1 : packagelist.getData().getPackages().getPackage()) {
				if(specialAmenities.getPackageCode().equals(package1.getAttributes().getPackageCode())){
					isDeactivateByPMS = false;
				}
			}
			if(isDeactivateByPMS) {
				specialAmenities.setPmsActive(false);
				specialAmenities.setActive(false);
				specialAmenitiesRepo.save(specialAmenities);
			}
			
		}	
	}
	
	/**
	 * 
	 * @return
	 */
	public List<SpecialAmenities> getSpecialAmenitiesForWeb(Hotel hotel) {
		List<SpecialAmenities> list = specialAmenitiesRepo.findAllByActive(true);
		if(list != null && list.size() > 1) {
			return list;
		}
		return getSpecialAmenities(hotel);
	}
	
	
	

}
