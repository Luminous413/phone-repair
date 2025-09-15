package com.lmscr.phonerepair.module;

import lombok.Data;

@Data
public class RepairManagement {
    private Integer userId;
    private Integer userRole;
    private String searchKeyword;
    private Integer pageNum;
    private Integer pageSize;
    private String sortField;
    private String sortOrder;
}
