package com.skypro.bills.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.skypro.bills.model.ElectricityMeter;
import lombok.Data;

@Data
public class MeterDTO {

  private String serialNumber;
  @JsonProperty(access = Access.READ_ONLY)
  private int lastIndication;

  public MeterDTO() {
  }

  public MeterDTO(String serialNumber, int lastIndication) {
    this.serialNumber = serialNumber;
    this.lastIndication = lastIndication;
  }
  public ElectricityMeter fromMeterDTOToElectricityMeter(){
    ElectricityMeter electricityMeter = new ElectricityMeter();
    electricityMeter.setSerialNumber(this.getSerialNumber());
    electricityMeter.setBalance(0);
    return electricityMeter;
  }

  public static MeterDTO fromElectricityMeterToMeterDTO (ElectricityMeter meter) {
    MeterDTO dto = new MeterDTO();
    dto.setSerialNumber(meter.getSerialNumber());
    return dto;
  }
//
//  public String getSerialNumber() {
//    return serialNumber;
//  }
//
//  public void setSerialNumber(String serialNumber) {
//    this.serialNumber = serialNumber;
//  }
//
//  public int getLastIndication() {
//    return lastIndication;
//  }
//
//  public void setLastIndication(int lastIndication) {
//    this.lastIndication = lastIndication;
//  }
}
