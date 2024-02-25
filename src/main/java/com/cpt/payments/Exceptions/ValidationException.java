package com.cpt.payments.Exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidationException extends RuntimeException{
    /**
	 * 
	 */
	private static final long serialVersionUID = 6926739941641110573L;
	private HttpStatus httpStatus;
    private String errorCode;
    private String errorMessage;

}
