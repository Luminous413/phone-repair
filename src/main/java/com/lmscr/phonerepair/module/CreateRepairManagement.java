package com.lmscr.phonerepair.module;

import lombok.Data;

@Data
public class CreateRepairManagement {
    private Integer repairRequestId;
    private String repairNotes;
    private Integer technicianId;
}
