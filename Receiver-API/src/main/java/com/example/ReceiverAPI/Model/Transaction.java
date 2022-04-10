package com.example.ReceiverAPI.Model;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Getter
@Setter
public class Transaction implements Serializable {

    private long accountNumber;
    private String type;
    private long amount;
    private String currency;
    private long accountFrom;

    public Transaction() {
        super();
    }

    public Transaction(long accountNumber, String type, long amount, String currency, long accountFrom) {
        this.accountNumber = accountNumber;
        this.type = type;
        this.amount = amount;
        this.currency = currency;
        this.accountFrom = accountFrom;
    }
}
