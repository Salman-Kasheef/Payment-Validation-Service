package com.cpt.payments.Service.Impl;

import com.cpt.payments.Pojo.PaymentRequest;
import com.cpt.payments.Pojo.PaymentResponse;
import com.cpt.payments.Service.PaymentService;
import com.cpt.payments.Service.Supplier;
import com.cpt.payments.Service.Validator;
import com.cpt.payments.constants.ValidatorEnum;
import com.cpt.payments.util.LogMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Value("${payment.validators}")
    private String validators;

    private static final Logger LOGGER =
            (Logger) LogManager.getLogger(PaymentServiceImpl.class);
    @Autowired
    private ApplicationContext context;
    @Override
    public PaymentResponse validateAndInitiatePayment(PaymentRequest paymentRequest) {

        LogMessage.log(LOGGER,"validators are -> "+validators);
        List<String> validatorList =
                Stream.of(validators.split(","))
                        .collect(Collectors.toList());
       validatorList.forEach(validator -> {
           ValidatorEnum validatorEnum =
                   ValidatorEnum.getEnumByString(validator);
           Supplier<? extends Validator> validatorSupplier =
                   () -> context.getBean(validatorEnum.getValidatorClass());
           validatorSupplier.get().doValidate(paymentRequest);
       });

       PaymentResponse paymentResponse = new PaymentResponse();
       paymentResponse.setPaymentReference("payment-reference");
       paymentResponse.setRedirectUrl("Trustly-url");
        return paymentResponse;

    }
}
