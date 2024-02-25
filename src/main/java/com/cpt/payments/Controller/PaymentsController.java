package com.cpt.payments.Controller;
import com.cpt.payments.Exceptions.ValidationException;
import com.cpt.payments.Pojo.PaymentRequest;
import com.cpt.payments.Pojo.PaymentResponse;
import com.cpt.payments.Service.HmacSha256Provider;
import com.cpt.payments.Service.PaymentService;
import com.cpt.payments.constants.Endpoint;
import com.cpt.payments.constants.ErrorCodeEnum;
import com.cpt.payments.util.HmacSha256;
import com.cpt.payments.util.LogMessage;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Endpoint.VALIDATION_MAPPING)
public class PaymentsController {

    private static final Logger LOGGER=
            (Logger) LogManager.getLogger(PaymentsController.class);
    @Autowired
    private PaymentService paymentService;

    @Autowired
    private HmacSha256Provider HmacSha256Provider;

    @PostMapping(value=Endpoint.INITIATE_PAYMENT)
    public ResponseEntity<PaymentResponse> sale(@RequestBody PaymentRequest paymentRequest){
                                              //  @RequestHeader("signature") String requestSignature){

        LOGGER.info("Initiate paymentRequest:{}"
                ,paymentRequest);
        LogMessage.setLogMessagePrefix("/INITIATE_PAYMENT");

        LOGGER.warn("SIGNATURE VALID, continue processing Payment");
        return new ResponseEntity<>
                (paymentService.validateAndInitiatePayment(paymentRequest), HttpStatus.CREATED);
    }

}
