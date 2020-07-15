package com.nec.hotels.mapper;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.nec.hotels.entity.Booking;
import com.nec.hotels.entity.Guest;
import com.nec.hotels.model.booking.BookingDTO;
import com.nec.hotels.model.guest.AccompanyGuest;
import com.nec.hotels.model.guest.GuestFactory;
import com.nec.hotels.model.guest.KidsGuest;
import com.nec.hotels.model.guest.PrimaryGuest;
import com.nec.hotels.model.guest.SharedGuest;

public class BookingMapper {

    public static BookingDTO getBookingDTO(Booking booking){
        BookingDTO bookingDTO=new BookingDTO(booking.getId(), booking.getArrivalTime(),booking.getDepartureTime(),booking.getCheckInTime(),booking.getPreCheckinStatus(),booking.getCheckinStatus(),booking.getBookingStatus(),booking.getCreditCard(),booking.getFaceRecognitionGuestId(),booking.isPayAtDesk(),booking.getConfirmationNumber(),booking.getStepsCompleted(),
        		booking.getSpecialAmenities(),booking.getSpecialRemarks(),booking.getDetailFetched(),booking.getRoom(),booking.getRoomInclusions(),booking.getFlightDetails(), booking.getNoOfKids());
        PrimaryGuest primaryGuest=new PrimaryGuest();
        List<SharedGuest> sharedGuests=new ArrayList<>();
        List<AccompanyGuest> accompanyGuests=new ArrayList<>();
        List<KidsGuest> kidsGuests=new ArrayList<>();
        GuestFactory guestFactory=new GuestFactory();
        for (Guest guest : booking.getGuests()) {
        	if(guest.getDateOfBirth() == null) {
        		guest.setDateOfBirth(0);
        	}
            switch (guest.getType()){
                case ACCOMPANY_GUEST:
                    accompanyGuests.add((AccompanyGuest)guestFactory.getGuest(guest,guest.getType()));
                    break;
                case SHARED_GUEST:
                    sharedGuests.add((SharedGuest)guestFactory.getGuest(guest,guest.getType()));
                    break;
                case KIDS:
                    kidsGuests.add((KidsGuest)guestFactory.getGuest(guest,guest.getType()));
                    break;
                case PRIMARY:
                    primaryGuest=(PrimaryGuest)guestFactory.getGuest(guest,guest.getType());
                    break;
                default:
                    break;
            }
        }
        bookingDTO.setPrimaryGuest(primaryGuest);
        bookingDTO.setSharerGuests(sharedGuests);
        bookingDTO.setAccompanyGuests(accompanyGuests);
        bookingDTO.setKids(kidsGuests);
        return bookingDTO;
    }
}
