package com.example.SenderAPI.Service;

import com.example.SenderAPI.Model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.IOException;
import java.io.Serializable;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@Service
public class SenderService {

    @Autowired
    private RestTemplate restTemplate;

    public String insertTransaction(Transaction transaction) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, IOException, InvalidKeyException {

//        Encrypt the received Transaction object
        SecretKey key = generateKey(128);
        IvParameterSpec ivParameterSpec = generateIv();
        String algorithm = "AES/CBC/PKCS5Padding";
        SealedObject sealedObject = encryptObject(
                algorithm, (Serializable) transaction, key, ivParameterSpec);
//        Calling the receiver API with RestTemplate.
        String response = restTemplate.postForObject("http://localhost:8081/insertTransaction", sealedObject, String.class);
        return response;
    }


//    Method to encrypt the object
    public static SealedObject encryptObject(String algorithm, Serializable object,
                                             SecretKey key, IvParameterSpec iv) throws InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, IOException, NoSuchPaddingException, NoSuchAlgorithmException {

        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        SealedObject sealedObject = new SealedObject(object, cipher);
        return sealedObject;
    }


//    IV is a pseudo-random value
    public static IvParameterSpec generateIv() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }

//    Method for generating the AES key
    public static SecretKey generateKey(int n) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(n);
        SecretKey key = keyGenerator.generateKey();
        return key;
    }
}
