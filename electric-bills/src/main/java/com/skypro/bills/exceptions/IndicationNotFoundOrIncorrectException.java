package com.skypro.bills.exceptions;

public class IndicationNotFoundOrIncorrectException extends RuntimeException{
    public IndicationNotFoundOrIncorrectException(String message) {
        super(message);
    }
}
