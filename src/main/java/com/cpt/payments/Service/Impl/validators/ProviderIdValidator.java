package com.cpt.payments.Service.Impl.validators;

import com.cpt.payments.Exceptions.ValidationException;
import com.cpt.payments.Pojo.PaymentRequest;
import com.cpt.payments.Service.Validator;
import com.cpt.payments.constants.ErrorCodeEnum;

import com.cpt.payments.util.LogMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class ProviderIdValidator implements Validator {
    private static final Logger LOGGER =
            (Logger) LogManager.getLogger(ProviderIdValidator.class);
    private static final String TRUSTLY = "TRUSTLY";

    @Override
    public void doValidate(PaymentRequest paymentRequest) {

        LogMessage.log(LOGGER, "Validating Provider Id request for ->  " + paymentRequest);
        if (paymentRequest != null &&
                paymentRequest.getPayment() != null
                && paymentRequest.getPayment().getProviderId() != null)
        {
            String providerId = paymentRequest.getPayment().
                    getProviderId().trim();
            if (providerId.equalsIgnoreCase(TRUSTLY)) {
                //Request is Valid
                LOGGER.info("ProviderId Valid");
                return;

            } else {
                LOGGER.info("Payment ProviderId is not TRUSTLY providerId:{}", providerId);
            }
        } else {
            LOGGER.info("Payment ProviderId is NULL - Invalid");
        }
        LOGGER.info("Payment ProviderId is INVALID ,throwing Exception");
        throw new ValidationException(HttpStatus.BAD_REQUEST,
                ErrorCodeEnum.PROVIDER_ID_VALIDATION_FAILED.getErrorCode(),
                ErrorCodeEnum.PROVIDER_ID_VALIDATION_FAILED.getErrorMessage()
         );
    }

}

