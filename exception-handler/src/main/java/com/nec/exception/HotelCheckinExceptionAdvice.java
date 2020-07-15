package com.nec.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@ControllerAdvice
public class HotelCheckinExceptionAdvice {

	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public ResponseEntity<ExceptionResponse> handleMaxSizeException(MaxUploadSizeExceededException exc,
			HttpServletRequest request, HttpServletResponse response) {
		return ResponseEntity.badRequest()
				.body(new ExceptionResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(),
						"File Size over the Limit. File Size limit is 2MB", request.getRequestURI()));
	}

	@ExceptionHandler(HotelCheckinException.class)
	public ResponseEntity<ExceptionResponse> handleHotelCheckinException(HotelCheckinException exc,
			HttpServletRequest request, HttpServletResponse response) {
		return ResponseEntity.status(exc.getStatus()).body(new ExceptionResponse(exc.getStatus().value(),
				exc.getStatus().getReasonPhrase(), exc.getMessage(), request.getRequestURI()));
	}

	@ExceptionHandler(PMSException.class)
	public ResponseEntity<ExceptionResponse> handleHotelCheckinException(PMSException exc, HttpServletRequest request,
			HttpServletResponse response) {
		return ResponseEntity.status(exc.getStatus()).body(new ExceptionResponse(exc.getStatus().value(),
				exc.getStatus().getReasonPhrase(), exc.getMessage(), request.getRequestURI()));
	}
}