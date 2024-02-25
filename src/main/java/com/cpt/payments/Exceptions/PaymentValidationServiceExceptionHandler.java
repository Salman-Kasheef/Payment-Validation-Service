package com.cpt.payments.Exceptions;

import com.cpt.payments.Pojo.PaymentError;
import com.cpt.payments.constants.ErrorCodeEnum;
import com.cpt.payments.util.LogMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class PaymentValidationServiceExceptionHandler {

    private static final Logger LOGGER =(Logger) LogManager.getLogger(PaymentValidationServiceExceptionHandler.class);
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<PaymentError> handleValidationException(ValidationException ex) {
        LogMessage.log(LOGGER, " validation exception is -> " + ex.getErrorMessage());
        PaymentError paymentResponse = PaymentError.builder()
                .errorCode(ex.getErrorCode())
                .errorMessage(ex.getErrorMessage())
                .build();
        LogMessage.log(LOGGER, " paymentResponse is -> " + paymentResponse);
        return new ResponseEntity<>(paymentResponse, ex.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<PaymentError> handleGenericException(Exception ex){
       LogMessage.log(LOGGER,"Generic Exception is -> "+ ex.getMessage());
       LogMessage.logException(LOGGER,ex);
        PaymentError paymentError = PaymentError.builder()
                .errorCode(ErrorCodeEnum.GENERIC_EXCEPTION.getErrorCode())
                .errorMessage(ErrorCodeEnum.GENERIC_EXCEPTION.getErrorMessage())
                .build();
        LogMessage.log(LOGGER,"paymentResponse is -> "+ paymentError);
        return new ResponseEntity<>(paymentError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
