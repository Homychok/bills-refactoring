package com.skypro.bills.handler;

import com.skypro.bills.exceptions.ElectricityMeterNotFoundException;
import com.skypro.bills.exceptions.IndicationNotFoundOrIncorrectException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionControllerAdvice {
    @ExceptionHandler(IndicationNotFoundOrIncorrectException.class)
    public ResponseEntity<?> badRequest() {
        return ResponseEntity.status(400).body("Ошибка данных");
    }
    @ExceptionHandler(ElectricityMeterNotFoundException.class)
    public ResponseEntity<?> notFounded() {
        return ResponseEntity.status(404).body("Cчётчик не найден");
    }
}
