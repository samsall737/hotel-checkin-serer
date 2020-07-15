package com.nec.pms.client;



import com.nec.pms.model.PMSToken;
import com.nec.pms.model.PMSTokenRequest;
import com.nec.pms.model.PMSTokenResponse;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface PMSTokenClient {
	
	@POST
	public Single<Response<PMSTokenResponse>> getToken(@Url String url,@Body PMSTokenRequest tokenRequest);
}
