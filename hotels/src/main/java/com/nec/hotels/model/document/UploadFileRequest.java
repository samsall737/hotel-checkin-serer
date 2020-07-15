package com.nec.hotels.model.document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UploadFileRequest {
	
	@JsonProperty("file_name")
    private String fileName;
	
	@JsonProperty("file_download_url")
    private String fileDownloadUrl;
	
	@JsonProperty("file_type")
    private String fileType;
    private long size;

    public UploadFileRequest(String fileName, String fileDownloadUrl, String fileType, long size) {
        this.fileName = fileName;
        this.fileDownloadUrl = fileDownloadUrl;
        this.fileType = fileType;
        this.size = size;
    }

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileDownloadUrl() {
		return fileDownloadUrl;
	}

	public void setFileDownloadUrl(String fileDownloadUrl) {
		this.fileDownloadUrl = fileDownloadUrl;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

    
}
