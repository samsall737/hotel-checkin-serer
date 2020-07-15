package com.nec.document.gateway;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.nec.document.client.DocumentClient;
import com.nec.document.model.UploadResponse;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

@Component
public class DocumentGateway {
	
	
	Logger logger= LoggerFactory.getLogger(DocumentGateway.class);
	
	@Autowired
	DocumentClient documentClient;
	
	/**
	 * Upload multipart file
	 * @param files
	 * @param bucketName
	 * @param folder
	 * @return
	 * @throws IOException
	 */
	public UploadResponse upload(MultipartFile files, String bucketName, String folder) throws IOException{
		File convertFile = convertMultiPartToFile(files);
		RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), convertFile);
        MultipartBody.Part multipartBody = MultipartBody.Part.createFormData("file",convertFile.getName(),requestFile);
        logger.info("========multipart details===={}",multipartBody.body());
        HashMap<String, String> map = new HashMap<>();
        String folderName=!StringUtils.isEmpty(folder) ? folder : "";
        map.put("bucket_name", "");
        map.put("folder_name", folderName);
        Call<UploadResponse> fileUpload=documentClient.uploads(multipartBody, map);
        Response<UploadResponse> response= fileUpload.execute();
        Files.deleteIfExists(convertFile.toPath());
        if(!response.isSuccessful()){
			throw new IOException("Image not uploaded");
		}
		return response.body();
	}
	
	/**
	 * uplaod file
	 * @param files
	 * @param bucketName
	 * @param folder
	 * @return
	 * @throws IOException
	 */
	public UploadResponse upload(File files, String bucketName, String folder) throws IOException{
		RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), files);
        MultipartBody.Part multipartBody = MultipartBody.Part.createFormData("file",files.getName(),requestFile);
        logger.info("========multipart details===={}",multipartBody.body());
        HashMap<String, String> map = new HashMap<>();
        String folderName=!StringUtils.isEmpty(folder) ? folder : "";
        map.put("bucket_name", "");
        map.put("folder_name", folderName);
        Call<UploadResponse> fileUpload=documentClient.uploads(multipartBody, map);
        Response<UploadResponse> response= fileUpload.execute();
        if(!response.isSuccessful()){
			throw new IOException("Image not uploaded");
		}
		return response.body();
	}
	
	private File convertMultiPartToFile(MultipartFile file) throws IOException {
		File convFile = new File(file.getOriginalFilename());
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(file.getBytes());
		fos.close();
		return convFile;
		}
	

}
