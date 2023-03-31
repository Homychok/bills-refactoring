package com.skypro.bills.model;


import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Data
public class ElectricityMeter {
  @Id
  private String serialNumber;
  @OneToMany(mappedBy = "electricityMeter", cascade = CascadeType.ALL)
  private List<Indication> indications;
  private double balance;

}
