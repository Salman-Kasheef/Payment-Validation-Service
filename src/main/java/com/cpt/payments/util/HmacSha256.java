package com.cpt.payments.util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import java.util.Base64;
@Component
public class HmacSha256 {

    private static final Logger LOGGER = (Logger) LogManager.getLogger(HmacSha256.class);
    public static final String HMAC_SHA256_ALGORITHM = "HmacSHA256";
    public String calculateHmacSha256(String data, String key)  {
        try {
            Mac hmacSha256 = Mac.getInstance(HMAC_SHA256_ALGORITHM);
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "HmacSHA256");
            hmacSha256.init(secretKeySpec);
            byte[] signatureBytes = hmacSha256.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(signatureBytes);
        }catch (Exception e){
            LOGGER.error("Exception generating Hmac Signature");
            return  null;
        }

    }

    public boolean verifyHmac(String secretKey,String data, String receivedHmac){
        try {
            String calculatedHmac = calculateHmacSha256(secretKey,data);
            LOGGER.info("calculatedHmac:{}|recievedHmac:{}",calculatedHmac,receivedHmac);
            return  calculatedHmac.equals(receivedHmac);
        }catch (Exception e){
            LOGGER.error("Exception Verifying Hmac");
            return  false;
        }
    }
}
