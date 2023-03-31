package com.skypro.bills.dto;

import com.skypro.bills.model.Indication;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class IndicationDTO {
    private String id;
    private int indication;
    private Instant sendingDate;

    public static IndicationDTO fromIndicationToIndicationDTO(Indication indication) {
        IndicationDTO dto = new IndicationDTO();
        dto.setId(UUID.randomUUID().toString());
        dto.setIndication(indication.getIndication());
        dto.setSendingDate(Instant.now());
        return dto;
    }

    public Indication fromIndicationDTOToIndication() {
        Indication indication = new Indication();
        indication.setId(this.getId());
        indication.setIndication(this.getIndication());
        indication.setSendingDate(this.getSendingDate());
        return indication;
    }
}
