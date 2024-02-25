package com.cpt.payments.Service.Impl;

import com.cpt.payments.Controller.PaymentsController;
import com.cpt.payments.Exceptions.ValidationException;
import com.cpt.payments.Service.HmacSha256Provider;
import com.cpt.payments.constants.ErrorCodeEnum;
import com.cpt.payments.util.HmacSha256;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class HmacSha256ProviderImpl implements HmacSha256Provider {
    private static final Logger LOGGER=
            (Logger) LogManager.getLogger(HmacSha256ProviderImpl.class);
    @Value("${payment.signatureKey}")
    private String secretKey;
    @Autowired
    private HmacSha256 hmacSha256;
    @Override
    public boolean isSigValid(String requestDataAsJson, String requestSignature) {


        if (requestSignature == null || requestSignature.trim().isEmpty()){
            throw new ValidationException(HttpStatus.BAD_REQUEST,
                    ErrorCodeEnum.SIGNATURE_EMPTY.getErrorMessage(),
                    ErrorCodeEnum.SIGNATURE_EMPTY.getErrorCode()

            );
        }

        boolean isSigValid = hmacSha256.verifyHmac(secretKey,requestDataAsJson,requestSignature);
        if (!isSigValid){
            LOGGER.warn("SIGNATURE NOT VALID");
            throw new ValidationException(HttpStatus.BAD_REQUEST,
                    ErrorCodeEnum.SIGNATURE_INVALID.getErrorMessage(),
                    ErrorCodeEnum.SIGNATURE_INVALID.getErrorCode()

            );
        }

        return true;
    }
}
