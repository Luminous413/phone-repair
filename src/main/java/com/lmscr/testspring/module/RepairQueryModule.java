package com.lmscr.testspring.module;

import lombok.Data;

/**
 * 报修查询模块
 * 用于接收前端报修查询的参数
 */
@Data // Lombok 注解，自动生成 getter/setter 方法，需导入 Lombok 依赖
public class RepairQueryModule {
    /**
     * 用户ID（前端是 number 类型，后端用 Integer 接收）
     */
    private Integer userId;
    /**
     * 用户角色（前端是 number 类型，后端用 Integer 接收）
     */
    private Integer userRole;
    /**
     * 搜索关键词（模糊查询用）
     */
    private String searchKeyword;
    /**
     * 报修状态（前端是 number 类型，后端用 Integer 接收）
     */
    private Integer repairStatus;
    /**
     * 当前页码，默认 1（防止前端不传）
     */
    private Integer pageNum = 1;
    /**
     * 每页条数，默认 10（防止前端不传）
     */
    private Integer pageSize = 10;
    /**
     * 排序字段（如 created_at）
     */
    private String sortField;
    /**
     * 排序顺序（asc/desc）
     */
    private String sortOrder;
}
