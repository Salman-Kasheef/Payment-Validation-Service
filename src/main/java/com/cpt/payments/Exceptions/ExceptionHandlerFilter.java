package com.cpt.payments.Exceptions;

import java.io.IOException;

import com.cpt.payments.Pojo.PaymentError;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;
import com.cpt.payments.constants.ErrorCodeEnum;

import com.google.gson.Gson;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
public class ExceptionHandlerFilter extends OncePerRequestFilter {
    private static final Logger LOGGER = LogManager.getLogger(ExceptionHandlerFilter.class);
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            LOGGER.info(" ExceptionHandlerFilter Before doFilter");
            filterChain.doFilter(request, response);
            LOGGER.info(" ExceptionHandlerFilter After doFilter");
        } catch (ValidationException ex) {
            LOGGER.info(" validation exception is -> " + ex.getErrorMessage());
            PaymentError paymentResponse = PaymentError.builder().errorCode(ex.getErrorCode())
                    .errorMessage(ex.getErrorMessage()).build();
            LOGGER.info(" paymentResponse is -> " + paymentResponse);
            Gson gson = new Gson();
            response.setStatus(ex.getHttpStatus().value());
            response.setContentType("application/json");
            response.getWriter().write(gson.toJson(paymentResponse));
            response.getWriter().flush();
        } catch (Exception ex) {
            LOGGER.info(" generic exception message is -> " + ex.getMessage());
            PaymentError paymentResponse = PaymentError.builder()
                    .errorCode(ErrorCodeEnum.GENERIC_EXCEPTION.getErrorCode())
                    .errorMessage(ErrorCodeEnum.GENERIC_EXCEPTION.getErrorMessage()).build();
            LOGGER.info(" paymentResponse is -> " + paymentResponse);
            Gson gson = new Gson();
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setContentType("application/json");
            response.getWriter().write(gson.toJson(paymentResponse));
            response.getWriter().flush();
        }
    }
}

