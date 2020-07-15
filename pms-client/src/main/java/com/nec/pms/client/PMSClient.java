package com.nec.pms.client;

import com.nec.pms.model.PMSToken;
import com.nec.pms.model.PMSTokenRequest;
import com.nec.pms.model.checkin.Checkin;
import com.nec.pms.model.checkin.CheckinResponse;
import com.nec.pms.model.checkin.PrecheckinDetailsDTO;
import com.nec.pms.model.guest.AccompanyGuest;
import com.nec.pms.model.packages.PackageList;
import com.nec.pms.model.regcard.RegCardData;
import com.nec.pms.model.reservation.PmsReservation;
import com.nec.pms.model.reservation.PmsReservationsDetails;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface PMSClient {

	@GET
	public Single<Response<PmsReservation>> getBookingDetails(@Url String url);
	
	@PATCH
	public Single<Response<PmsReservation>> updateBookingDetails(@Url String url,
				@Body PrecheckinDetailsDTO precheckinDetails);
	
	@GET
	public Single<Response<RegCardData>> getRegCard(@Url String url);
	
	@GET
	public Single<Response<PmsReservationsDetails>> getConfirmationNumbers(@Url String url ,@retrofit2.http.Query("hotelCode") String hotelCode, @retrofit2.http.Query("from") long from, @retrofit2.http.Query("to") long to ,@retrofit2.http.Query("brief") boolean brief);

	@POST
	public Single<Response<CheckinResponse>> uploadSignedRegCard(@Url String url, @Body Checkin checkin);
	
	//todo check Asma AccompanyGuest
	@POST
	public Single<Response<String>> addAcompanyGuest(@Url String url, @Body AccompanyGuest secondaryGuest);
	
	@GET
	public Single<Response<PackageList>> getPackageList(@Url String url, @retrofit2.http.Query("hotelCode") String hotelCode, @retrofit2.http.Query("chainCode") String chainCode);
	
	@POST("/tokens/v1/token/generate")
	public Single<Response<PMSToken>> getToken(@Body PMSTokenRequest tokenRequest);
}
	
