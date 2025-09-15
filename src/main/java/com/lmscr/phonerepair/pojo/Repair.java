package com.lmscr.phonerepair.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 报修表
 */
@Data
@TableName(value = "yjx_repair_request")  // 指定数据库表名
@Builder  // 构造器
@AllArgsConstructor  // 全参构造函数
@NoArgsConstructor  // 无参构造函数
public class Repair {
    /**
     * 报修请求ID
     */
    @TableId
    private Integer requestId;
    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 接待员ID
     */
    private Integer receptionistId;
    /**
     * 报修类型
     */
    private String phoneModel;
    /**
     * 报修问题描述
     */
    private String phoneIssueDescription;
    /**
     * 报修状态
     */
    private Integer requestStatus;
    /**
     * 维修状态名称
     */
    @TableField(exist = false)
    private String requestStatusName;
    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
    // 以下字段为非数据库字段
    /**
     * 接待员姓名
     */
    @TableField(exist = false)  // 关键：告诉 MyBatis-Plus 该字段不存在于数据库表中
    private String receptionistName;
    /**
     * 报修人姓名
     */
    @TableField(exist = false)
    private String repairUserName;
}
