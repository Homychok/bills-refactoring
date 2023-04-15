package com.skypro.bills.dto;

import com.skypro.bills.model.ElectricityMeter;
import lombok.Data;
@Data
public class BalanceDTO {

  private String serialNumber;
  private double currentBalance;

  public BalanceDTO() {
  }

  public BalanceDTO(String serialNumber, double currentBalance) {
    this.serialNumber = serialNumber;
    this.currentBalance = currentBalance;
  }

  public static BalanceDTO fromElectricityMeterToBalanceDTO(ElectricityMeter electricityMeter) {
    BalanceDTO balanceDTO = new BalanceDTO();
    balanceDTO.setSerialNumber(electricityMeter.getSerialNumber());
    balanceDTO.setCurrentBalance(electricityMeter.getBalance());
    return balanceDTO;
  }

  public ElectricityMeter fromBalanceDTOtoElectricityMeter(BalanceDTO balanceDTO) {
    ElectricityMeter electricityMeter = new ElectricityMeter();
    electricityMeter.setSerialNumber(this.serialNumber);
    electricityMeter.setBalance(this.currentBalance);
    return electricityMeter;
  }
}
