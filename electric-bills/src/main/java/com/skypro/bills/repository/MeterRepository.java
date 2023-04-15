package com.skypro.bills.repository;

import com.skypro.bills.model.ElectricityMeter;
import com.skypro.bills.projection.Projection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MeterRepository extends JpaRepository<ElectricityMeter, String> {

    Optional<ElectricityMeter> findAllBySerialNumber(String serialNumber);
    @Query (value = "SELECT table1.serial_number AS serialNumber, " +
            "table2.indication AS maxIndication, " +
            "MAX(table2.sending_date) AS maxSendingDate " +
            "FROM electricity_meter AS table1 " +
            "LEFT JOIN indication AS table2 " +
            "ON table1.serial_number = table2.electricity_meter_serial_number " +
            "WHERE table1.serial_number LIKE ?1 " +
            "GROUP BY serialNumber, maxIndication " +
            "ORDER BY maxSendingDate DESC " +
            "LIMIT 1", nativeQuery = true)
    Projection findAllById(String serialNumber);
}
