package com.nec.hotels.service;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nec.hotels.entity.Booking;
import com.nec.hotels.entity.CardDetails;
import com.nec.hotels.entity.Guest;
import com.nec.hotels.entity.GuestSpecialAmenities;
import com.nec.hotels.enums.GuestType;
import com.nec.hotels.model.booking.Room;
import com.nec.hotels.utils.DateUtils;
import com.nec.master.model.Configuration;
import com.nec.master.model.Hotel;
import com.nec.pms.gateway.PMSGateway;
import com.nec.pms.model.FlightDetails;
import com.nec.pms.model.RoomDetail;
import com.nec.pms.model.RoomRatesAndPackage;
import com.nec.pms.model.Transport;
import com.nec.pms.model.checkin.Checkin;
import com.nec.pms.model.checkin.CheckinResponse;
import com.nec.pms.model.checkin.ETA;
import com.nec.pms.model.checkin.Package;
import com.nec.pms.model.checkin.PmsGuestDTO;
import com.nec.pms.model.checkin.PrecheckinDetailsDTO;
import com.nec.pms.model.guest.PrimaryGuest;
import com.nec.pms.model.packages.PackageList;
import com.nec.pms.model.regcard.RegCardData;
import com.nec.pms.model.reservation.Data;
import com.nec.pms.model.reservation.PmsReservation;
import com.nec.pms.model.reservation.PmsReservationsDetails;


@Service
public class PMSService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PMSService.class);
	
	 public static final String REGCARD = "regcard";
	
	private final PMSGateway pmsGateway;
	
	@Autowired
	public PMSService(final PMSGateway pmsGateway) {
		this.pmsGateway = pmsGateway;
	}
	
	/**
	 * 
	 * @param pmsBaseUrl
	 * @param booking
	 * @return
	 */
	 public Optional<Booking> updateDetailsFromPms(String pmsBaseUrl, Booking booking, Configuration configuration, UUID hotelId) {
	        LOGGER.info("==============updateDetailsFromPms============= {}", booking);
	        PmsReservation pmsReservation = pmsGateway.getBookingDetails(pmsBaseUrl, booking.getConfirmationNumber(), configuration.isPmsActive(),hotelId);
	       if(Objects.isNull(pmsReservation.getData())){
	    	   return Optional.of(booking);
	       }
	        if (pmsReservation.getData().getReservationStatus().equalsIgnoreCase("CANCELED")) {
	          //  throw new HotelCheckinException("Booking Cancelled", HttpStatus.OK);
	        }
	        if (pmsReservation.getData().getPrimaryGuest() == null) {
	            return Optional.of(booking);
	        }
	        getRoomDetailsFromPMS(booking, pmsReservation);
	        getAndUpdatePackageDetailsFromPMs(booking, pmsReservation);
	        return Optional.of(booking);
	    }
	 
	 /**
	  * 
	  * @param booking
	  * @param pmsReservation
	  */
	 private void getRoomDetailsFromPMS(Booking booking, PmsReservation pmsReservation) {
	        List<Room> rooms=new ArrayList<>();
	        for (int index = 0; index < pmsReservation.getData().getRoomDetails().size(); index++) {
	            RoomDetail roomDetails = pmsReservation.getData().getRoomDetails().get(index);
	            Room room=new Room();
	            if (roomDetails.getRoomNumber() != null) {
	                room.setRoomNumber(Integer.parseInt(roomDetails.getRoomNumber()));
	            } else {
	                room.setRoomNumber(null);
	            }
	            room.setRoomStatus(roomDetails.getRoomStatus());
	            room.setRate(pmsReservation.getData().getExpectedCharges().getPackageTotal());
	            room.setType(roomDetails.getRoomType());
	            rooms.add(room);
	        }
	        booking.setRoom(rooms);
	    }
	 
	 /**
	  * 
	  * @param booking
	  * @param pmsReservation
	  */
	 private void getAndUpdatePackageDetailsFromPMs(Booking booking, PmsReservation pmsReservation) {
	        if (pmsReservation.getData().getExpectedCharges().getDailyBreakdown().get(0).getRoomRatesAndPackage()
	                .isEmpty()) {
	            for (int index = 0; index < pmsReservation.getData().getExpectedCharges().getDailyBreakdown().get(0)
	                    .getRoomRatesAndPackage().size(); index++) {
	                RoomRatesAndPackage packages = pmsReservation.getData().getExpectedCharges().getDailyBreakdown().get(0)
	                        .getRoomRatesAndPackage().get(index);
	                if (Objects.nonNull(packages.getDescription())
	                        && packages.getDescription().equals("BASE RATE")) {
	                    continue;
	                }
	                booking.getRoomInclusions().get(index)
	                        .setInclusion(packages.getDescription());
	            }
	        }
	    }
	 
	 
	 
	 /**
	  * 
	  * @param hotel
	  * @param booking
	  * @return
	  */
	  public boolean updateDetailsToPms(Hotel hotel, Booking booking) {
	        LOGGER.info("==============updateDetailsToPms============= {}", booking);
	        PrecheckinDetailsDTO precheckinDetails = new PrecheckinDetailsDTO();
	        PmsReservation pmsReservation = pmsGateway.getBookingDetails(hotel.getPmsBaseUrl(), booking.getConfirmationNumber(), hotel.getConfiguration().isPmsActive(), hotel.getId());
	        Data data = pmsReservation.getData();
	        if (booking.getCheckInTime() != null) {
	            precheckinDetails.setEta(new ETA(DateUtils.getTimefromMillis(booking.getCheckInTime())));
	        }
	        List<PmsGuestDTO> guests = getPrimaryGuestSDetails(booking, precheckinDetails, data);
	        setSecondaryGuestDetails(booking, precheckinDetails, guests);
	        setPackageDetails(booking, precheckinDetails, pmsReservation);
	        precheckinDetails.setComment(booking.getSpecialRemarks());
	        precheckinDetails.setFlightDetails(updateFlightDetails(data.getFlightDetails(), booking.getFlightDetails()));
	        ObjectMapper mapper = new ObjectMapper();
	        try {
	            LOGGER.info("PMS update: {}", mapper.writeValueAsString(precheckinDetails));
	        } catch (JsonProcessingException e) {
	            LOGGER.error("Error on map prechecking details" , e.getMessage());
	        }
	        LOGGER.info("...........{}", booking.getConfirmationNumber());
	        PmsReservation updateResponse = pmsGateway.updateBookingDetails(hotel.getPmsBaseUrl(),booking.getConfirmationNumber(),
	                precheckinDetails, hotel.getConfiguration().isPmsActive(),hotel.getId()	);
	        LOGGER.info("...........{}", updateResponse.getStatus());
	        return Objects.nonNull(updateResponse);
	    }

	  
	  @NotNull
	    private List<PmsGuestDTO> getPrimaryGuestSDetails(Booking booking, PrecheckinDetailsDTO precheckinDetails, Data data) {
	        List<PmsGuestDTO> guests = new ArrayList<>();
	        Guest primaryGuest = booking.getGuests().stream().filter(guest -> GuestType.PRIMARY.equals(guest.getType())).findAny().get();
	        PrimaryGuest pmsPrimaryGuestDeails = data.getPrimaryGuest();
	        PmsGuestDTO guest = new PmsGuestDTO();
	        if (pmsPrimaryGuestDeails != null) {
	            guest.setGuestId(pmsPrimaryGuestDeails.getGuestId());
	            if (primaryGuest.getPmsGuestId() != null
	                    && !pmsPrimaryGuestDeails.getGuestId().equals(primaryGuest.getPmsGuestId())) {
	                guest.setGuestId(primaryGuest.getPmsGuestId());
	            }
	            if (!pmsPrimaryGuestDeails.getName().getFirstName().equals(primaryGuest.getFirstName())) {
	                guest.setFirstName(primaryGuest.getFirstName());
	            }
	            if (!pmsPrimaryGuestDeails.getName().getLastName().equals(primaryGuest.getLastName())) {
	                guest.setLastName(primaryGuest.getLastName());
	            }
	            if (primaryGuest.getTitle() != null
	                    && !pmsPrimaryGuestDeails.getName().getNameTitle().equals(primaryGuest.getTitle())) {
	                guest.setNameTitle(primaryGuest.getTitle().toString());
	            }
	            if (primaryGuest.getNationality() != null && !primaryGuest.getNationality().equals(data.getNationality())) {
	                guest.setNationality(primaryGuest.getNationality());
	            }
	            guest.setEmail((primaryGuest.getEmail() != null)?primaryGuest.getEmail():null);
	            guest.setPhone("+"+primaryGuest.getIsdCode()+primaryGuest.getContactNumber());
	            if (Objects.nonNull(primaryGuest.getDateOfBirth())) {
	                guest.setDob(DateUtils.getDatefromMillis(primaryGuest.getDateOfBirth()));
	            }
	            guest.setNationality(primaryGuest.getNationality());
	            guests.add(guest);
	        }
	        return guests;
	    }
	  
	  /**
	   * 
	   * @param pmsFlightDetails
	   * @param flightDetails
	   * @return
	   */
	  private FlightDetails updateFlightDetails(FlightDetails pmsFlightDetails, com.nec.hotels.model.booking.FlightDetails flightDetails) {
	        if (Objects.nonNull(pmsFlightDetails) && Objects.nonNull(flightDetails)) {
	            Transport arrivalTransport = pmsFlightDetails.getArrivalTransport() != null ? pmsFlightDetails.getArrivalTransport() : new Transport();
	            arrivalTransport.setCarrierCode(flightDetails.getNumber());
	            arrivalTransport.setTime(DateUtils.getTimestampfromMillis(flightDetails.getDate()));
			//	arrivalTransport.setNumber(flightDetails.getNumber());
				pmsFlightDetails.setArrivalTransport(arrivalTransport);
	   
	        }
	        return pmsFlightDetails;
	    }
	  
	  /**
	   * 
	   * @param booking
	   * @param precheckinDetails
	   * @param guests
	   */
	  private void setSecondaryGuestDetails(Booking booking, PrecheckinDetailsDTO precheckinDetails, List<PmsGuestDTO> guests) {
	        List<Guest> secondaryGuests=booking.getGuests().stream().filter(secondaryGuest->!GuestType.PRIMARY.equals(secondaryGuest.getType())).collect(Collectors.toList());
	        if (!secondaryGuests.isEmpty()) {
	            for (Guest secondaryGuest : secondaryGuests) {
	                if (secondaryGuest.getId() != null) {
	                    guests.add(new PmsGuestDTO(secondaryGuest.getPmsGuestId(), null, null, secondaryGuest.getTitle().toString(),
	                            secondaryGuest.getFirstName(), null, secondaryGuest.getLastName(),
	                            secondaryGuest.getNationality(), null,
	                            DateUtils.getTimestampfromMillis(secondaryGuest.getDateOfBirth())));
	                }
	            }
	        }
	        precheckinDetails.setPmsGuestDTOS(guests);
	    }
	  
	  /**
	   * 
	   * @param booking
	   * @param precheckinDetails
	   * @param pmsReservation
	   */
	  private void setPackageDetails(Booking booking, PrecheckinDetailsDTO precheckinDetails, PmsReservation pmsReservation) {
	        List<com.nec.pms.model.checkin.Package> packages = new ArrayList<>();
	        if (booking.getSpecialAmenities() != null) {
	            for (GuestSpecialAmenities specialAmenity : booking.getSpecialAmenities()) {
	                packages.add(new Package(specialAmenity.getAmenity(), (long) specialAmenity.getRate(),
	                        0, specialAmenity.getPackageCode(),
	                        pmsReservation.getData().getTimespan().getArrivalDate(),
	                        pmsReservation.getData().getTimespan().getDepartureDate()));
	            }
	        }
	        precheckinDetails.setPackages(packages);
	    }
	  
	  /**
	   * 
	   * @param pmsUrl
	   * @param confirmationNumber
	   * @return
	   * @throws FileNotFoundException
	   * @throws IOException
	   */
	  public RegCardData getRegCard(String pmsUrl, String confirmationNumber, Configuration configuration, UUID hotelId) throws FileNotFoundException, IOException {
	        LOGGER.info("==============getRegCard============= {}", confirmationNumber);
	        return pmsGateway.getRegCard(pmsUrl, confirmationNumber, configuration.isPmsActive(),hotelId);
	    }
	  
	  /**
	   * 
	   * @param regcardUrl
	   * @param signature
	   * @param xPosition
	   * @param yPosition
	   * @return
	   * @throws IOException
	   */
	  public byte[] addSignatureToRegCardInString(String regcardUrl, String signature, int xPosition, int yPosition, String bookingId) throws IOException {
	        LOGGER.info("==============addSignatureToRegCard============= {} | {} ", regcardUrl, signature);
	        PDDocument doc = PDDocument.load(com.nec.hotels.utils.FileUtils.getFileUrlToByteArray(regcardUrl));
	        PDPage page = doc.getPage(0);
	        PDImageXObject pdImage = PDImageXObject.createFromFile(getTemporaryFileForSignature(signature, bookingId), doc);
	        PDPageContentStream contents = new PDPageContentStream(doc, page, AppendMode.PREPEND, false);
	        float pdImageHeight = 30;
	        contents.drawImage(pdImage, xPosition, yPosition, pdImage.getWidth() * (pdImageHeight / pdImage.getHeight()),
	                pdImageHeight);
	        LOGGER.info("Image inserted.....");
	        contents.close();
	        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	        doc.save(byteArrayOutputStream);
	        doc.close();
	        return byteArrayOutputStream.toByteArray();
	    }
	  
	  /**
	   * 
	   * @param signatureUrl
	   * @param bookingId
	   * @return
	   * @throws IOException
	   */
	  private String getTemporaryFileForSignature(String signatureUrl, String bookingId) throws IOException {
		   String destPath = System.getProperty("java.io.tmpdir") + "/" + bookingId+ "_signtaure.png" ;
		   byte[] byteArray = com.nec.hotels.utils.FileUtils.getFileUrlToByteArray(signatureUrl);
		   FileOutputStream  fileOutputStream = new FileOutputStream(destPath);
		   fileOutputStream.write(byteArray);
		   fileOutputStream.flush();
	       fileOutputStream.close();
		   return destPath;
	  }  
	  
	  /**
	   * 
	   * @param hotel
	   * @param confirmationNumber
	   * @param creditCard
	   * @param signedDocument
	   * @return
	   * @throws IOException
	   */
	  public CheckinResponse reservationCheckin(Hotel hotel, String confirmationNumber, CardDetails creditCard, String signedDocument)
	            throws IOException {
	        LOGGER.info("==============reservationCheckin============= {} | {} ", confirmationNumber, creditCard);
	        Checkin checkin = new Checkin();
	        if (Objects.nonNull(creditCard)) {
	            checkin.setCardNumber(creditCard.getNumber() + "");
	            checkin.setCardHolderName(creditCard.getHolderName());
	            checkin.setCardType("MC");
	            checkin.setExpirationDate(creditCard.getExpiryDate());
	            checkin.setSignedDocument(signedDocument);
	        }
	        CheckinResponse updateResponse = pmsGateway.uploadSignedRegCard(hotel.getPmsBaseUrl(), confirmationNumber,checkin, hotel.getConfiguration().isPmsActive(),hotel.getId());
	        return updateResponse;
	    }
	  
	  /**
	   * 
	   * @param hotel
	   * @return
	   */
	  public Optional<PackageList> getPackageList(Hotel hotel){
	    	return pmsGateway.getPackageList(hotel.getPmsBaseUrl(), hotel.getHotelCode(), 
	    			Objects.nonNull(hotel.getChains()) ? hotel.getChains().getChainCode() : "CHA" , hotel.getConfiguration().isPmsActive(),hotel.getId());
	    }
	  
	  /**
	   * 
	   * @param hotel
	   * @param from
	   * @param to
	   * @param brief
	   * @return
	   */
	  public PmsReservationsDetails getConfirmationNumbers(Hotel hotel,long from,long to,boolean brief ) {
		  return pmsGateway.getConfirmationNumbers(hotel.getPmsBaseUrl(), from, to, brief, hotel.getConfiguration().isPmsActive(), hotel);
	  }
	  
	  /**
	   * 
	   * @param pmsBaseUrl
	   * @param reservationId
	   * @param configuration
	   * @return
	   */
	  public PmsReservation getBookingDetails(String pmsBaseUrl, String  reservationId, Configuration configuration, UUID hotelId) {
		  return pmsGateway.getBookingDetails(pmsBaseUrl, reservationId, configuration.isPmsActive(),hotelId);
	  }



}
