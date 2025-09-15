package com.lmscr.phonerepair.service.util;

public interface PageParam {
    String getSearchKeyword();
    Integer getPageNum();
    Integer getPageSize();
    String getSortField();
    String getSortOrder();
    
    void setSearchKeyword(String keyword);
    void setPageNum(Integer num);
    void setPageSize(Integer size);
    void setSortField(String field);
    void setSortOrder(String order);
}
