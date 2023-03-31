package com.skypro.bills.service;

import com.skypro.bills.dto.BalanceDTO;
import com.skypro.bills.dto.IndicationDTO;
import com.skypro.bills.dto.MeterDTO;
import com.skypro.bills.dto.PaymentDTO;
import com.skypro.bills.exceptions.ElectricityMeterNotFoundException;
import com.skypro.bills.exceptions.IndicationNotFoundOrIncorrectException;
import com.skypro.bills.model.ElectricityMeter;
import com.skypro.bills.model.Indication;
import com.skypro.bills.projection.Projection;
import com.skypro.bills.repository.IndicationRepository;
import com.skypro.bills.repository.MeterRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Optional;

@Service
public class ElectricityMeterService {
    private final static double priceForKW = 1.05;
    private MeterRepository meterRepository;
    private IndicationRepository indicationRepository;

    public ElectricityMeterService(MeterRepository meterRepository, IndicationRepository indicationRepository) {
        this.meterRepository = meterRepository;
        this.indicationRepository = indicationRepository;
    }

    public MeterDTO createMeter(MeterDTO meterDTO) {
        ElectricityMeter electricityMeter = meterDTO.fromMeterDTOToElectricityMeter();
         meterRepository.save(electricityMeter);
         return meterDTO;
    }

    public MeterDTO getMeterBySerialNumber(String serialNumber) {
        Optional<ElectricityMeter> electricityMeter = meterRepository.findAllBySerialNumber(serialNumber);
       if (electricityMeter.isEmpty()){
           throw new ElectricityMeterNotFoundException();
       }
//       ElectricityMeter meter1 = electricityMeter.get();
       int meter = electricityMeter.get().getIndications().stream()
               .max(Comparator.comparing(Indication::getSendingDate)).orElse(new Indication()).getIndication();

       MeterDTO meterDTO = new MeterDTO();
       meterDTO.setLastIndication(meter);
       return meterDTO;
    }
    public MeterDTO addIndication(String serialNumber, Integer indication) {

        Optional<ElectricityMeter> electricityMeter = meterRepository.findAllBySerialNumber(serialNumber);

        if(electricityMeter.isEmpty()) {
            throw new ElectricityMeterNotFoundException();
        }
        if(indication < 0) {
            throw new IndicationNotFoundOrIncorrectException();
        }

        ElectricityMeter meter = electricityMeter.get();

        Indication maxIndication = meter.getIndications()
                .stream()
                .max(Comparator.comparing(Indication::getSendingDate))
                .orElse(new Indication());

        if (maxIndication.getIndication() > indication) {
            throw new IndicationNotFoundOrIncorrectException();
        }

        Indication indicationCurrent = new Indication();
        indicationCurrent.setIndication(indication);
        IndicationDTO indicationDTO = IndicationDTO.fromIndicationToIndicationDTO(indicationCurrent);
        Indication indicationNew = indicationDTO.fromIndicationDTOToIndication();
        indicationNew.setElectricityMeter(meter);
        indicationRepository.save(indicationNew);
        meter.getIndications().add(indicationNew);

        MeterDTO meterDTO = new MeterDTO();
        meterDTO.setSerialNumber(meter.getSerialNumber());
        meterDTO.setLastIndication(indicationNew.getIndication());

        return meterDTO;
    }
    public BalanceDTO pay(String serialNumber, PaymentDTO paymentDTO) {
        Projection projection = meterRepository.findAllById(serialNumber);
        int indication;
        if (paymentDTO.getAmount() <= 0) {
            throw new IndicationNotFoundOrIncorrectException();
        }
        if (projection.getIndication() == null) {
            indication = 0;
        } else {
            indication = projection.getIndication();
        }

        double newBalance = projection.getBalance() + paymentDTO.getAmount() - (indication * priceForKW);
        ElectricityMeter meter = new ElectricityMeter();
        meter.setSerialNumber(projection.getSerialNumber());
        meter.setBalance(newBalance);
        meterRepository.save(meter);
        BalanceDTO balanceDTO = new BalanceDTO();
        balanceDTO.setSerialNumber(serialNumber);
        balanceDTO.setCurrentBalance(newBalance);
        return balanceDTO;
    }

    public BalanceDTO pay(String serialNumber){
        Projection projection = meterRepository.findAllById(serialNumber);
        int indication;
        if (projection.getIndication() == null) {
            indication = 0;
        } else {
            indication = projection.getIndication();
        }
        double newBalance = projection.getBalance() - indication * priceForKW;
        BalanceDTO balanceDTO = new BalanceDTO();
        balanceDTO.setSerialNumber(serialNumber);
        balanceDTO.setCurrentBalance(newBalance);
        return balanceDTO;
    }
}
