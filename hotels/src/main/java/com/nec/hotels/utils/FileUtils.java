package com.nec.hotels.utils;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import com.itextpdf.io.source.ByteArrayOutputStream;

public class FileUtils {
	
	@SuppressWarnings("resource")
	public static byte[] getFileUrlToByteArray(String fileUrl) throws IOException {
		URL url = new URL(fileUrl);
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		URLConnection conn = url.openConnection();

		try (java.io.InputStream inputStream = conn.getInputStream()) {
		  int n = 0;
		  byte[] buffer = new byte[1024];
		  while (-1 != (n = inputStream.read(buffer))) {
		    output.write(buffer, 0, n);
		  }
		}
		return output.toByteArray();
	}

}
