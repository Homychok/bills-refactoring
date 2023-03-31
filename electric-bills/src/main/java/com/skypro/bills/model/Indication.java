package com.skypro.bills.model;
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.FetchType;
//import jakarta.persistence.Id;
//import jakarta.persistence.ManyToOne;

import lombok.Data;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Data
public class Indication {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private String id;
  private int indication;
  private Instant sendingDate;
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private ElectricityMeter electricityMeter;
//  public void setElectricityMeter(ElectricityMeter electricityMeter) {
//    this.electricityMeter = electricityMeter;
//  }
//  public String getId() {
//    return id;
//  }
//
//  public void setId(String id) {
//    this.id = id;
//  }
//
//  public int getIndication() {
//    return indication;
//  }
//
//  public void setIndication(int indication) {
//    this.indication = indication;
//  }
//
//  public Instant getSendingDate() {
//    return sendingDate;
//  }
//
//  public void setSendingDate(Instant sendingDate) {
//    this.sendingDate = sendingDate;
//  }
//
//  public ElectricityMeter getElectricityMeter() {
//    return electricityMeter;
//  }


}
