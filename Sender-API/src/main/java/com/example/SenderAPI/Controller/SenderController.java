package com.example.SenderAPI.Controller;


import com.example.SenderAPI.Model.Transaction;
import com.example.SenderAPI.Service.SenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
public class SenderController {

    @Autowired
    SenderService senderService;

    @PostMapping("/insertTransaction")
    public String insertTransaction(@RequestBody Transaction transaction) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, IOException, InvalidKeyException {
        return senderService.insertTransaction(transaction);
    }
}
