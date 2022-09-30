package com.company.hotel_booking.service.utils;

import org.springframework.stereotype.Component;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

/**
 * Enum with method to hash user's password
 */
@Component
public class DigestUtil {
    /**
     * Method hashes the user's password
     *
     * @param password user's password
     * @return hashed password in system hexadecimal and uppercase
     */
    public String hash(String password) {
        try {
            KeySpec spec = new PBEKeySpec(password.toCharArray(), new byte[16], 20000, 160);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] bytes = factory.generateSecret(spec).getEncoded();
            BigInteger bigInteger = new BigInteger(1, bytes);
            return bigInteger.toString(16).toUpperCase();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }
}