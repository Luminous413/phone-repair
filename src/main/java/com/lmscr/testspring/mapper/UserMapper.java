package com.lmscr.testspring.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lmscr.testspring.module.Role;
import com.lmscr.testspring.pojo.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface UserMapper extends BaseMapper<User> {
    /**
     * 根据用户 ID 查询用户
     *
     * @param userId 用户 ID
     * @return 用户
     */
    @Select("""
            select * from yjx_user where user_id = #{userId}
            """)
    User selectById(Integer userId);

    /**
     * 根据用户 ID 更新用户密码
     *
     * @param userId           用户 ID
     * @param userPasswordHash 用户密码哈希
     * @return 更新结果
     */
    @Update("""
            update yjx_user
            set user_password_hash = #{userPasswordHash}
            where user_id = #{userId}
            """)
    boolean updatePasswordByUserId(@Param("userId") Integer userId,
                                   @Param("userPasswordHash") String userPasswordHash);

    /**
     * 更新用户最后活跃时间
     *
     * @param userId     用户 ID
     * @param lastActive 最后活跃时间
     * @return 更新结果
     */
    @Update("""
            update yjx_user
            set user_last_active = #{lastActive}
            where user_id = #{userId}
            """)
    boolean updateUserLastActive(@Param("userId") Integer userId,
                                 @Param("lastActive") LocalDateTime lastActive);

    /**
     * 获取所有用户
     *
     * @param page          分页信息
     * @param userId        当前用户 ID
     * @param userRole      当前用户角色
     * @param searchKeyword 搜索关键词
     * @param sortField     排序字段
     * @param sortPart      排序方向
     * @return 用户列表
     */
    @Select("""
                SELECT
                    user_id AS userId,
                    user_name AS userName,
                    user_email AS userEmail,
                    role_id AS roleId,
                    user_bio AS userBio,
                    user_phone AS userPhone,
                    user_created_at AS userCreatedAt
                FROM
                    yjx_user
                WHERE 1 = 1
                AND (#{userRole} IN (1, 3) OR user_id = #{userId})
                AND (user_name LIKE CONCAT('%', #{searchKeyword}, '%')
                    OR user_email LIKE CONCAT('%', #{searchKeyword}, '%')
                    OR user_bio LIKE CONCAT('%', #{searchKeyword}, '%')
                    OR user_phone LIKE CONCAT('%', #{searchKeyword}, '%'))
                ORDER BY
                    CASE WHEN #{sortField} IS NOT NULL AND #{sortPart} IS NOT NULL
                         THEN CASE #{sortField} 
                              WHEN 'createdAt' THEN user_created_at 
                              WHEN 'userName' THEN user_name 
                              ELSE user_created_at END 
                    ELSE user_created_at END
                    -- 排序方向：默认降序
                    ${sortPart == 'desc' ? 'DESC' : 'ASC'}
            """)
    IPage<User> getAllUsers(Page<User> page,
                            @Param("userId") Integer userId,
                            @Param("userRole") Integer userRole,
                            @Param("searchKeyword") String searchKeyword,
                            @Param("sortField") String sortField,
                            @Param("sortPart") String sortPart);

    /**
     * 获取角色列表
     *
     * @return 角色列表
     */
    @Select("""
                SELECT
                    role_id AS roleId,
                    role_name AS roleName
                FROM
                    yjx_role
                ORDER BY
                    role_id
            """)
    List<Role> getRoleList();

    /**
     * 根据用户 ID 更新用户信息
     *
     * @param userId    用户 ID
     * @param userName  用户名
     * @param userEmail 用户邮箱
     * @param userPhone 用户手机号
     * @param userBio   用户简介
     * @param roleId    角色 ID
     * @return 更新结果
     */
    @Update("""
                UPDATE yjx_user
                SET
                    user_name = #{userName},
                    user_email = #{userEmail},
                    user_phone = #{userPhone},
                    user_bio = #{userBio},
                    role_id = #{roleId}
                WHERE
                    user_id = #{userId}
            """)
    boolean updateUserByUserId(@Param("userId") Integer userId,
                               @Param("userName") String userName,
                               @Param("userEmail") String userEmail,
                               @Param("userPhone") String userPhone,
                               @Param("userBio") String userBio,
                               @Param("roleId") Integer roleId);

    /**
     * 根据用户 ID 删除用户
     *
     * @param userId 用户 ID
     * @return 删除结果
     */
    @Delete("""
                DELETE FROM yjx_user
                WHERE
                    user_id = #{userId}
            """)
    boolean deleteUserByUserId(@Param("userId") Integer userId);

    /**
     * 创建新用户
     *
     * @param userName         用户名
     * @param userEmail        用户邮箱
     * @param userPasswordHash 用户密码哈希
     * @param userPhone        用户手机号
     * @param userBio          用户简介
     * @param roleId           角色 ID
     * @param userCreatedAt    用户创建时间
     * @return 创建结果
     */
    @Insert("""
                INSERT yjx_user
                    (user_name, user_email, user_password_hash, role_id, user_bio, user_phone, user_created_at)
                VALUES
                    (#{userName}, #{userEmail}, #{userPasswordHash}, #{roleId}, #{userBio}, #{userPhone}, #{userCreatedAt})
            """)
    boolean createNewUser(@Param("userName") String userName,
                          @Param("userEmail") String userEmail,
                          @Param("userPasswordHash") String userPasswordHash,
                          @Param("userPhone") String userPhone,
                          @Param("userBio") String userBio,
                          @Param("roleId") Integer roleId,
                          @Param("userCreatedAt") LocalDateTime userCreatedAt);
}