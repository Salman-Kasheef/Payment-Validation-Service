package com.cpt.payments.constants;

import com.cpt.payments.Service.Impl.validators.PaymentTypeValidator;
import com.cpt.payments.Service.Impl.validators.ProviderIdValidator;
import com.cpt.payments.Service.Validator;
import lombok.Getter;

public enum ValidatorEnum {

    PROVIDER_ID_FILTER("PROVIDER_ID_FILTER", ProviderIdValidator.class),
    PAYMENT_TYPE_FILTER("PAYMENT_TYPE_FILTER", PaymentTypeValidator.class);

    @Getter
    private String validatorName;
    @Getter
    private Class<? extends Validator> validatorClass;

    private ValidatorEnum(String validatorName, Class<? extends Validator> validatorClass) {
        this.validatorName = validatorName;
        this.validatorClass = validatorClass;
    }

    public static ValidatorEnum getEnumByString(String name) {
        for (ValidatorEnum e : ValidatorEnum.values()) {
            if (name.equals(e.validatorName))
                return e;
        }
        return null;
    }

}
