package com.lmscr.testspring.service.util;

public class CheckParams {
    public static void checkParams(PageParam object) {
        if (object.getSearchKeyword() == null) {
            object.setSearchKeyword("");
        }
        if (object.getPageNum() == null) {
            object.setPageNum(1);
        }
        if (object.getPageSize() == null) {
            object.setPageSize(10);
        }
        if (object.getSortField() == null) {
            object.setSortField("created_at");
        }
        if (object.getSortPart() == null) {
            object.setSortPart("desc");
        }
    }
}
