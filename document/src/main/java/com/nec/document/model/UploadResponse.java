package com.nec.document.model;

public class UploadResponse {

	private String fileName;
	
	private String fileType;
	
	private String fileDownloadUri;
	
	private long size;

	public UploadResponse()
	{
		
	}

	public UploadResponse(String fileName, String fileType, String fileDownloadUri, long size) {
		
		this.fileName = fileName;
		this.fileType = fileType;
		this.fileDownloadUri = fileDownloadUri;
		this.size = size;
	}

	public String getFileDownloadUri() {
		return fileDownloadUri;
	}

	public void setFileDownloadUri(String fileDownloadUri) {
		this.fileDownloadUri = fileDownloadUri;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
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
