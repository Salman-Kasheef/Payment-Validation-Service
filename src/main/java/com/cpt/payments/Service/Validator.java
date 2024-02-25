package com.cpt.payments.Service;

import com.cpt.payments.Pojo.PaymentRequest;

public interface Validator {
    void doValidate(PaymentRequest paymentRequest);
}
