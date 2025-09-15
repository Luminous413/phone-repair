package com.lmscr.phonerepair.module;

import lombok.Data;

@Data
public class AddPart {
    private String partName;
    private Double partPrice;
    private Integer stockQuantity;
    private Integer supplierId;
}
