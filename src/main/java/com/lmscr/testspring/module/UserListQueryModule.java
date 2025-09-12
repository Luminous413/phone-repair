package com.lmscr.testspring.module;

import com.lmscr.testspring.service.util.PageParam;
import lombok.Data;

@Data
public class UserListQueryModule implements PageParam {
    private Integer userId;
    private Integer userRole;
    private String searchKeyword;
    private Integer pageNum;
    private Integer pageSize;
    private String sortField;
    private String sortPart;
}
