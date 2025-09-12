package com.lmscr.testspring.module;

import lombok.Data;

@Data
public class CreateRepairManagement {
    private Integer repairRequestId;
    private String repairNotes;
    private Integer technicianId;
}
