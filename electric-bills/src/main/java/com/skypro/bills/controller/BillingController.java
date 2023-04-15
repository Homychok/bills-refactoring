package com.skypro.bills.controller;

import com.skypro.bills.dto.PaymentDTO;

import com.skypro.bills.service.BillingService;
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
public class BillingController {
  private BillingService billingService;
  public BillingController(BillingService billingService) {
    this.billingService = billingService;
  }

  @PostMapping("/{serial}")
  public ResponseEntity<?> pay(@PathVariable("serial") String serialNumber,
      @RequestBody PaymentDTO paymentDTO) {
    return ResponseEntity.ok(billingService.pay(serialNumber, paymentDTO));
  }

  @GetMapping("/{serial}")
  public ResponseEntity<?> pay(@PathVariable("serial") String serialNumber) {
    return ResponseEntity.ok(billingService.pay(serialNumber));
  }

}
