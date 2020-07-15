package com.nec.hotels.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

/*
*<p>
* Trivial implementation of the {@link MultipartFile} interface to wrap a byte[] decoded
* from a BASE64 encoded String
*</p>
*/
public class Base64DecodedMultipartFile implements MultipartFile {
       
	private final byte[] imgContent;

    public Base64DecodedMultipartFile(byte[] imgContent) {
        this.imgContent = imgContent;
    }

        @Override
        public String getName() {
        return null;
    }

    @Override
    public String getOriginalFilename() {
        return null;
    }

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return imgContent == null || imgContent.length == 0;
    }

    @Override
    public long getSize() {
        return imgContent.length;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return imgContent;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(imgContent);
    }

    @Override
    public void transferTo(File dest) throws IOException { 
    	try (FileOutputStream fileOutputStream = new FileOutputStream(dest)){
    		fileOutputStream.write(imgContent);
    	}
    }
}
