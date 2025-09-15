package com.lmscr.phonerepair.module;

import lombok.Data;

@Data
public class UpdatePart {
    private Integer partId;
    private String partName;
    private Double partPrice;
    private Integer stockQuantity;
    private Integer supplierId;
    private String partDescription;
}
