package com.lmscr.testspring.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lmscr.testspring.module.LoginUser;
import com.lmscr.testspring.module.RegisterUser;
import com.lmscr.testspring.module.Role;
import com.lmscr.testspring.module.UserListQueryModule;
import com.lmscr.testspring.pojo.User;
import com.lmscr.testspring.service.util.Result;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;

/**
 * 用户服务接口
 */
public interface UserService extends IService<User> {
    /**
     * 用户登录
     *
     * @param usernameOrEmail 用户名或邮箱
     * @param password        密码
     * @return 登录用户
     */
    LoginUser login(String usernameOrEmail, String password);

    /**
     * 用户注册
     *
     * @param registerUser 注册用户
     * @return 注册结果
     */
    Result<String> register(RegisterUser registerUser);

    /**
     * 更新用户密码
     *
     * @param userEmail       用户邮箱
     * @param userNewPassword 用户新密码
     * @return 更新结果
     */
    Result<String> updatePassword(String userEmail, String userNewPassword);

    /**
     * 获取所有用户
     *
     * @return 所有用户
     */
    Result<Map<String, Object>> getAllUsers(UserListQueryModule userListQueryModule);

    /**
     * 获取角色列表
     *
     * @return 角色列表
     */
    Result<List<Role>> getRoleList();
}
