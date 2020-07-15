package com.nec.document.client;

import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.Call;
import okhttp3.MultipartBody;

import java.util.HashMap;

import com.nec.document.model.UploadResponse;

public interface DocumentClient {
	
	@Multipart
	@POST("/api/v1/uploads")
	Call<UploadResponse> uploads(@Part MultipartBody.Part file, @QueryMap HashMap<String, String> queryMap);

}
