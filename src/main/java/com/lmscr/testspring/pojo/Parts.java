package com.lmscr.testspring.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@TableName(value = "yjx_parts")
@Builder  // 构造器
@AllArgsConstructor  // 全参构造函数
@NoArgsConstructor  // 无参构造函数
public class Parts {
    private Integer partId;
    private String partName;
    private String partDescription;
    private Double partPrice;
    private Integer stockQuantity;
    private Integer supplierId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
