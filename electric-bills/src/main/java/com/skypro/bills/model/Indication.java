package com.skypro.bills.model;

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

}
