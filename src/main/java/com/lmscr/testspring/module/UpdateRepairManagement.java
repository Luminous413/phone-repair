package com.lmscr.testspring.module;

import lombok.Data;

@Data
public class UpdateRepairManagement {
    private Integer repairId;
    private Double repairPrice;
    private String paymentStatus;
    private String repairNotes;
    private Integer technicianId;
}
