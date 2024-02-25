package com.cpt.payments.Pojo;

import lombok.Data;

@Data
public class PaymentRequest {

    private User user;
    private Payment payment;
}
