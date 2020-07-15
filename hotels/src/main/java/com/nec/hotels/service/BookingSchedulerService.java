package com.nec.hotels.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import com.nec.email.client.EmailClient;
import com.nec.hotels.dao.BookingRepository;
import com.nec.hotels.dao.GuestRepository;
import com.nec.hotels.entity.Booking;
import com.nec.hotels.entity.Guest;
import com.nec.hotels.enums.CheckinStatus;
import com.nec.hotels.enums.GuestType;
import com.nec.hotels.enums.PreCheckinStatus;
import com.nec.hotels.mapper.AddressMapper;
import com.nec.hotels.utils.BookingUtils;
import com.nec.hotels.utils.DateUtils;
import com.nec.master.model.Configuration;
import com.nec.master.model.Hotel;
import com.nec.pms.model.guest.AccompanyGuest;
import com.nec.pms.model.guest.SharedGuest;
import com.nec.pms.model.guest.Timespan;
import com.nec.pms.model.reservation.PmsReservation;
import com.nec.pms.model.reservation.PmsReservationsDetails;
import com.nec.pms.utils.PhoneNumberLibUtils;


@Service
public class BookingSchedulerService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BookingService.class);
	
	public final MasterService masterService;
	public final PMSService pmsService;
	public final BookingRepository bookingRepository;
	private final EmailClient emailClient;
	private final String preCheckinUrl;
	private final String preCheckinEmailSubject;
	private final GuestRepository guestRepository;
	
	@Autowired
	public BookingSchedulerService(final GuestRepository guestRepository, final MasterService masterService, final PMSService pmsService,
			final BookingRepository bookingRepository, final EmailClient emailClient, @Value("${pre-checkin.url}") String preCheckinUrl,
			@Value("${pre-checkin.email.subject}") String preCheckinEmailSubject) {
		this.guestRepository = guestRepository;
		this.masterService = masterService;
		this.pmsService = pmsService;
		this.bookingRepository = bookingRepository;
		this.emailClient = emailClient;
		this.preCheckinUrl = preCheckinUrl;
		this.preCheckinEmailSubject = preCheckinEmailSubject;
	}
	
	/**
	 * 
	 */
	 public void fetchBookingDetails() {
	        LOGGER.info("==============fetchBookingDetails====Crone============= {}", new Date());
	        List<Hotel> hotels = masterService.getAllHotels();
	        for (Hotel hotel : hotels) {
	            fetchBookings(hotel);
	        }
	    }
	 
	

	 /**
	  * 
	  * @param hotel
	  */
	public void fetchBookings(Hotel hotel) {
		Map<String, String> contextMap = new HashMap<>();
		contextMap.put("hotel_id", hotel.getId().toString());
		MDC.setContextMap(contextMap);
		int startDays = hotel.getConfiguration().getSyncStartDays();
		int endDays = hotel.getConfiguration().getSyncEndDays();
		PmsReservationsDetails pmsReservationDetails = pmsService.getConfirmationNumbers(hotel,getLongFromLocalDateTime(LocalDate.now().minusDays(startDays).atStartOfDay()),getLongFromLocalDateTime(LocalDate.now().plusDays(endDays).atStartOfDay()),true);
		if(Objects.nonNull(pmsReservationDetails) && Objects.nonNull(pmsReservationDetails.getData())) {
        List<String> confirmationNumbers = pmsReservationDetails.getData().parallelStream().map(v->v.getConfirmationNumber()).collect(Collectors.toList());
        confirmationNumbers.parallelStream().forEach(confirmationNumber -> {
           try {
                PmsReservation pmsReservation = pmsService.getBookingDetails(hotel.getPmsBaseUrl(), confirmationNumber, hotel.getConfiguration(),hotel.getId());
                LOGGER.info("..............{}",pmsReservation.getMessage());
                if (Objects.isNull(pmsReservation) || pmsReservation.getSuccess().equals(Boolean.FALSE)) {
                    return;
                }
                Optional<Booking> existingBooking = bookingRepository.findByConfirmationNumber(confirmationNumber);
                Booking newBooking = getBookings(pmsReservation,hotel,existingBooking, confirmationNumber, pmsReservation.getData().getChildCounts());
                newBooking.setHotelId(hotel.getId());
                Iterator<Guest> iterator = newBooking.getGuests().iterator();
                List<Guest> bookingGuests = newBooking.getGuests();
                bookingGuests.stream().forEach(bookingGuest -> {
                	List<Guest> existingGuestList = guestRepository.findByPmsGuestId(bookingGuest.getPmsGuestId());
                	Guest existingGuest = null;
                	for(Guest guest : existingGuestList) {
                		if(org.apache.commons.lang3.StringUtils.equals(guest.getBookingId(), bookingGuest.getBookingId())) {
                			existingGuest = guest;
                		}
                	}
                	if(Objects.isNull(bookingGuest.getDateOfBirth())) {
                		bookingGuest.setDateOfBirth(0);
                	}
                	if(Objects.nonNull(existingGuest))
                	{
                		if(org.apache.commons.lang3.StringUtils.equals(bookingGuest.getBookingId(), existingGuest.getBookingId())) {
                			bookingGuest.setId(existingGuest.getId());
                		}
                		if(newBooking.getPreCheckinStatus() == PreCheckinStatus.SUCCESS || newBooking.getPreCheckinStatus() == PreCheckinStatus.ATTEMPTED) {
                		bookingGuest.setDocumentImageValidation(existingGuest.getDocumentImageValidation());
                		bookingGuest.setDateOfBirth(existingGuest.getDateOfBirth());
                		bookingGuest.setDocumentNameValidation(existingGuest.getDocumentNameValidation());
                		bookingGuest.setGuestDocuments(existingGuest.getGuestDocuments());
                		bookingGuest.setGuestUploads(existingGuest.getGuestUploads());
                		bookingGuest.setImageUrl(existingGuest.getImageUrl());
                		bookingGuest.setEmail(existingGuest.getEmail());
                		bookingGuest.setNationality(existingGuest.getNationality());
                		bookingGuest.setSignatureUrl(existingGuest.getSignatureUrl());
                		bookingGuest.setRegcardUrl(existingGuest.getRegcardUrl());
                		}
                	}
                });
                bookingRepository.save(newBooking);
                LOGGER.info("=====================booking saved =============={} ", newBooking.getId());
                if(Arrays.asList("RESERVED","DUEIN").contains(newBooking.getBookingStatus()) && newBooking.getPreCheckinStatus() != PreCheckinStatus.SUCCESS ) {
       			 if(!existingBooking.isPresent() || !newBooking.getBookingStatus().equals(existingBooking.get().getBookingStatus())) {
       				LOGGER.info("====================  SENDING MAIL  ============== ");
       				sendMailAndMessageToGuest(hotel, newBooking);
       			 } 
       		 }
                if(!existingBooking.isPresent()) {
                    LOGGER.info("==============fetchBookingDetails====new Booking Id============= {}", newBooking.getId());
                }
                LOGGER.info("==============fetchBookingDetails===saved============= {}", newBooking.getId());
            } catch (Exception e) {
                LOGGER.error("Error on add booking detail in db "+ confirmationNumber + " Exception : "  + e.getMessage());
            }
        });
        	}
       	}
       
	 
	 
	 /**
	  * 
	  * @param localDateTime
	  * @return
	  */
	 private long getLongFromLocalDateTime(LocalDateTime localDateTime) {
	        return localDateTime
	                .atZone(ZoneId.systemDefault())
	                .toInstant().toEpochMilli()/1000;
	    }
	 
	 /**
	  * 
	  * @param pmsReservation
	  * @param guests
	  * @param hotel
	  * @param existingBooking
	  * @param bookingId
	  * @param confirmationNumber
	  * @param noOfKids
	  * @return
	  */
	 private Booking getBookings(PmsReservation pmsReservation, 
             Hotel hotel,Optional<Booking> existingBooking, String confirmationNumber, int noOfKids ) {
		 PreCheckinStatus preCheckinStatus =PreCheckinStatus.PENDING;
		 CheckinStatus checkinStatus =CheckinStatus.PENDING;
		 String bookingId ;     
		 if(existingBooking.isPresent()) {
			 preCheckinStatus = existingBooking.get().getPreCheckinStatus();
			 checkinStatus = existingBooking.get().getCheckinStatus();
			 bookingId = existingBooking.get().getId();
		 } else {
			 bookingId = BookingUtils.generateBookingId(hotel.getHotelCode() ,confirmationNumber) ;
		 }
		 List<Guest> guests = new ArrayList<>();
		 guests.addAll(getGuestDetails(pmsReservation,existingBooking.isPresent()?existingBooking.get().getGuests():new ArrayList<>(), bookingId));
         Timespan timeSpan = pmsReservation.getData().getTimespan();
         Booking newBooking = new Booking.Builder(bookingId,
				 DateUtils.getMillisFromTimestamp(timeSpan.getArrivalDate()),
        		 DateUtils.getMillisFromTimestamp(timeSpan.getDepartureDate())
				 /*"IST"*/, preCheckinStatus, checkinStatus, guests ,
				 confirmationNumber, noOfKids, hotel.getId()).build();
		 newBooking.setBookingStatus(pmsReservation.getData().getReservationStatus());
		 if(existingBooking.isPresent()) {
			 newBooking.setFaceRecognitionGuestId(existingBooking.get().getFaceRecognitionGuestId());
			 newBooking.setCreditCard(existingBooking.get().getCreditCard());
			 newBooking.setPayAtDesk(existingBooking.get().isPayAtDesk());
			 newBooking.setStepsCompleted(existingBooking.get().getStepsCompleted());
			 newBooking.setSpecialRemarks(existingBooking.get().getSpecialRemarks());
			 newBooking.setSpecialAmenities(existingBooking.get().getSpecialAmenities());
		 }
		 newBooking.setDetailFetched(true);
		 return newBooking;
	 }
	 
	 private long getTimestampWithDateAndTime(String date , String time, String timezone) {
		long millis =  DateUtils.getMillisFromTimestamp(date);
		if(!StringUtils.isEmpty(time) && time.length() > 8) {
			millis += DateUtils.getSecFromTime(time.substring(0, 8), timezone)*1000;
		}
		return millis;
	 }

	 /**
	  * 
	  * @param hotel
	  * @param newBooking
	  */
	 private void sendMailAndMessageToGuest(Hotel hotel, Booking newBooking) {
		 LOGGER.info("=================  sending mail ================");
		 Guest primaryGuest = newBooking.getGuests().stream().filter(guest -> GuestType.PRIMARY.equals(guest.getType())).findFirst().get();
		 if(Objects.nonNull(primaryGuest.getEmail())) {
			 Map<String, String> model = new HashMap<>();
			    model.put("name", primaryGuest.getName());
			    model.put("booking_id", newBooking.getId());
				model.put("pre_checkin_link", preCheckinUrl + hotel.getId()+"/booking/");
				model.put("hotel_name", hotel.getName());
				model.put("hotel_city", hotel.getAddress().getCity());
				model.put("arrival_date",
						DateUtils.millisecondToDateWithMonthName(newBooking.getArrivalTime(), hotel.getTimezone()));
				model.put("departure_date",
						DateUtils.millisecondToDateWithMonthName(newBooking.getDepartureTime(), hotel.getTimezone()));

				Configuration hoteConfiguration = hotel.getConfiguration();
				if(Objects.nonNull(hoteConfiguration) && !StringUtils.isEmpty(hoteConfiguration.getEmailUserName())) {
					emailClient.emailConfig(model, hoteConfiguration.getEmailUserName(), hoteConfiguration.getEmailPassword(), hotel.getId().toString(), primaryGuest.getEmail(), preCheckinEmailSubject, null, null );
					return;
				}
				emailClient.emailConfig(model, primaryGuest.getEmail(), preCheckinEmailSubject);
		 }
	 }

	 /**
	  * 
	  * @param pmsReservation
	  * @param existingGuests
	  * @param bookingId
	  * @return
	  */
	 private List<Guest> getGuestDetails(PmsReservation pmsReservation,List<Guest> existingGuests, String bookingId) {
		 List<Guest> guests=new ArrayList<>();
		 Map<String,Guest> guestIdAndGuestMap=existingGuests.stream().collect(Collectors.toMap(k->k.getPmsGuestId(),v->v));
		 guests.add(getPrimaryGuest(pmsReservation,guestIdAndGuestMap,GuestType.PRIMARY, bookingId));
		 if(Objects.nonNull(pmsReservation.getData().getAccompanyGuests())){
			 guests.addAll(getAcompanyGuestDetails(pmsReservation.getData().getAccompanyGuests(),guestIdAndGuestMap,GuestType.ACCOMPANY_GUEST, bookingId));
		 }
		 if(Objects.nonNull(pmsReservation.getData().getSharedReservations())){
			 guests.addAll(getShareGuestDetails(pmsReservation.getData().getSharedReservations(),guestIdAndGuestMap,GuestType.SHARED_GUEST, bookingId));
		 }
		 return guests;
	 }

	 /**
	  * 
	  * @param secondaryGuests
	  * @param guestIdAndGuestMap
	  * @param guestType
	  * @param bookingId
	  * @return
	  */
	 private List<Guest> getShareGuestDetails(List<SharedGuest> secondaryGuests, Map<String,Guest> guestIdAndGuestMap, GuestType guestType,  String bookingId) {
		 List<Guest> guests=new ArrayList<>();
		 Guest guest;
		 for (SharedGuest accompanyGuest : secondaryGuests) {
			 guest=guestIdAndGuestMap.containsKey(accompanyGuest.getGuestId())?guestIdAndGuestMap.get(accompanyGuest.getGuestId()):new Guest();
			 guest.setFirstName(accompanyGuest.getFirstName());
			 guest.setLastName(getLastName(accompanyGuest.getMiddleName(), accompanyGuest.getLastName()));
			 guest.setType(guestType);
			 guest.setBookingId(bookingId);
			 guest.setPmsGuestId(accompanyGuest.getGuestId());
			 guests.add(guest);
		 }
		 return guests;
	 }
	 
	 /**
	  * 
	  * @param secondaryGuests
	  * @param guestIdAndGuestMap
	  * @param guestType
	  * @param bookingId
	  * @return
	  */
	 private List<Guest> getAcompanyGuestDetails(List<AccompanyGuest> secondaryGuests, Map<String,Guest> guestIdAndGuestMap, GuestType guestType, String bookingId) {
		 List<Guest> guests=new ArrayList<>();
		 Guest guest;
		 for (AccompanyGuest accompanyGuest : secondaryGuests) {
			 guest=guestIdAndGuestMap.containsKey(accompanyGuest.getGuestId())?guestIdAndGuestMap.get(accompanyGuest.getGuestId()):new Guest();
			 guest.setFirstName(accompanyGuest.getFirstName());
			 guest.setLastName(accompanyGuest.getLastName());
			 guest.setType(guestType);
			 guest.setBookingId(bookingId);
			 guest.setPmsGuestId(accompanyGuest.getGuestId());
			 guests.add(guest);
		 }
		 return guests;
	 }
	 
	 /**
	  * 
	  * @param pmsReservation
	  * @param guestIdAndGuestMap
	  * @param guestType
	  * @param bookingId
	  * @return
	  */
	 @NotNull
	    private Guest getPrimaryGuest(PmsReservation pmsReservation,Map<String,Guest> guestIdAndGuestMap,GuestType guestType, String bookingId) {
	        Guest guest=guestIdAndGuestMap.containsKey(pmsReservation.getData().getPrimaryGuest().getGuestId())?guestIdAndGuestMap.get(pmsReservation.getData().getPrimaryGuest().getGuestId()):new Guest();
	        Optional<Booking> booking= bookingRepository.findById(bookingId);
	        guest.setFirstName(pmsReservation.getData().getPrimaryGuest().getName().getFirstName());
	        guest.setLastName(getLastName(pmsReservation.getData().getPrimaryGuest().getName().getMiddleName(), pmsReservation.getData().getPrimaryGuest().getName().getLastName()));
	        guest.setTitle(pmsReservation.getData().getPrimaryGuest().getName().getNameTitle());
	        guest.setType(guestType);
	        guest.setBookingId(bookingId);
	        guest.setNationality(pmsReservation.getData().getNationality());
	        guest.setPmsGuestId(pmsReservation.getData().getPrimaryGuest().getGuestId());
	        guest.setEmail(!pmsReservation.getData().getEmails().isEmpty()
	                ? pmsReservation.getData().getEmails().get(0)
	                : null);
	        if(!pmsReservation.getData().getPhoneNumber().isEmpty()) {
	            PhoneNumber number = PhoneNumberLibUtils.getNumber(pmsReservation.getData().getPhoneNumber().get(0), pmsReservation.getData().getPrimaryGuest().getAddress().getCountryCode());
	            if(Objects.nonNull(number)) {
	                guest.setIsdCode(number.getCountryCode());
	                guest.setContactNumber(String.valueOf(number.getNationalNumber()));
	            }
	        }
	        guest.setAddress(AddressMapper.createAddress(pmsReservation.getData().getPrimaryGuest().getAddress())); 
	        return guest;
	    }
	
	 
	 private String getLastName(String middleName, String lastName) {
		StringBuilder sb = new StringBuilder();
		 if(!StringUtils.isEmpty(middleName)) {
			 sb.append(middleName).append(" ");
		 }
		 sb.append(lastName);
		 return sb.toString();
	 }



}
