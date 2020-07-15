package com.nec.pms.model.regcard;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonIgnoreProperties(ignoreUnknown = true)
	public class DocumentToSign {
		
	@JsonProperty("fileContents")
	private FileContents fileContents;
	
	@JsonProperty("fileContents")
	public FileContents getFileContents() {
	return fileContents;
	}
	
	@JsonProperty("fileContents")
	public void setFileContents(FileContents fileContents) {
	this.fileContents = fileContents;
	}

}
