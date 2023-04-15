package com.skypro.bills.exceptions;

public class ElectricityMeterNotFoundException extends RuntimeException{
    public ElectricityMeterNotFoundException(String message) {
        super(message);
    }
}
