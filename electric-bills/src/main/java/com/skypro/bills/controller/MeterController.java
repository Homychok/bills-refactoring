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
    /*
    MeterDTO meterDTO1 = new MeterDTO();
    ElectricityMeter meter = new ElectricityMeter();
    meter.setSerialNumber(meterDTO.getSerialNumber());
    meter = meterRepository.save(meter);
    meterDTO1.setSerialNumber(meter.getSerialNumber());
    return meterDTO1;
         */
    return ResponseEntity.ok(electricityMeterService.createMeter(meterDTO));
  }

  @GetMapping("/{serial}")
  public ResponseEntity<MeterDTO> getMeter(@PathVariable("serial") String serialNumber) {
    /*
    MeterDTO meterDTO = new MeterDTO();
    meterDTO.setSerialNumber(meterRepository.findById(serialNumber).get().getSerialNumber());
    meterDTO.setLastIndication(
        meterRepository.findById(serialNumber).get().getIndications().stream()
            .max(Comparator.comparing(Indication::getSendingDate))
            .orElse(new Indication()).getIndication());
    return meterDTO;
     */
    return ResponseEntity.ok(electricityMeterService.getMeterBySerialNumber(serialNumber));
  }

  @PostMapping("/{serial}/{indication}")
  public ResponseEntity<?> newIndication(@PathVariable("serial") String serialNumber,
      @PathVariable("indication") int indication) {
    return ResponseEntity.ok(electricityMeterService.addIndication(serialNumber, indication));
// //   Indication lastIndication = meterRepository.findById(serial).get().getIndications().stream()
//    Indication lastIndication = meterService.findById(serial).get().getIndications().stream()
//        .max(Comparator.comparing(Indication::getSendingDate)).orElse(new Indication());
//    if (indication < 0){
//      return ResponseEntity.badRequest().body("Показания не могут быть отрицательными");
//    }
//    if (lastIndication.getIndication() > indication) {
//      return ResponseEntity.badRequest().body("Показания счетчика меньше предыдущих показаний");
//    } else {
//      ElectricityMeter meter = meterRepository.findById(serial).get();
//      Indication indication1 = new Indication();
//      indication1.setIndication(indication);
//      indication1.setId(UUID.randomUUID().toString());
//      indication1.setSendingDate(Instant.now());
//      indication1.setElectricityMeter(meter);
//      meter.getIndications().add(indication1);
//      /*
//      meterRepository.save(meter);
//      return ResponseEntity.ok(new MeterDTO(meter.getSerialNumber(), indication1.getIndication()));
//       */
//      electricityMeterService.save(meter);
//      return ResponseEntity.ok(new MeterDTO(meter.getSerialNumber(), indication1.getIndication()));
    }
  }
