package com.cpt.payments.Service;

import com.cpt.payments.Pojo.PaymentRequest;
import com.cpt.payments.Pojo.PaymentResponse;

public interface PaymentService {

    PaymentResponse validateAndInitiatePayment(PaymentRequest paymentRequest);
}
