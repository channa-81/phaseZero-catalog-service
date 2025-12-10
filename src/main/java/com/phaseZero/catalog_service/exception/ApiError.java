package com.phaseZero.catalog_service.exception;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;


public class ApiError {
	 private LocalDateTime timestamp;
	    private int status;
	    private String error;
	    private String message;
	    private List<String> errors;

	    public ApiError() {
	        this.timestamp = LocalDateTime.now();
	    }

	    public ApiError(HttpStatus status, String message, List<String> errors) {
	        this();
	        this.status = status.value();
	        this.error = status.getReasonPhrase();
	        this.message = message;
	        this.errors = errors;
	    }

	    public LocalDateTime getTimestamp() {
	        return timestamp;
	    }

	    public int getStatus() {
	        return status;
	    }

	    public String getError() {
	        return error;
	    }

	    public String getMessage() {
	        return message;
	    }

	    public List<String> getErrors() {
	        return errors;
	    }
}
