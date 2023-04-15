package com.skypro.bills.controller;

import com.skypro.bills.dto.MeterDTO;

import com.skypro.bills.service.ElectricityMeterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/meter")

public class MeterController {

private ElectricityMeterService electricityMeterService;

  public MeterController(ElectricityMeterService electricityMeterService) {
    this.electricityMeterService = electricityMeterService;
  }

  @PostMapping
  public ResponseEntity<MeterDTO> createMeter(@RequestBody MeterDTO meterDTO) {
    return ResponseEntity.ok(electricityMeterService.createMeter(meterDTO));
  }

  @GetMapping("/{serial}")
  public ResponseEntity<MeterDTO> getMeter(@PathVariable("serial") String serialNumber) {
    return ResponseEntity.ok(electricityMeterService.getMeterBySerialNumber(serialNumber));
  }

  @PostMapping("/{serial}/{indication}")
  public ResponseEntity<?> newIndication(@PathVariable("serial") String serialNumber,
      @PathVariable("indication") int indication) {
    return ResponseEntity.ok(electricityMeterService.addIndication(serialNumber, indication));
   }
  }
