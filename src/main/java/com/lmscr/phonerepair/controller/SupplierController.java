package com.lmscr.phonerepair.controller;

import com.lmscr.phonerepair.module.CurrentSupplierManagement;
import com.lmscr.phonerepair.module.NewSupplierManagement;
import com.lmscr.phonerepair.module.SupplierManagementQueryModule;
import com.lmscr.phonerepair.service.SupplierService;
import com.lmscr.phonerepair.service.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/supplier")
public class SupplierController {
    @Autowired
    private SupplierService supplierService;

    /**
     * 分页查询所有供应商信息
     *
     * @param supplierManagementQueryModule 供应商管理查询模块
     * @return 分页结果
     */
    @GetMapping("/getAllSupplierManagement")
    public Result<Map<String, Object>> getAllSupplierManagement(SupplierManagementQueryModule supplierManagementQueryModule) {
        return supplierService.getAllSupplierManagement(supplierManagementQueryModule);
    }

    /**
     * 创建供应商管理
     *
     * @param newSupplierManagement 新供应商管理
     * @return 结果
     */
    @PostMapping("/createSupplierManagement")
    public Result<String> createSupplierManagement(@RequestBody NewSupplierManagement newSupplierManagement) {
        return supplierService.createSupplierManagement(newSupplierManagement);
    }

    /**
     * 更新供应商管理
     *
     * @param currentSupplierManagement 当前供应商管理
     * @return 结果
     */
    @PostMapping("/updateSupplierManagement")
    public Result<String> updateSupplierManagement(@RequestBody CurrentSupplierManagement currentSupplierManagement) {
        return supplierService.updateSupplierManagement(currentSupplierManagement);
    }

    @PostMapping("/deleteSupplierManagement")
    public Result<String> deleteSupplierManagement(@RequestParam(value = "supplierManagementId") Integer supplierManagementId,
                                                   @RequestParam(value = "userId") Integer userId,
                                                   @RequestParam(value = "userPasswd") String userPasswd) {
        return supplierService.deleteSupplierManagement(supplierManagementId, userId, userPasswd);
    }
}
