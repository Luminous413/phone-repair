package com.lmscr.testspring.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
//import com.github.pagehelper.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page; // 使用MP的Page
import com.lmscr.testspring.module.ReceptionistVO;
import com.lmscr.testspring.pojo.Repair;
import com.lmscr.testspring.pojo.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RepairMapper extends BaseMapper<Repair> {
    /**
     * 自定义分页查询方法：分页查询维修单：支持权限过滤（userId = 1/3 查全部，其他查自己）、搜索、排序
     *
     * @param page          分页参数（页码、每页条数）
     * @param userId        当前登录用户 ID（用于权限过滤）
     * @param searchKeyword 搜索关键词（模糊查手机型号、问题描述）
     * @param sortField     排序字段（如 createdAt、requestId）
     * @param sortOrder     排序方向（asc/desc）
     * @return 分页结果（含数据列表和总条数）
     */
    @Select("""
                SELECT
                    rr.*,  -- 保留维修单表所有字段
                    u.user_name AS receptionistName,  -- 从用户表获取接待人员姓名，别名对应 Repair 类的 receptionistName 字段
                    ru.user_name AS repairUserName  -- 从用户表获取报修人员姓名，别名对应 Repair 类的 repairUserName 字段
                FROM yjx_repair_request rr
                -- 关键：关联用户表，通过维修单的 receptionistId 匹配用户表的 userId
                LEFT JOIN yjx_user u ON rr.receptionist_id = u.user_id
                -- 修改：使用不同的别名避免关键字冲突
                LEFT JOIN yjx_user cu ON cu.user_id = #{userId}
                -- 修改：使用不同的别名避免关键字冲突
                LEFT JOIN yjx_user ru ON ru.user_id = rr.user_id
                WHERE 1 = 1
                -- 修改后的权限过滤：根据用户角色过滤
                -- 角色 1/3/4 查全部，其他只查自己创建的
                AND ( cu.role_id IN (1, 3, 4) OR rr.user_id = #{userId} )
                -- 搜索关键词：模糊匹配手机型号或问题描述
                AND ( rr.phone_model LIKE CONCAT('%', #{searchKeyword}, '%') 
                      OR rr.phone_issue_description LIKE CONCAT('%', #{searchKeyword}, '%') )
                -- 排序：有排序字段则按字段排序，无则默认按创建时间降序
                ORDER BY
                    CASE WHEN #{sortField} IS NOT NULL AND #{sortOrder} IS NOT NULL
                        THEN CASE #{sortField}
                            WHEN 'createdAt' THEN rr.created_at
                            WHEN 'requestId' THEN rr.request_id
                            ELSE rr.created_at END
                    ELSE rr.created_at END
                    ${sortOrder == 'desc' ? 'DESC' : 'ASC'}
            """)
    IPage<Repair> selectRepairByCondition(Page<Repair> page,                            // MP 分页对象（自动处理 limit 和 offset）
                                          @Param("userId") Integer userId,              // 权限过滤用的用户 ID
                                          @Param("searchKeyword") String searchKeyword, // 搜索关键词
                                          @Param("sortField") String sortField,         // 排序字段
                                          @Param("sortOrder") String sortOrder);        // 排序方向

    /**
     * 获取维修状态名称
     *
     * @param requestStatus 维修状态码
     * @return 维修状态名称
     */
    @Select("""
                SELECT
                    status_name
                FROM yjx_repair_status
                WHERE status_id = #{requestStatus}
            """)
    String getRepairStatus(Integer requestStatus);

    /**
     * 获取所有接待员
     *
     * @return 接待员列表
     */
    @Select("""
                SELECT
                    user_id AS userId,
                    user_name AS userName
                FROM yjx_user
                WHERE role_id = 3
                GROUP BY user_id
            """)
    List<ReceptionistVO> getAllReceptionistList();

    /**
     * 删除维修单
     *
     * @param repairId 维修单 ID
     * @param userId   用户 ID（用于权限校验）
     * @return 删除成功返回 true，否则返回 false
     */
    @Delete("""
            DELETE
            FROM yjx_repair_request
            WHERE request_id = #{repairId}
            AND (#{userId} IN (1, 3) OR user_id = #{userId})
            """)
    Integer deleteRepairByIdAndUserId(Integer repairId, Integer userId);
}
