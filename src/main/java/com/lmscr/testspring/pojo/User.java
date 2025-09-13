package com.lmscr.testspring.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户表
 */
@Data  // 自动生成 getter 和 setter 方法
@TableName(value = "yjx_user")  // 指定数据库表名
@Builder  // 构造器
@AllArgsConstructor  // 全参构造函数
@NoArgsConstructor  // 无参构造函数
public class User implements Serializable {
    /**
     * 用户ID
     */
    @TableId
    private Integer userId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 用户邮箱
     */
    private String userEmail;
    /**
     * 用户密码哈希
     */
    private String userPasswordHash;
    /**
     * 角色ID
     */
    private String roleId;
    /**
     * 用户简介
     */
    private String userBio;
    /**
     * 用户手机号
     */
    private String userPhone;
    /**
     * 用户性别
     */
    private String userGender;
    /**
     * 用户最后活跃时间
     */
    private LocalDateTime userLastActive;
    /**
     * 用户创建时间
     */
    private LocalDateTime userCreatedAt;
    /**
     * 用户状态
     */
    private String userStatus;
}
