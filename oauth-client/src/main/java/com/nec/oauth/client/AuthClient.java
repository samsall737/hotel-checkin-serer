package com.nec.oauth.client;


import com.nec.oauth.model.Token;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthClient {

	@POST("/api/v1/tokens/access/decode")
	Single<Response<Token>> verifyAndDecodeAccessToken(@Body Token token);

	@POST("/api/v1/tokens/access")
	Single<Response<Token>> generateAccessToken(@Body Token token);

	@POST("/api/v1/tokens")
	Single<Response<Token>> generateAuthToken(@Body Token token);

	@POST("/api/v1/tokens/verify")
	Single<Response<Token>> verifyAndDecodeAuthToken(@Body Token token);
}
