package com.bankdnc.springbackend.error;

public class NoBalanceException extends RuntimeException{

    public NoBalanceException(String message) {
        super(message);
    }
}
