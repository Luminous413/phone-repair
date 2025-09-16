package com.lmscr.phonerepair.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@TableName(value = "yjx_repair_management")
@Builder  // 构造器
@AllArgsConstructor  // 全参构造函数
@NoArgsConstructor  // 无参构造函数
public class Management {
    @TableId(type = IdType.AUTO)
    private Integer repairId;
    private Integer repairRequestId;
    private String technicianId;
    private Double repairPrice;
    private String paymentStatus;
    private String repairNotes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // 2. 新增非数据库字段（从关联表获取，前端必需）
    /**
     * 手机型号（来自 yjx_repair_request.phone_model）
     */
    @TableField(exist = false)  // 标记：该字段不在 yjx_repair_management 表中
    private String phoneModel;
    /**
     * 维修状态名称（来自 yjx_repair_request.request_status，转文字）
     */
    @TableField(exist = false)
    private String statusName;
    /**
     * 订单用户名（来自 yjx_user.user_name）
     */
    @TableField(exist = false)
    private String userName;
}
