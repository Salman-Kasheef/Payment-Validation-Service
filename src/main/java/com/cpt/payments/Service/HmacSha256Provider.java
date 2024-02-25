package com.cpt.payments.Service;

public interface HmacSha256Provider {

    public  boolean isSigValid(String requestDataAsJson, String requestSignature);
}
