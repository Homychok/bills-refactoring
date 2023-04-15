package com.skypro.bills.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Entity
@Data
public class Indication {

  @Id
  private String id;
  private int indication;
  private Instant sendingDate;
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private ElectricityMeter electricityMeter;

}
