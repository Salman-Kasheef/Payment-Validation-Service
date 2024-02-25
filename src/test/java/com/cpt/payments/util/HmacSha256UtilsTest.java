package com.cpt.payments.util;

import com.cpt.payments.Pojo.Payment;
import com.cpt.payments.Pojo.PaymentRequest;
import com.cpt.payments.Pojo.User;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class HmacSha256UtilsTest {

    private static final Logger LOGGER = (Logger) LogManager.getLogger(HmacSha256UtilsTest.class);
    @InjectMocks
    HmacSha256 hmacSha256;
    @Test
    void testCalculateHmacSuccess(){
        //Arrange data
        String secretKey = "ecom-123qwe!@#";

        PaymentRequest paymentRequest = new PaymentRequest();

        User user = TestDataProviderUtil.getTestUserBean();
        Payment payment = TestDataProviderUtil.getTestPayment();

        paymentRequest.setPayment(payment);
        paymentRequest.setUser(user);

        Gson gson = new Gson();
        String requestData = gson.toJson(paymentRequest);

        //Invoke the method
        String generatedSignature = hmacSha256.calculateHmacSha256(secretKey,requestData);

        LOGGER.info("generatedSignature:{}",generatedSignature);

        //verify what to expect
        assertNotNull(generatedSignature);
        assertNotEquals("",generatedSignature);

    }

    @Test
    void testVerifyHmac(){
        //Arrange data
        String secretKey = "ecom-123qwe!@#";

        PaymentRequest paymentRequest = new PaymentRequest();

        User user = TestDataProviderUtil.getTestUserBean();
        Payment payment = TestDataProviderUtil.getTestPayment();

        paymentRequest.setPayment(payment);
        paymentRequest.setUser(user);

        Gson gson = new Gson();
        String requestData = gson.toJson(paymentRequest);

        //Invoke the method
        String receivedHmac = "1/9peX0RHJLCBIDTpDCRzJiQtA6jdzHpXh3RQWLSA8c=";
        boolean isSigValid = hmacSha256.verifyHmac(secretKey,requestData, receivedHmac);

        LOGGER.info("generatedSignature:{}",isSigValid);

        //verify what to expect
        assertTrue(isSigValid);

    }
}
