package com.bankdnc.springbackend.error;

public class BalanceNotZeroException extends RuntimeException{

        public BalanceNotZeroException(String message) {
            super(message);
        }
}
