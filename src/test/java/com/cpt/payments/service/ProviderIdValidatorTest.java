package com.cpt.payments.service;

import com.cpt.payments.Exceptions.ValidationException;
import com.cpt.payments.Pojo.Payment;
import com.cpt.payments.Pojo.PaymentRequest;
import com.cpt.payments.Pojo.User;
import com.cpt.payments.Service.Impl.validators.ProviderIdValidator;
import com.cpt.payments.constants.ErrorCodeEnum;
import com.cpt.payments.util.TestDataProviderUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ProviderIdValidatorTest {

    @InjectMocks
    ProviderIdValidator validator;
    @Test
    void testDoValidateInCorrectProviderId() {
        User user = new User();
        user.setEmail("johnpeter@gmail.com");
        user.setFirstName("john");
        user.setLastName("peter");
        user.setPhoneNumber("+919393939393");

        Payment payment = new Payment();
        payment.setAmount("18.00");
        payment.setCreditorAccount("4242424242424242");
        payment.setCurrency("EUR");
        payment.setDebitorAccount("4111111111111111");
        payment.setMerchantTransactionReference("cptraining_test8");
        payment.setPaymentMethod("APM");
        payment.setPaymentType("SALE");
        payment.setProviderId("Trustly-Temp");

        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setPayment(payment);
        paymentRequest.setUser(user);

        ValidationException returnedException =
                assertThrows(ValidationException.class,
                        () -> validator.doValidate(paymentRequest));

        assertEquals(HttpStatus.BAD_REQUEST,returnedException.getHttpStatus());

        assertEquals(ErrorCodeEnum.PROVIDER_ID_VALIDATION_FAILED.getErrorCode(),
                returnedException.getErrorCode());

        assertEquals(ErrorCodeEnum.PROVIDER_ID_VALIDATION_FAILED.getErrorMessage(),
                returnedException.getErrorMessage());
    }
    @Test
    void testDoValidateValidProviderId(){

        User user = new User();
        user.setEmail("johnpeter@gmail.com");
        user.setFirstName("john");
        user.setLastName("peter");
        user.setPhoneNumber("+919393939393");

        Payment payment = new Payment();
        payment.setAmount("18.00");
        payment.setCreditorAccount("4242424242424242");
        payment.setCurrency("EUR");
        payment.setDebitorAccount("4111111111111111");
        payment.setMerchantTransactionReference("cptraining_test8");
        payment.setPaymentMethod("APM");
        payment.setPaymentType("SALE");
        payment.setProviderId("Trustly");

        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setPayment(payment);
        paymentRequest.setUser(user);

        assertDoesNotThrow(
                () -> validator.doValidate(paymentRequest));
    }

    @Test
    void testDoValidateProviderIdCaseInsensitive(){
        PaymentRequest paymentRequest = new PaymentRequest();
        Payment payment = TestDataProviderUtil.getTestPayment();
        payment.setProviderId("TrUstly");
        User user = TestDataProviderUtil.getTestUserBean();

        paymentRequest.setPayment(payment);
        paymentRequest.setUser(user);

        assertDoesNotThrow(
                () -> validator.doValidate(paymentRequest));
    }
    @Test
    void testDoValidatePaymentRequestNull(){
        PaymentRequest paymentRequest = null;

        ValidationException returnedException =
                assertThrows(ValidationException.class,
                        () -> validator.doValidate(paymentRequest));

        assertEquals(HttpStatus.BAD_REQUEST,returnedException.getHttpStatus());

        assertEquals(ErrorCodeEnum.PROVIDER_ID_VALIDATION_FAILED.getErrorCode(),
                returnedException.getErrorCode());

        assertEquals(ErrorCodeEnum.PROVIDER_ID_VALIDATION_FAILED.getErrorMessage(),
                returnedException.getErrorMessage());
    }

    @Test
    void testDoValidatePaymentIsNull(){
        PaymentRequest paymentRequest = new PaymentRequest();

        Payment payment = null;
        User user = TestDataProviderUtil.getTestUserBean();

        paymentRequest.setPayment(payment);
        paymentRequest.setUser(user);

        ValidationException returnedException =
                assertThrows(ValidationException.class,
                        () -> validator.doValidate(paymentRequest));

        assertEquals(HttpStatus.BAD_REQUEST,
                returnedException.getHttpStatus());

        assertEquals(ErrorCodeEnum.PROVIDER_ID_VALIDATION_FAILED.getErrorCode(),
                returnedException.getErrorCode());

        assertEquals(ErrorCodeEnum.PROVIDER_ID_VALIDATION_FAILED.getErrorMessage(),
                returnedException.getErrorMessage());
    }

}
