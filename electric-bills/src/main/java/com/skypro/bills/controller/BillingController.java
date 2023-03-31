package com.skypro.bills.controller;

import com.skypro.bills.dto.PaymentDTO;

import com.skypro.bills.service.ElectricityMeterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/billing")
//TODO: Хорошо бы задокументировать АПИ :-(

public class BillingController {

  //TODO: Стоит сделать это свойство конфигурируемым через application.properties
private ElectricityMeterService electricityMeterService;

  public BillingController(ElectricityMeterService electricityMeterService) {
    this.electricityMeterService = electricityMeterService;
  }

  @PostMapping("/{serial}")
  public ResponseEntity<?> pay(@PathVariable("serial") String serialNumber,
      @RequestBody PaymentDTO paymentDTO) {
    return ResponseEntity.ok(electricityMeterService.pay(serialNumber, paymentDTO));
  }

  @GetMapping("/{serial}")
  public ResponseEntity<?> pay(@PathVariable("serial") String serialNumber) {
    return ResponseEntity.ok(electricityMeterService.pay(serialNumber));
  }
}
