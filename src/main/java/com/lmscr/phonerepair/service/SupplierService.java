package com.lmscr.phonerepair.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lmscr.phonerepair.module.CurrentSupplierManagement;
import com.lmscr.phonerepair.module.NewSupplierManagement;
import com.lmscr.phonerepair.module.SupplierManagementQueryModule;
import com.lmscr.phonerepair.pojo.Supplier;
import com.lmscr.phonerepair.util.Result;

import java.util.Map;

public interface SupplierService extends IService<Supplier> {
    /**
     * 分页查询所有供应商信息
     *
     * @param supplierManagementQueryModule 供应商管理查询模块
     * @return 分页结果
     */
    Result<Map<String, Object>> getAllSupplierManagement(SupplierManagementQueryModule supplierManagementQueryModule);

    /**
     * 创建供应商管理
     *
     * @param newSupplierManagement 新供应商管理
     * @return 结果
     */
    Result<String> createSupplierManagement(NewSupplierManagement newSupplierManagement);

    /**
     * 更新供应商管理
     *
     * @param currentSupplierManagement 当前供应商管理
     * @return 结果
     */
    Result<String> updateSupplierManagement(CurrentSupplierManagement currentSupplierManagement);

    /**
     * 删除供应商管理
     *
     * @param supplierManagementId 供应商管理 ID
     * @param userId               用户 ID
     * @param userPasswd           用户密码
     * @return 结果
     */
    Result<String> deleteSupplierManagement(Integer supplierManagementId, Integer userId, String userPasswd);
}
