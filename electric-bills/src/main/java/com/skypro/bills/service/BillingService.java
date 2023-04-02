package com.skypro.bills.service;

import com.skypro.bills.config.ConfigFile;
import com.skypro.bills.dto.BalanceDTO;
import com.skypro.bills.dto.PaymentDTO;
import com.skypro.bills.exceptions.IndicationNotFoundOrIncorrectException;
import com.skypro.bills.model.ElectricityMeter;
import com.skypro.bills.projection.Projection;
import com.skypro.bills.repository.IndicationRepository;
import com.skypro.bills.repository.MeterRepository;
import org.springframework.stereotype.Service;

@Service
public class BillingService extends ConfigFile {
    private IndicationRepository indicationRepository;

    private MeterRepository meterRepository;
    public BillingService(IndicationRepository indicationRepository, MeterRepository meterRepository) {
        this.indicationRepository = indicationRepository;
        this.meterRepository = meterRepository;
    }

    public BalanceDTO pay(String serialNumber, PaymentDTO paymentDTO) {
        Projection projection = meterRepository.findAllById(serialNumber);
        int indication;
        if (paymentDTO.getAmount() <= 0) {
            throw new IndicationNotFoundOrIncorrectException("Значение не может быть меньше нуля");
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
    public BalanceDTO pay(String serialNumber) {

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
