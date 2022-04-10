package com.example.ReceiverAPI.Service;


import com.example.ReceiverAPI.Model.Transaction;
import com.example.ReceiverAPI.Repository.DB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.IOException;
import java.io.Serializable;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.SQLException;

@Service
public class ReceiverService {


    @Autowired
    DB db;
    public String insertTransaction(SealedObject sealedObject) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, ClassNotFoundException, InvalidAlgorithmParameterException, InvalidKeyException, SQLException {
//        Decrypt the received Transaction object
        SecretKey key = generateKey(128);
        IvParameterSpec ivParameterSpec = generateIv();
        String algorithm = "AES/CBC/PKCS5Padding";
        Transaction transaction = (Transaction) decryptObject(
                algorithm, sealedObject, key, ivParameterSpec);
        return db.insertTransaction(transaction);
    }

//  Method to decrypt the object.
    public static Serializable decryptObject(String algorithm, SealedObject sealedObject,
                                             SecretKey key, IvParameterSpec iv) throws NoSuchPaddingException,
            NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException,
            ClassNotFoundException, BadPaddingException, IllegalBlockSizeException,
            IOException {

        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        Serializable unsealObject = (Serializable) sealedObject.getObject(cipher);
        return unsealObject;
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
