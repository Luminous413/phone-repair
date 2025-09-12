package com.lmscr.testspring.module;

import lombok.Data;

/**
 * 注册用户模块
 * 用于接收前端注册用户的信息
 */
@Data
public class RegisterUser {
    /**
     * 用户名
     */
    private String userName;
    /**
     * 用户邮箱
     */
    private String userEmail;
    /**
     * 用户密码
     */
    private String userPasswordHash;
}
