package com.cpt.payments;

import com.cpt.payments.Exceptions.ValidationException;
import com.cpt.payments.Pojo.Payment;
import com.cpt.payments.Pojo.PaymentRequest;
import com.cpt.payments.Pojo.User;
import com.cpt.payments.Service.Impl.validators.ProviderIdValidator;
import com.cpt.payments.constants.ErrorCodeEnum;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class PaymentValidationServiceApplicationTests {

	@Test
	void contextLoads() {
	}

}