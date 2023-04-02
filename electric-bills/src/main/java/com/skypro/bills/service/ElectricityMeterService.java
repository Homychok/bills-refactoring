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
           throw new ElectricityMeterNotFoundException("Нет значения");
       }
       int meter = electricityMeter.get().getIndications().stream()
               .max(Comparator.comparing(Indication::getSendingDate)).orElse(new Indication()).getIndication();

       MeterDTO meterDTO = new MeterDTO();
       meterDTO.setLastIndication(meter);
       return meterDTO;
    }
    public MeterDTO addIndication(String serialNumber, Integer indication) {

        Optional<ElectricityMeter> electricityMeter = meterRepository.findAllBySerialNumber(serialNumber);

        if(electricityMeter.isEmpty()) {
            throw new ElectricityMeterNotFoundException("Нет значения");
        }
        if(indication < 0) {
            throw new IndicationNotFoundOrIncorrectException("Индикатор не может быть меньше нуля");
        }

        ElectricityMeter meter = electricityMeter.get();

        Indication maxIndication = meter.getIndications()
                .stream()
                .max(Comparator.comparing(Indication::getSendingDate))
                .orElse(new Indication());

        if (maxIndication.getIndication() > indication) {
            throw new IndicationNotFoundOrIncorrectException("Некорректное значение");
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
}
