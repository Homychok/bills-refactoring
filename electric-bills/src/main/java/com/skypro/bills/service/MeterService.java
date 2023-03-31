package com.skypro.bills.service;

import com.skypro.bills.dto.MeterDTO;
import com.skypro.bills.exceptions.ElectricityMeterNotFoundException;
import com.skypro.bills.projection.MeterWithLastIndicationView;
import com.skypro.bills.repository.MeterRepository;
import org.springframework.stereotype.Service;

@Service
public class MeterService {

    private MeterRepository meterRepository;

    public MeterService(MeterRepository meterRepository) {
        this.meterRepository = meterRepository;
    }


    public MeterDTO createMeter(MeterDTO meterDTO) {
         meterRepository.save(meterDTO.to());
         return meterDTO;
    }

    public MeterDTO getMeterBySerialNumber(String serialNumber) {
       MeterWithLastIndicationView meterProj = meterRepository.findBySerialNumber(serialNumber);
       if (meterProj == null){
           throw new ElectricityMeterNotFoundException();
       }
       MeterDTO meterDTO = new MeterDTO();
       meterDTO.setSerialNumber(meterProj.getSerialNumber());
       meterDTO.setLastIndication(meterProj.getIndication());
       return meterDTO;
    }
}
