package com.cpt.payments.security;

import java.io.BufferedReader;
import java.io.IOException;

import com.cpt.payments.Service.HmacSha256Provider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class HmacFilter extends OncePerRequestFilter {
    private HmacSha256Provider HmacSha256Provider;
    private static final Logger LOGGER = LogManager.getLogger(HmacFilter.class);
    public HmacFilter() {
    }

    public HmacFilter(HmacSha256Provider hmacSha256Provider) {
        this.HmacSha256Provider = hmacSha256Provider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        LOGGER.info(">> in HmacFilter ");
        String requestSignature = servletRequest.getHeader("signature");
        WrappedRequest wrappedRequest = new WrappedRequest(servletRequest);
        String requestBody = wrappedRequest.getBody().replaceAll("\\s+", "");

       /* //Reads request body from HttpServlet
        StringBuilder requestBody = new StringBuilder();
        BufferedReader reader = servletRequest.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            requestBody.append(line);
        }
        reader.close();*/
        String requestData =requestBody.toString().replaceAll("\\s+", "");

        boolean isVerified = HmacSha256Provider.isSigValid
                (requestData, requestSignature);

        if (isVerified) {
            LOGGER.info(">> in HmacFilter >> signature verified and proceeding further");
              Authentication auth = new CustomAuthToken();
              SecurityContextHolder.getContext().setAuthentication(auth);
            filterChain.doFilter(wrappedRequest, servletResponse);
        }
    }
}

