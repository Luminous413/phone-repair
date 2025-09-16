package com.lmscr.phonerepair.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lmscr.phonerepair.mapper.UserMapper;
import com.lmscr.phonerepair.module.CurrentSupplierManagement;
import com.lmscr.phonerepair.module.NewSupplierManagement;
import com.lmscr.phonerepair.module.SupplierManagementQueryModule;
import com.lmscr.phonerepair.pojo.User;
import com.lmscr.phonerepair.service.util.CheckParams;
import com.lmscr.phonerepair.service.util.Md5Password;
import com.lmscr.phonerepair.service.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lmscr.phonerepair.mapper.SupplierMapper;
import com.lmscr.phonerepair.pojo.Supplier;
import com.lmscr.phonerepair.service.SupplierService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class SupplierServiceImpl extends ServiceImpl<SupplierMapper, Supplier> implements SupplierService {
    /**
     * 用户映射器
     */
    @Autowired
    private UserMapper userMapper;

    /**
     * 供应商映射器
     */
    @Autowired
    private SupplierMapper supplierMapper;

    /**
     * 分页查询所有供应商信息
     *
     * @param supplierManagementQueryModule 供应商管理查询模块
     * @return 分页结果
     */
    @Override
    public Result<Map<String, Object>> getAllSupplierManagement(SupplierManagementQueryModule supplierManagementQueryModule) {
        // 参数校验
        CheckParams.checkParams(supplierManagementQueryModule);
        // 分页查询
        Page<Supplier> page = new Page<>(supplierManagementQueryModule.getPageNum(), supplierManagementQueryModule.getPageSize());
        IPage<Supplier> supplierIPage = supplierMapper.getAllWithPage(
                page,
                supplierManagementQueryModule.getUserId(),
                supplierManagementQueryModule.getSearchKeyword(),
                supplierManagementQueryModule.getSortField(),
                supplierManagementQueryModule.getSortOrder()
        );
        if (supplierIPage.getRecords().isEmpty()) {
            return Result.fail("暂无数据", 404);
        }
        // 封装结果
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("count", supplierIPage.getTotal());
        resultMap.put("supplierManagementList", supplierIPage.getRecords());

        return Result.success("查询成功", resultMap);
    }

    /**
     * 创建供应商管理
     *
     * @param newSupplierManagement 新供应商管理
     * @return 结果
     */
    @Override
    public Result<String> createSupplierManagement(NewSupplierManagement newSupplierManagement) {
        // 参数校验
        if (newSupplierManagement.getSupplierId() == null) {
            return Result.fail("供应商ID不能为空", 400);
        }
        if (newSupplierManagement.getPartId() == null) {
            return Result.fail("零件ID不能为空", 400);
        }
        if (newSupplierManagement.getSupplyQuantity() == null) {
            return Result.fail("供应量不能为空", 400);
//            newSupplierManagement.setSupplyQuantity(0);
        }

        boolean isCreate = supplierMapper.createSupplierManagement(
                newSupplierManagement.getSupplierId(),
                newSupplierManagement.getPartId(),
                newSupplierManagement.getSupplyQuantity(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        if (isCreate) {
            return Result.success("新增成功", "");
        }
        return Result.fail("新增失败", 500);
    }

    /**
     * 更新供应商管理
     *
     * @param currentSupplierManagement 当前供应商管理
     * @return 结果
     */
    @Override
    public Result<String> updateSupplierManagement(CurrentSupplierManagement currentSupplierManagement) {
        // 参数校验
        if (currentSupplierManagement.getSupplierId() == null) {
            return Result.fail("供应商ID不能为空", 400);
        }
        if (currentSupplierManagement.getPartId() == null) {
            return Result.fail("零件ID不能为空", 400);
        }
        if (currentSupplierManagement.getSupplyQuantity() == null) {
            return Result.fail("供应量不能为空", 400);
        }
        boolean isUpdate = supplierMapper.updateSupplierManagement(
                currentSupplierManagement.getSupplierManagementId(),
                currentSupplierManagement.getSupplierId(),
                currentSupplierManagement.getPartId(),
                currentSupplierManagement.getSupplyQuantity(),
                LocalDateTime.now()
        );
        if (isUpdate) {
            return Result.success("更新成功", "");
        }
        return Result.fail("更新失败", 500);
    }

    /**
     * 删除供应商管理
     *
     * @param supplierManagementId 供应商管理 ID
     * @param userId               用户 ID
     * @param userPasswd           用户密码
     * @return 结果
     */
    @Override
    public Result<String> deleteSupplierManagement(Integer supplierManagementId, Integer userId, String userPasswd) {
        // 参数校验
        if (userId == null) {
            return Result.fail("用户ID不能为空", 400);
        }
        User user = userMapper.selectById(userId);
        if (user == null) {
            return Result.fail("用户不存在", 400);
        }
        if (!user.getUserPasswordHash().equals(Md5Password.generateMD5(userPasswd))) {
            return Result.fail("密码错误", 400);
        }

        boolean isDelete = supplierMapper.deleteSupplierManagement(supplierManagementId);
        if (isDelete) {
            return Result.success("删除成功", "");
        }
        return Result.fail("删除失败", 500);
    }
}
