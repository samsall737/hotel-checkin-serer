package com.nec.hotels.service;

import com.nec.email.client.EmailClient;
import com.nec.hotels.dao.BookingRepository;
import com.nec.hotels.dao.GuestRepository;
import com.nec.hotels.entity.Booking;
import com.nec.hotels.entity.Guest;
import com.nec.hotels.enums.GuestType;
import com.nec.hotels.model.booking.BookingDTO;
import com.nec.hotels.model.guest.PrimaryGuest;
import com.nec.hotels.utils.DateUtils;
import com.nec.master.model.Configuration;
import com.nec.master.model.Hotel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class EmailAndSmsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailAndSmsService.class);

    public final MasterService masterService;
    public final PMSService pmsService;
    public final BookingRepository bookingRepository;
    private final EmailClient emailClient;
    private final String preCheckinUrl;
    private final String preCheckinEmailSubject;
    private final GuestRepository guestRepository;

    @Autowired
    public EmailAndSmsService(final GuestRepository guestRepository, final MasterService masterService, final PMSService pmsService,
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
     * @param hotel
     * @param booking
     */
    public void sendQRCodeOnMailAndSmsToGuest(Hotel hotel, BookingDTO booking) {
        sendQRCodeMail(hotel, booking);
    }

    public void sendCheckinLinkOrOTPOnMailAndSmsToGuest(Hotel hotel, BookingDTO booking, String otp) {
        sendCheckinLink(hotel, booking);
        sendOTP(hotel, booking,otp);
    }


    private void sendQRCodeMail(Hotel hotel, BookingDTO booking) {
        LOGGER.info("=================  sending QR Code Mail ================");
        PrimaryGuest primaryGuest = booking.getPrimaryGuest();
        if(Objects.nonNull(primaryGuest.getEmail())) {
            Map<String, String> model = new HashMap<>();
            model.put("name", primaryGuest.getFirstName() + " "+primaryGuest.getLastName());
            model.put("hotel_name", hotel.getName());
            model.put("arrival_date",
                    DateUtils.millisecondToDateWithMonthName(booking.getArrivalTime(), hotel.getTimezone()));
            model.put("departure_date",
                    DateUtils.millisecondToDateWithMonthName(booking.getDepartureTime(), hotel.getTimezone()));

            Configuration hoteConfiguration = hotel.getConfiguration();
            String subject = "QR code for check-in";
            if(Objects.nonNull(hoteConfiguration) && !StringUtils.isEmpty(hoteConfiguration.getEmailUserName())) {
                emailClient.emailConfig(model, hoteConfiguration.getEmailUserName(), hoteConfiguration.getEmailPassword(), hotel.getId().toString(), primaryGuest.getEmail(), subject, "hotels\\src\\main\\resources\\qrcode\\"+booking.getId()+".png", "qrCodeForCheckinMail");
                return;
            }
            emailClient.emailConfig(model, primaryGuest.getEmail(), preCheckinEmailSubject);
        }
    }

    private void sendCheckinLink(Hotel hotel, BookingDTO booking) {
        LOGGER.info("=================  sending Checkin Link mail ================");
        PrimaryGuest primaryGuest = booking.getPrimaryGuest();
        if(Objects.nonNull(primaryGuest.getEmail())) {
            Map<String, String> model = new HashMap<>();
            model.put("name", primaryGuest.getFirstName()+" "+primaryGuest.getLastName());
            model.put("checkin_link", "https://facexpressuat.nectechnologies.in:4203/booking/"+booking.getId());
            model.put("hotel_name", hotel.getName());

            Configuration hoteConfiguration = hotel.getConfiguration();
            String subject = "Contactless Check-in";
            if(Objects.nonNull(hoteConfiguration) && !StringUtils.isEmpty(hoteConfiguration.getEmailUserName())) {
                emailClient.emailConfig(model, hoteConfiguration.getEmailUserName(), hoteConfiguration.getEmailPassword(), hotel.getId().toString(), primaryGuest.getEmail(), subject, null, "contactlessCheckinMail");
                return;
            }
            emailClient.emailConfig(model, primaryGuest.getEmail(), preCheckinEmailSubject);
        }
    }

    private void sendOTP(Hotel hotel, BookingDTO booking, String OTP) {
        LOGGER.info("=================  sending OTP mail ================");
        PrimaryGuest primaryGuest = booking.getPrimaryGuest();
        if(Objects.nonNull(primaryGuest.getEmail())) {
            Map<String, String> model = new HashMap<>();
            model.put("name", primaryGuest.getFirstName()+" "+primaryGuest.getLastName());
            model.put("hotel_name", hotel.getName());
            model.put("otp_val", OTP);

            String subject = "OTP to complete your check-in";
            Configuration hoteConfiguration = hotel.getConfiguration();
            if(Objects.nonNull(hoteConfiguration) && !StringUtils.isEmpty(hoteConfiguration.getEmailUserName())) {
                emailClient.emailConfig(model, hoteConfiguration.getEmailUserName(), hoteConfiguration.getEmailPassword(), hotel.getId().toString(), primaryGuest.getEmail(), subject, null, "otpOnMail");
                return;
            }
            emailClient.emailConfig(model, primaryGuest.getEmail(), preCheckinEmailSubject);
        }
    }

}
