package com.lmscr.testspring.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lmscr.testspring.module.PartsSupplier;
import com.lmscr.testspring.pojo.Parts;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PartsMapper extends BaseMapper<Parts> {
    /**
     * 获取所有配件列表
     *
     * @param page          分页信息
     * @param userRole      用户角色
     * @param searchKeyword 搜索关键词
     * @param sortField     排序字段
     * @param sortPart      排序字段
     * @return 配件列表
     */
    @Select("""
                SELECT
                    part_id AS partId,
                    part_name AS partName,
                    part_description AS partDescription,
                    part_price AS partPrice,
                    stock_quantity AS stockQuantity,
                    supplier_id AS supplierId,
                    created_at AS createdAt
                FROM
                    yjx_parts
                WHERE 1 = 1
                AND (#{userRole} IN (1, 3, 4))
                AND (part_name LIKE CONCAT('%', #{searchKeyword}, '%')
                    OR part_description LIKE CONCAT('%', #{searchKeyword}, '%'))
                ORDER BY
                    CASE WHEN #{sortField} IS NOT NULL AND #{sortPart} IS NOT NULL
                         THEN CASE #{sortField}
                              WHEN 'createdAt' THEN created_at
                              WHEN 'partId' THEN part_id
                              ELSE created_at END
                    ELSE created_at END
                    -- 排序方向：默认降序
                    ${sortPart == 'desc' ? 'DESC' : 'ASC'}
            """)
    IPage<Parts> selectPartsList(Page<Parts> page,
                                 @Param("userRole") Integer userRole,
                                 @Param("searchKeyword") String searchKeyword,
                                 @Param("sortField") String sortField,
                                 @Param("sortPart") String sortPart);

    /**
     * 更新配件
     *
     * @param partId          配件 ID
     * @param partName        配件名称
     * @param partDescription 配件描述
     * @param partPrice       配件价格
     * @param stockQuantity   库存数量
     * @param supplierId      供应商 ID
     * @param updatedAt       更新时间
     * @return 更新结果
     */
    @Update("""
                UPDATE
                    yjx_parts
                SET
                    part_name = #{partName},
                    part_description = #{partDescription},
                    part_price = #{partPrice},
                    stock_quantity = #{stockQuantity},
                    supplier_id = #{supplierId},
                    updated_at = #{updatedAt}
                WHERE
                    part_id = #{partId}
            """)
    Integer updatePartById(@Param("partId") Integer partId,
                           @Param("partName") String partName,
                           @Param("partDescription") String partDescription,
                           @Param("partPrice") Double partPrice,
                           @Param("stockQuantity") Integer stockQuantity,
                           @Param("supplierId") Integer supplierId,
                           @Param("updatedAt") LocalDateTime updatedAt);

    /**
     * 删除配件
     *
     * @param partId 配件ID
     * @return 删除结果
     */
    @Delete("""
                DELETE FROM
                    yjx_parts
                WHERE
                    part_id = #{partId}
            """)
    Integer deletePartById(@Param("partId") Integer partId);

    /**
     * 添加配件
     *
     * @param partName        配件名称
     * @param partDescription 配件描述
     * @param partPrice       配件价格
     * @param stockQuantity   库存数量
     * @param supplierId      供应商 ID
     * @param createdAt       创建时间
     * @param updatedAt       更新时间
     * @return 添加结果
     */
    @Insert("""
                INSERT INTO
                    yjx_parts (part_name, part_description, part_price, stock_quantity, supplier_id, created_at, updated_at)
                VALUES
                    (#{partName}, #{partDescription}, #{partPrice}, #{stockQuantity}, #{supplierId}, #{createdAt}, #{updatedAt})
            """)
    Integer insertPart(@Param("partName") String partName,
                    @Param("partDescription") String partDescription,
                    @Param("partPrice") Double partPrice,
                    @Param("stockQuantity") Integer stockQuantity,
                    @Param("supplierId") Integer supplierId,
                    @Param("createdAt") LocalDateTime createdAt,
                    @Param("updatedAt") LocalDateTime updatedAt);

    /**
     * 获取所有供应商列表
     *
     * @return 供应商列表
     */
    @Select("""
                SELECT
                    user_id AS supplierId,
                    user_name AS supplierName
                FROM
                    yjx_user
                WHERE
                    role_id = 5
                ORDER BY
                    user_id
            """)
    List<PartsSupplier> getSupplierList();
}
