package com.cpt.payments.constants;

import lombok.Getter;

public enum ErrorCodeEnum {

    GENERIC_EXCEPTION("10001","Something went wrong, please try later"),
    SIGNATURE_INVALID("10002","Bad request, signature not found"),
    SIGNATURE_EMPTY("10002","Bad request, please provide signature"),
    PAYMENT_TYPE_VALIDATION_FAILED("10012","Bad request, given paymentType parameter is not valid or empty"),
    PROVIDER_ID_VALIDATION_FAILED("10017","Bad request, given proiderId parameter is not valid or empty");

    @Getter
    private  String errorCode;
    @Getter
    private String errorMessage;

    ErrorCodeEnum(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
