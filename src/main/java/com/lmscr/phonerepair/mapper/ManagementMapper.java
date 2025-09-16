package com.lmscr.phonerepair.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lmscr.phonerepair.module.TechnicianVO;
import com.lmscr.phonerepair.pojo.Management;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ManagementMapper extends BaseMapper<Management> {
    /**
     * 分页查询维修管理列表（关联维修单表 + 用户表，获取前端所需所有字段）
     *
     * @param page          分页对象（页码、每页条数）
     * @param userId        当前登录用户 ID（用于权限过滤：如管理员查全部，普通用户查自己）
     * @param searchKeyword 搜索关键词（模糊匹配手机型号、维修描述、用户名）
     * @param sortField     排序字段（如 createdAt、repairId）
     * @param sortOrder     排序方向（asc/desc）
     * @return 分页结果（包含 Management 列表和总条数）
     */
    @Select("""
                SELECT
                    -- 1. 维修管理表（yjx_repair_management）字段
                    rm.repair_id AS repairId,
                    rm.repair_request_id AS repairRequestId,
                    rm.technician_id AS technicianId,
                    rm.repair_price AS repairPrice,
                    rm.payment_status AS paymentStatus,
                    rm.repair_notes AS repairNotes,
                    rm.created_at AS createdAt,
                    -- 2. 关联维修单表（yjx_repair_request）获取手机型号和维修状态
                    rr.phone_model AS phoneModel,
                    -- 维修状态转文字：根据实际枚举值调整（如 1 = 待维修，2 = 维修中，3/4 = 已完成）
                    CASE rr.request_status
                        WHEN 1 THEN '待维修'
                        WHEN 2 THEN '维修中'
                        WHEN 3 THEN '已完成'
                        WHEN 4 THEN '已完成'
                        ELSE '未知状态' END AS statusName,
                    -- 3. 关联用户表（yjx_user）获取报修人姓名
                    u.user_name AS userName
                FROM yjx_repair_management rm
                -- 第一次关联：维修管理表 → 维修单表（通过订单 ID 关联）
                LEFT JOIN yjx_repair_request rr
                    ON rm.repair_request_id = rr.request_id
                -- 第二次关联：维修单表 → 用户表（通过用户 ID 关联，获取用户名）
                LEFT JOIN yjx_user u
                    ON rr.user_id = u.user_id
                LEFT JOIN yjx_user u2 ON #{userId} = u2.user_id
                WHERE 1 = 1
                -- 权限过滤：与你之前的逻辑一致（roleId = 1/3查全部，其他查自己关联的）
                AND ( u2.role_id IN (1, 3, 4) OR u.user_id = #{userId} )
                -- 搜索关键词：模糊匹配手机型号、维修描述、用户名（前端可能按这些字段搜索）
                AND (
                    rr.phone_model LIKE CONCAT('%', #{searchKeyword}, '%')
                    OR rm.repair_notes LIKE CONCAT('%', #{searchKeyword}, '%')
                    OR u.user_name LIKE CONCAT('%', #{searchKeyword}, '%')
                )
                -- 排序：与你之前的逻辑一致（支持按创建时间、维修 ID 排序）
                ORDER BY
                    CASE WHEN #{sortField} IS NOT NULL AND #{sortOrder} IS NOT NULL
                         THEN CASE #{sortField}
                              WHEN 'createdAt' THEN rm.created_at
                              WHEN 'repairId' THEN rm.repair_id
                              WHEN 'userName' THEN u.user_name
                              ELSE rm.created_at END
                    ELSE rm.created_at END
                    -- 排序方向：默认降序
                    ${sortOrder == 'desc' ? 'DESC' : 'ASC'}
            """)
    IPage<Management> selectAllRepairManagement(Page<Management> page,
                                                @Param("userId") Integer userId,
                                                @Param("searchKeyword") String searchKeyword,
                                                @Param("sortField") String sortField,
                                                @Param("sortOrder") String sortOrder
    );

    /**
     * 更新维修管理
     *
     * @param repairId      维修管理 ID
     * @param repairPrice   维修价格
     * @param paymentStatus 支付状态
     * @param repairNotes   维修备注
     * @param technicianId  维修技术人员 ID
     * @param updatedAt     更新时间
     * @return 是否更新成功
     */
    @Update("""
                UPDATE yjx_repair_management
                SET
                    repair_price = #{repairPrice},
                    payment_status = #{paymentStatus},
                    repair_notes = #{repairNotes},
                    technician_id = #{technicianId},
                    updated_at = #{updatedAt}
                WHERE repair_id = #{repairId}
            """)
    boolean updateRepairManagementById(@Param(value = "repairId") Integer repairId,
                                       @Param(value = "repairPrice") Double repairPrice,
                                       @Param(value = "paymentStatus") String paymentStatus,
                                       @Param(value = "repairNotes") String repairNotes,
                                       @Param(value = "technicianId") Integer technicianId,
                                       @Param(value = "updatedAt") LocalDateTime updatedAt);

    /**
     * 删除维修管理
     *
     * @param repairId 维修管理 ID
     * @return 是否删除成功
     */
    @Delete("""
                DELETE FROM yjx_repair_management
                WHERE repair_id = #{repairId}
            """)
    Integer deleteRepairManagementById(@Param(value = "repairId") Integer repairId);

    /**
     * 创建维修管理并更新对应的维修请求状态
     *
     * @param repairRequestId 维修请求 ID
     * @param repairPrice     维修价格
     * @param paymentStatus   支付状态
     * @param repairNotes     维修备注
     * @param technicianId    维修技术人员 ID
     * @param createdAt       创建时间
     * @param updatedAt       更新时间
     * @return 是否创建成功
     */
    @Insert("""
                INSERT INTO
                    yjx_repair_management (repair_request_id, repair_price, payment_status, repair_notes, technician_id, created_at, updated_at)
                VALUES (#{repairRequestId}, #{repairPrice}, #{paymentStatus}, #{repairNotes}, #{technicianId}, #{createdAt}, #{updatedAt})
            """)
    Integer insertRepairManagement(@Param("repairRequestId") Integer repairRequestId,
                                   @Param("repairPrice") Double repairPrice,
                                   @Param("paymentStatus") String paymentStatus,
                                   @Param("repairNotes") String repairNotes,
                                   @Param("technicianId") Integer technicianId,
                                   @Param("createdAt") LocalDateTime createdAt,
                                   @Param("updatedAt") LocalDateTime updatedAt);

    /**
     * 更新对应维修请求的状态
     *
     * @param repairRequestId 维修请求 ID
     * @return 是否更新成功
     */
    @Update("""
                UPDATE yjx_repair_request
                SET request_status = 1
                WHERE request_id = #{repairRequestId}
            """)
    Integer updateRepairRequestStatusByRepairRequestId(@Param("repairRequestId") Integer repairRequestId);

    /**
     * 获取所有维修技术人员
     *
     * @return Technician列表
     */
    @Select("""
                SELECT
                    user_id AS userId,
                    user_name AS technicianName
                FROM yjx_user
                WHERE role_id = 4
                GROUP BY user_id
            """)
    List<TechnicianVO> selectAllTechnician();
}
