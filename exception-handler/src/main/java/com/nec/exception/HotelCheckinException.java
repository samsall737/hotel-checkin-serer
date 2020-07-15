package com.nec.exception;

import org.springframework.http.HttpStatus;
public class HotelCheckinException extends  RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 2457189159617736175L;
	
	private final HttpStatus status;
    private final String message;
    private final String code;

	/**
     * Arg constructor.
     *
     * @param ex the {@link Throwable}
     * @param message the {@link String} message to set.
     * @param status the {@link HttpStatus} to set.
     */    
    public HotelCheckinException(final Throwable ex, final String message, final HttpStatus status) {
        super(message, ex);
        
        this.status = status;
        this.message = message;
        this.code = null;
    }
    
    /**
     * Arg constructor.
     *
     * @param ex the {@link Throwable}
     * @param message the {@link String} message to set.
     * @param message the {@link String} code to set.
     * @param status the {@link HttpStatus} to set.
     */
    public HotelCheckinException(final Throwable ex, final String message,final String code, final HttpStatus status) {
        super(message, ex);
        this.status = status;
        this.message = message;
        this.code = null;
    }

    /**
     * Arg constructor.
     *
     * @param message the {@link String} message to set.
     * @param status the {@link HttpStatus} to set.
     */
    
    public HotelCheckinException(final String message, final HttpStatus status) {
        super(message);
        this.status = status;
        this.message = message;
        this.code = null;
        
    }
    /**
     * Arg constructor.
     *
     * @param message the {@link String} message to set.
     * @param status the {@link HttpStatus} to set.
     */
    public HotelCheckinException(final String message,final String code, final HttpStatus status) {
    	super(message);
        this.status = status;
        this.message = message;
        this.code = code;
        
    }

    /**
     * Arg constructor.
     *
     * @param ex the {@link Throwable}
     * @param message the {@link String} message to set.
     * @param status the {@link Integer} to set.
     */
    public HotelCheckinException(final Throwable ex, final String message, final Integer status) {
        super(message, ex);
        final HttpStatus resolvedStatus = HttpStatus.resolve(status);
        this.status =  resolvedStatus == null ? HttpStatus.INTERNAL_SERVER_ERROR : resolvedStatus;
        this.message = message;
        this.code = null;
    }

    /**
     * Arg constructor.
     *
     * @param ex the {@link Throwable}
     * @param message the {@link String} message to set.
     */
    public HotelCheckinException(final Throwable ex, final String message) {
        super(message, ex);
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
        this.message = message;
        this.code = null;
    }

    /**
     * Arg constructor.
     *
     * @param message the {@link String} message to set.
     * @param status the {@link HttpStatus} to set.
     */
    public HotelCheckinException(final String message, final Integer status) {
        super(message);
        final HttpStatus resolvedStatus = HttpStatus.resolve(status);
        this.status =  resolvedStatus == null ? HttpStatus.INTERNAL_SERVER_ERROR : resolvedStatus;
        this.message = message;
        this.code = null;
    }

    /**
     * Arg constructor.
     *
     * @param ex the {@link Throwable}
     */
    public HotelCheckinException(final Throwable ex) {
        super(ex);
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
        this.message = ex.getMessage();
        this.code = null;
    }

    /**
     * Getter for set http-status.
     * @return the {@link HttpStatus} http-status. set.
     */
    public HttpStatus getStatus() {
        return this.status;
    }

    /**
     * Getter for message.
     *
     * @return the {@link String} message set.
     */
    @Override
    public String getMessage() {
        return this.message;
    }
    
    
    /**
     * Getter for message.
     *
     * @return the {@link String} message set.
     */
    public String getCode() {
        return this.code;
    }
    
}
