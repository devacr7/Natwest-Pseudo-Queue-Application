package com.example.ReceiverAPI.Controller;


import com.example.ReceiverAPI.Service.ReceiverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

@RestController
public class ReceiverController {

    @Autowired
    ReceiverService receiverService;

    @PostMapping("/insertTransaction")
    public String insertTransaction(@RequestBody SealedObject sealedObject) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, IOException, InvalidKeyException, BadPaddingException, ClassNotFoundException, SQLException {
        return receiverService.insertTransaction(sealedObject);
    }

}
