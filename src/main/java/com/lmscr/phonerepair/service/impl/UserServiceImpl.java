package com.lmscr.phonerepair.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lmscr.phonerepair.mapper.UserMapper;
import com.lmscr.phonerepair.module.*;
import com.lmscr.phonerepair.pojo.User;
import com.lmscr.phonerepair.service.UserService;
import com.lmscr.phonerepair.service.util.CheckParams;
import com.lmscr.phonerepair.service.util.Md5Password;
import com.lmscr.phonerepair.service.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户服务实现类
 * 提供用户的登录、注册等操作
 * 依赖于 UserMapper 和 UserService 接口
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private UserMapper userMapper;

    /**
     * 用户登录
     *
     * @param usernameOrEmail 用户名或邮箱
     * @param password        密码
     * @return 登录用户信息
     */
    @Override
    public CurrentUser login(String usernameOrEmail, String password) {
        // 构建组合查询条件（用户名或邮箱匹配）
        User user = this.getOne(new QueryWrapper<User>()
                // 用户名条件
                .eq("user_name", usernameOrEmail)
                .or()
                // 邮箱条件
                .eq("user_email", usernameOrEmail));

        // 用户不存在时返回空
        if (user == null) {
            return null;
        }

        // 使用MD5加密后比对密码哈希值
        if (!user.getUserPasswordHash().equals(Md5Password.generateMD5(password))) {
            return null;
        }

        // 将User实体转换为登录用户信息对象
        CurrentUser currentUser = new CurrentUser();
        // 设置用户ID
        currentUser.setUserId(user.getUserId());
        // 设置用户名
        currentUser.setUserName(user.getUserName());
        // 设置用户邮箱
        currentUser.setUserEmail(user.getUserEmail());
        // 设置角色ID（用于权限控制）
        currentUser.setRoleId(user.getRoleId());
        // 设置用户简介
        currentUser.setUserBio(user.getUserBio());
        // 设置用户手机号
        currentUser.setUserPhone(user.getUserPhone());

        // 更新用户最后活跃时间
        userMapper.updateUserLastActive(user.getUserId(), LocalDateTime.now());

        // 登录成功，返回用户信息
        return currentUser;
    }


    /**
     * 用户注册
     *
     * @param registerUser 注册用户
     * @return 注册结果
     */
    @Override
    public Result<String> register(RegisterUser registerUser) {
        // 判断参数是否为空
        if (registerUser.getUserName() == null || registerUser.getUserEmail() == null || registerUser.getUserPasswordHash() == null) {
            return Result.fail("参数不能为空", 500);
        }
        // 判断用户是否存在
        if (this.getOne(new QueryWrapper<User>()
                .eq("user_name", registerUser.getUserName())
                .or()
                .eq("user_email", registerUser.getUserEmail())) != null) {
            return Result.fail("用户名或邮箱已存在", 500);
        }
        // 判断密码是否符合规则（长度大于6，且包含字符串）
        if (registerUser.getUserPasswordHash().length() < 6 || !registerUser.getUserPasswordHash().matches(".*[a-zA-Z]+.*")) {
            return Result.fail("密码长度必须大于6，且包含字符串", 500);
        }

        // 创建用户实体
        User user = new User();
        // 设置用户名
        user.setUserName(registerUser.getUserName());
        // 设置用户邮箱
        user.setUserEmail(registerUser.getUserEmail());
        // 设置用户密码（MD5 加密）
        user.setUserPasswordHash(Md5Password.generateMD5(registerUser.getUserPasswordHash()));
        // 设置角色 ID（默认普通用户）
        user.setRoleId(2);
        // 调用 MyBatis-plus 的 save 方法保存用户
        boolean save = this.save(user);
        if (save) {
            return Result.success("注册成功", "");
        }

        return Result.fail("注册失败", 500);
    }

    /**
     * 更新用户密码
     *
     * @param userEmail       用户邮箱
     * @param userNewPassword 用户新密码
     * @return 更新结果
     */
    @Override
    public Result<String> updatePassword(String userEmail, String userNewPassword) {
        User user = this.getOne(new QueryWrapper<User>()
                .eq("user_email", userEmail));
        // 判断用户是否存在
        if (user == null) {
            return Result.fail("用户不存在", 500);
        }
        // 更新密码
        user.setUserPasswordHash(Md5Password.generateMD5(userNewPassword));

        boolean isUpdate = userMapper.updatePasswordByUserId(user.getUserId(), user.getUserPasswordHash());
        if (isUpdate) {
            return Result.success("更新成功", "");
        }
        return Result.fail("更新失败", 500);
    }

    /**
     * 获取所有用户
     *
     * @return 所有用户
     */
    @Override
    public Result<Map<String, Object>> getAllUsers(UserListQueryModule userListQueryModule) {
        // 参数校验
        if (userListQueryModule.getUserId() == null) {
            return Result.fail("用户 ID 不能为空", 500);
        }
        CheckParams.checkParams(userListQueryModule);

        // 分页查询
        Page<User> page = new Page<>(userListQueryModule.getPageNum(), userListQueryModule.getPageSize());
        IPage<User> userIPage = userMapper.getAllUsers(
                page,
                userListQueryModule.getUserId(),
                userListQueryModule.getUserRole(),
                userListQueryModule.getSearchKeyword(),
                userListQueryModule.getSortField(),
                userListQueryModule.getSortOrder()
        );
        if (userIPage.getRecords().isEmpty()) {
            return Result.fail("没有查询到数据", 404);
        }
        // 构建结果
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("count", userIPage.getTotal());
        resultMap.put("userList", userIPage.getRecords());
        return Result.success("查询成功", resultMap);
    }

    /**
     * 获取角色列表
     *
     * @return 角色列表
     */
    @Override
    public Result<List<Role>> getRoleList() {
        List<Role> roleList = userMapper.getRoleList();
        if (roleList == null) {
            return Result.fail("没有查询到数据", 404);
        }
        return Result.success("查询成功", roleList);
    }

    /**
     * 更新用户信息
     *
     * @param currentUser 当前用户
     * @return 更新结果
     */
    @Override
    public Result<String> updateUser(CurrentUser currentUser) {
        // 校验用户是否存在
        User user = this.getOne(new QueryWrapper<User>()
                .eq("user_id", currentUser.getUserId()));
        if (user == null) {
            return Result.fail("用户不存在", 404);
        }
        // 参数校验
        if (currentUser.getRoleId() == null) {
            currentUser.setRoleId(2);
        }
        if (currentUser.getUserName() == null) {
            currentUser.setUserName(user.getUserName());
        }
        if (currentUser.getUserEmail() == null) {
            currentUser.setUserEmail(user.getUserEmail());
        }
        if (currentUser.getUserPhone() == null) {
            currentUser.setUserPhone("");
        }
        if (currentUser.getUserBio() == null) {
            currentUser.setUserBio("");
        }
        // 校验邮箱是否被其他用户使用（邮箱具有唯一性）
        User userByEmail = this.getOne(new QueryWrapper<User>()
                .eq("user_email", currentUser.getUserEmail()));
        if (userByEmail != null && !userByEmail.getUserId().equals(user.getUserId())) {
            return Result.fail("邮箱已被其他用户使用", 500);
        }
        // 更新用户信息
        boolean isUpdate = userMapper.updateUserByUserId(
                currentUser.getUserId(),
                currentUser.getUserName(),
                currentUser.getUserEmail(),
                currentUser.getUserPhone(),
                currentUser.getUserBio(),
                currentUser.getRoleId()
        );
        if (isUpdate) {
            return Result.success("更新成功", "");
        }
        return Result.fail("更新失败", 500);
    }

    /**
     * 删除用户
     *
     * @param userId 用户ID
     * @return 删除结果
     */
    @Override
    public Result<String> deleteUser(Integer userId) {
        // 校验用户是否存在
        User user = this.getOne(new QueryWrapper<User>()
                .eq("user_id", userId));
        if (user == null) {
            return Result.fail("用户不存在", 404);
        }
        // 删除用户
        boolean isDelete = userMapper.deleteUserByUserId(userId);
        if (isDelete) {
            return Result.success("删除成功", "");
        }
        return Result.fail("删除失败", 500);
    }

    /**
     * 创建用户
     *
     * @param newUser 新用户
     * @return 创建结果
     */
    @Override
    public Result<String> createNewUser(NewUser newUser) {
        // 参数校验
        if (newUser.getUserPwd() == null) {
            return Result.fail("密码不能为空", 500);
        }
        if (newUser.getUserPhone() == null) {
            newUser.setUserPhone("");
        }
        // 校验邮箱是否存在（邮箱具有唯一性）
        User user = this.getOne(new QueryWrapper<User>()
                .eq("user_email", newUser.getUserEmail()));
        if (user != null) {
            return Result.fail("邮箱已存在", 500);
        }
        // 创建用户
        boolean isCreate = userMapper.createNewUser(
                newUser.getUserName(),
                newUser.getUserEmail(),
                Md5Password.generateMD5(newUser.getUserPwd()),
                newUser.getUserPhone(),
                "",
                2,
                LocalDateTime.now()
        );
        if (isCreate) {
            return Result.success("创建成功", "");
        }
        return Result.fail("创建失败", 500);
    }
}