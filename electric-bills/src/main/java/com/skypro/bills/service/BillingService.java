package com.skypro.bills.service;

import com.skypro.bills.dto.BalanceDTO;
import com.skypro.bills.dto.PaymentDTO;
import com.skypro.bills.exceptions.ElectricityMeterNotFoundException;
import com.skypro.bills.exceptions.IndicationNotFoundOrIncorrectException;
import com.skypro.bills.model.ElectricityMeter;
import com.skypro.bills.model.Indication;
import com.skypro.bills.projection.Projection;
import com.skypro.bills.repository.IndicationRepository;
import com.skypro.bills.repository.MeterRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class BillingService  {
    private IndicationRepository indicationRepository;
    @Value("${priceForKW}")
    private double priceForKW;
    private MeterRepository meterRepository;
    public BillingService(IndicationRepository indicationRepository, MeterRepository meterRepository) {
        this.indicationRepository = indicationRepository;
        this.meterRepository = meterRepository;
    }

    public BalanceDTO pay(String serialNumber, PaymentDTO paymentDTO) {
        if (paymentDTO.getAmount() <= 0) {
            throw new IndicationNotFoundOrIncorrectException("Сумма не может быть меньше или равна нулю");
        }
        ElectricityMeter meter = meterRepository.findById(serialNumber).orElseThrow(() ->new ElectricityMeterNotFoundException("Нет значения"));
        double newBalance = meter.getBalance() + paymentDTO.getAmount();
        meter.setBalance(newBalance);
        Indication indication = indicationRepository.findFirstByElectricityMeterSerialNumberOrderBySendingDate(serialNumber).orElse(new Indication());
        double currentBalance = meter.getBalance() - indication.getIndication() * priceForKW;
        meter.setBalance(currentBalance);
        meterRepository.save(meter);
        BalanceDTO balanceDTO = new BalanceDTO();
        balanceDTO.setSerialNumber(meter.getSerialNumber());
        balanceDTO.setCurrentBalance(currentBalance);
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
