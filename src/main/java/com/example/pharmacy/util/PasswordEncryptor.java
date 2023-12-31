package com.example.pharmacy.util;

import com.example.pharmacy.exception.ServiceException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordEncryptor {
    private PasswordEncryptor(){}

    public static final String ALGORITHM = "MD5";

    public static String encryptPassword(String password) throws ServiceException {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance(ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new ServiceException("Password encryption failed.", e);
        }
        md.update(password.getBytes());
        byte[] digest = md.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
