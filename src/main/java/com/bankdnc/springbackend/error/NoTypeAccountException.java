package com.bankdnc.springbackend.error;

public class NoTypeAccountException extends RuntimeException{

    public NoTypeAccountException(String message) {
        super(message);
    }
}
