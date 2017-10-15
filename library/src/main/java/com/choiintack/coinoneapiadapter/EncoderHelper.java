package com.choiintack.coinoneapiadapter;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by choiintack on 2017. 10. 15..
 */

public class EncoderHelper {

    public static String base64Encode(String payload) throws UnsupportedEncodingException {
        byte[] data = payload.getBytes("UTF-8");
        return Base64.encodeToString(data, Base64.DEFAULT).trim().replaceAll("\\s+","");
    }

    public static String signatureEncode(String secret, String message) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac sha_HMAC = Mac.getInstance("HmacSHA512");
        SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA512");
        sha_HMAC.init(secret_key);
        byte[] bytes = sha_HMAC.doFinal(message.getBytes());
        return byteArrayToHex(bytes);
    }

    public static String byteArrayToHex(byte[] a) {
        StringBuilder sb = new StringBuilder(a.length * 2);
        for(byte b: a)
            sb.append(String.format("%02x", b));
        return sb.toString();
    }

}
