package com.lmscr.testspring.module;

import lombok.Data;

@Data
public class AddPart {
    private String partName;
    private Double partPrice;
    private Integer stockQuantity;
    private Integer supplierId;
}
