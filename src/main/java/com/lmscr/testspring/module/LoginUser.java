package com.lmscr.testspring.module;

import lombok.Data;

/**
 * 登录用户模块：封装登录用户的基本信息
 * 用于接收前端登录用户的信息
 */
@Data // 自动生成 getter/setter/toString 等方法（Lombok 注解）
public class LoginUser {
    /**
     * 用户唯一标识
     */
    private Integer userId;
    /**
     * 用户姓名
     */
    private String userName;
    /**
     * 用户电子邮箱
     */
    private String userEmail;
    /**
     * 关联角色 ID（用于权限控制）
     */
    private String roleId;
    /**
     * 用户个人简介
     */
    private String userBio;
    /**
     * 用户手机号码（用于联系）
     */
    private String userPhone;
}

