package com.skypro.bills.controller;

import com.skypro.bills.dto.MeterDTO;
import com.skypro.bills.model.ElectricityMeter;
import com.skypro.bills.model.Indication;

import java.time.Instant;
import java.util.Comparator;
import java.util.UUID;

import com.skypro.bills.repository.MeterRepository;
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
//TODO: Хорошо бы задокументировать АПИ :-(

public class MeterController {

//  private MeterRepository meterRepository;
private ElectricityMeterService electricityMeterService;
//  public MeterController(MeterRepository meterRepository) {
//    this.meterRepository = meterRepository;
//  }


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
