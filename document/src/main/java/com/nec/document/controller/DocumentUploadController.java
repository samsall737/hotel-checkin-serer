package com.nec.document.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.nec.document.gateway.DocumentGateway;
import com.nec.document.model.UploadResponse;


@RestController
@RequestMapping("/api/v1/uploads")
public class DocumentUploadController {
	
	@Autowired
	private DocumentGateway documentGateway;
	
	
	@PostMapping
	public UploadResponse upload(@RequestParam MultipartFile files,
			@RequestParam(required = false, name = "bucket_name") String bucketName,
			@RequestParam(required = false, name = "folder_name") String folderName) throws IOException{
		 return documentGateway.upload(files, bucketName, folderName);
	}

}
