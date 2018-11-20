package com.service;

import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

class HashGenerator {

    private static final String ALGORITHM = "SHA-1";

    private HashGenerator() {
    }

    static String invoke(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance(ALGORITHM);
            digest.reset();
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            return DatatypeConverter.printHexBinary(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Error while creating hash.", e);
        }
    }
}
