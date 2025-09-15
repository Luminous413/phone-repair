package com.lmscr.phonerepair.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lmscr.phonerepair.pojo.Supplier;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;

public interface SupplierMapper extends BaseMapper<Supplier> {
    /**
     * 分页查询所有供应商信息
     *
     * @param page          分页对象
     * @param searchKeyword 搜索关键词
     * @param sortField     排序字段
     * @param sortOrder     排序顺序
     * @return 分页结果
     */
    @Select("""
                SELECT
                    s.supplier_management_id AS supplierManagementId,
                    s.supplier_id AS supplierId,
                    s.part_id AS partId,
                    s.supply_quantity AS supplyQuantity,
                    s.created_at AS createdAt,
                    u.user_name AS supplierName,
                    p.part_name AS partName
                FROM
                    yjx_supplier_management s
                LEFT JOIN yjx_user u ON s.supplier_id = u.user_id
                LEFT JOIN yjx_parts p ON s.part_id = p.part_id
                WHERE 1 = 1
                AND (u.user_name LIKE CONCAT('%', #{searchKeyword}, '%')
                    OR p.part_name LIKE CONCAT('%', #{searchKeyword}, '%'))
                ORDER BY
                    CASE WHEN #{sortField} IS NOT NULL AND #{sortOrder} IS NOT NULL
                         THEN CASE #{sortField}
                              WHEN 'supplierManagementId' THEN s.supplier_management_id
                              WHEN 'supplierId' THEN s.supplier_id
                              WHEN 'partId' THEN s.part_id
                              WHEN 'createdAt' THEN s.created_at
                              ELSE s.created_at END
                    ELSE s.created_at END
                    -- 排序方向：默认降序
                    ${sortOrder == 'desc' ? 'DESC' : 'ASC'}
            """)
    IPage<Supplier> getAllWithPage(Page<Supplier> page,
                                   @Param("searchKeyword") String searchKeyword,
                                   @Param("sortField") String sortField,
                                   @Param("sortOrder") String sortOrder);

    /**
     * 创建供应商管理
     *
     * @param supplierId     供应商 ID
     * @param partId         零件 ID
     * @param supplyQuantity 供应商数量
     * @param createdAt      创建时间
     * @param updatedAt      更新时间
     * @return 是否创建成功
     */
    @Insert("""
                INSERT INTO
                    yjx_supplier_management (supplier_id, part_id, supply_quantity, created_at, updated_at)
                VALUES
                    (#{supplierId}, #{partId}, #{supplyQuantity}, #{createdAt}, #{updatedAt})
            """)
    boolean createSupplierManagement(@Param("supplierId") Integer supplierId,
                                     @Param("partId") Integer partId,
                                     @Param("supplyQuantity") Integer supplyQuantity,
                                     @Param("createdAt") LocalDateTime createdAt,
                                     @Param("updatedAt") LocalDateTime updatedAt);

    /**
     * 更新供应商管理
     *
     * @param supplierManagementId 供应商管理 ID
     * @param supplierId           供应商 ID
     * @param partId               零件 ID
     * @param supplyQuantity       供应商数量
     * @param updatedAt            更新时间
     * @return 是否更新成功
     */
    @Update("""
                UPDATE
                    yjx_supplier_management
                SET
                    supplier_id = #{supplierId},
                    part_id = #{partId},
                    supply_quantity = #{supplyQuantity},
                    updated_at = #{updatedAt}
                WHERE
                    supplier_management_id = #{supplierManagementId}
            """)
    boolean updateSupplierManagement(@Param("supplierManagementId") Integer supplierManagementId,
                                     @Param("supplierId") Integer supplierId,
                                     @Param("partId") Integer partId,
                                     @Param("supplyQuantity") Integer supplyQuantity,
                                     @Param("updatedAt") LocalDateTime updatedAt);

    @Delete("""
                DELETE
                FROM
                    yjx_supplier_management
                WHERE
                    supplier_management_id = #{supplierManagementId}
            """)
    boolean deleteSupplierManagement(@Param("supplierManagementId") Integer supplierManagementId);
}
