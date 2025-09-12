package com.lmscr.testspring.service.util;

public interface PageParam {
    String getSearchKeyword();
    Integer getPageNum();
    Integer getPageSize();
    String getSortField();
    String getSortPart();
    
    void setSearchKeyword(String keyword);
    void setPageNum(Integer num);
    void setPageSize(Integer size);
    void setSortField(String field);
    void setSortPart(String part);
}
