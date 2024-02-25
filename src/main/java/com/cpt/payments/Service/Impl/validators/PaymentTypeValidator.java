package com.cpt.payments.Service.Impl.validators;

import com.cpt.payments.Exceptions.ValidationException;
import com.cpt.payments.Pojo.PaymentRequest;
import com.cpt.payments.Service.Impl.validators.ProviderIdValidator;
import com.cpt.payments.Service.Validator;
import com.cpt.payments.constants.ErrorCodeEnum;
import com.cpt.payments.util.LogMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class PaymentTypeValidator implements Validator {

    private static final Logger LOGGER =
            (Logger) LogManager.getLogger(PaymentTypeValidator.class);
    @Override
    public void doValidate(PaymentRequest paymentRequest) {
        LogMessage.log(LOGGER, "Validating Payment Type request for:{}" + paymentRequest);
        if (paymentRequest != null &&
                paymentRequest.getPayment() != null
                && paymentRequest.getPayment().getPaymentType() !=null){
            String paymentType = paymentRequest.getPayment().getPaymentType().trim();
            if (paymentType.equalsIgnoreCase("SALE")) {
                //Request is Valid
                LOGGER.info("PaymentType is Valid");
                return;
            }else {
                LOGGER.info("Payment PaymentType is not SALE paymentType:{}", paymentType);
            }
            LOGGER.info("Payment paymentType is INVALID ,throwing Exception");
            throw new ValidationException(HttpStatus.BAD_REQUEST,
                    ErrorCodeEnum.PAYMENT_TYPE_VALIDATION_FAILED.getErrorCode(),
                    ErrorCodeEnum.PAYMENT_TYPE_VALIDATION_FAILED.getErrorMessage()
            );
        }
    }
}
