package com.lmscr.testspring.controller;

import com.lmscr.testspring.module.LoginUser;
import com.lmscr.testspring.module.RegisterUser;
import com.lmscr.testspring.module.Role;
import com.lmscr.testspring.module.UserListQueryModule;
import com.lmscr.testspring.service.UserService;
import com.lmscr.testspring.service.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    /**
     * 自动注入用户服务层实现
     */
    @Autowired
    private UserService userService;

    /**
     * 用户登录
     *
     * @param usernameOrEmail 用户名或邮箱
     * @param password        密码
     * @return 登录用户
     */
    @PostMapping("/login")
    public Result<LoginUser> login(@RequestParam(value = "usernameOrEmail") String usernameOrEmail, @RequestParam(value = "password") String password) {
        // 调用用户服务层登录方法
        LoginUser loginUser = userService.login(usernameOrEmail, password);
        if (loginUser == null) {
            // 登录失败
            return Result.fail("登录失败", 400);
        }
        // 登录成功
        return Result.success("登录成功", loginUser);
    }

    /**
     * 用户注册
     *
     * @param registerUser 注册用户
     * @return 注册结果
     */
    @PostMapping("/createUser")
    public Result<String> register(@RequestBody RegisterUser registerUser) {
        // 调用用户服务层注册方法
        return userService.register(registerUser);
    }

    /**
     * 更新用户密码
     *
     * @param userEmail       用户邮箱
     * @param userNewPassword 用户新密码
     * @return 更新结果
     */
    @PostMapping("/updatePassword")
    public Result<String> updatePassword(@RequestParam(value = "userEmail") String userEmail, @RequestParam(value = "userNewPassword") String userNewPassword) {
        return userService.updatePassword(userEmail, userNewPassword);
    }

    /**
     * 获取所有用户
     *
     * @return 所有用户
     */
    @GetMapping("/getAllUsers")
    public Result<Map<String, Object>> getAllUsers(UserListQueryModule userListQueryModule) {
        return userService.getAllUsers(userListQueryModule);
    }

    /**
     * 获取角色列表
     *
     * @return 角色列表
     */
    @GetMapping("/getRoleList")
    public Result<List<Role>> getRoleList() {
        return userService.getRoleList();
    }
}
