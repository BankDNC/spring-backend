package com.bankdnc.springbackend.error;

public class AccountNotFoundException extends RuntimeException{

        public AccountNotFoundException(String message) {
            super(message);
        }
}
