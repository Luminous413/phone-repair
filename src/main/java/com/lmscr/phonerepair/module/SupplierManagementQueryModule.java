package com.lmscr.phonerepair.module;

import com.lmscr.phonerepair.util.PageParam;
import lombok.Data;

@Data
public class SupplierManagementQueryModule implements PageParam {
    private Integer userId;
    private String searchKeyword;
    private Integer pageNum;
    private Integer pageSize;
    private String sortField;
    private String sortOrder;
}
