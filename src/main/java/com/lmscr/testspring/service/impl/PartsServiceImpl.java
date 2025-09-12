package com.lmscr.testspring.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lmscr.testspring.module.AddPart;
import com.lmscr.testspring.module.PartsListQueryModule;
import com.lmscr.testspring.module.PartsSupplier;
import com.lmscr.testspring.module.UpdatePart;
import com.lmscr.testspring.service.util.CheckParams;
import com.lmscr.testspring.service.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lmscr.testspring.mapper.PartsMapper;
import com.lmscr.testspring.pojo.Parts;
import com.lmscr.testspring.service.PartsService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

@Service
public class PartsServiceImpl extends ServiceImpl<PartsMapper, Parts> implements PartsService {
    /**
     * 配件映射器
     */
    @Autowired
    private PartsMapper partsMapper;

    /**
     * 获取配件列表
     *
     * @param partsListQueryModule 配件列表查询模块
     * @return 配件列表
     */
    @Override
    public Result<Map<String, Object>> getPartsList(PartsListQueryModule partsListQueryModule) {
        // 校验参数
        if (partsListQueryModule.getUserId() == null) {
            return Result.fail("用户ID不能为空", 400);
        }
        if (partsListQueryModule.getUserRole() == null) {
            return Result.fail("用户角色不能为空", 400);
        }
        CheckParams.checkParams(partsListQueryModule);

        // 分页查询
        Page<Parts> page = new Page<>(partsListQueryModule.getPageNum(), partsListQueryModule.getPageSize());
        IPage<Parts> partsIPage = partsMapper.selectPartsList(
                page,
                partsListQueryModule.getUserRole(),
                partsListQueryModule.getSearchKeyword(),
                partsListQueryModule.getSortField(),
                partsListQueryModule.getSortPart()
        );
        if (partsIPage.getRecords().isEmpty()) {
            return Result.fail("没有查询到数据", 404);
        }
        // 构建结果
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("total", partsIPage.getTotal());
        resultMap.put("records", partsIPage.getRecords());

        return Result.success("查询成功", resultMap);
    }

    /**
     * 更新配件
     *
     * @param updatePart 更新配件模块
     * @return 更新结果
     */
    @Override
    public Result<String> updatePartByPartId(UpdatePart updatePart) {
        // 校验参数
        if (updatePart.getPartName() == null) {
            updatePart.setPartName("");
        }
        if (updatePart.getPartDescription() == null) {
            updatePart.setPartDescription("");
        }
        if (updatePart.getPartPrice() == null) {
            updatePart.setPartPrice(0.00);
        }
        if (updatePart.getStockQuantity() == null) {
            updatePart.setStockQuantity(0);
        }
        if (updatePart.getSupplierId() == null) {
            return Result.fail("供应商ID不能为空", 400);
        }
        // 更新配件
        Integer updateResult = partsMapper.updatePartById(
                updatePart.getPartId(),
                updatePart.getPartName(),
                updatePart.getPartDescription(),
                updatePart.getPartPrice(),
                updatePart.getStockQuantity(),
                updatePart.getSupplierId(),
                LocalDateTime.now()
        );
        if (updateResult == 0) {
            return Result.fail("更新失败", 500);
        }
        return Result.success("更新成功", "更新成功");
    }

    /**
     * 删除配件
     *
     * @param partId 配件ID
     * @return 删除结果
     */
    @Override
    public Result<String> deletePartByPartId(Integer partId) {
        // 校验参数
        if (partId == null) {
            return Result.fail("配件ID不能为空", 400);
        }
        // 删除配件
        Integer deleteResult = partsMapper.deletePartById(partId);
        if (deleteResult == 0) {
            return Result.fail("删除失败", 500);
        }
        return Result.success("删除成功", "删除成功");
    }

    /**
     * 添加配件
     *
     * @param addPart 添加配件模块
     * @return 添加结果
     */
    @Override
    public Result<String> addPart(AddPart addPart) {
        // 添加配件
        Integer addResult = partsMapper.insertPart(
                addPart.getPartName(),
                "",
                addPart.getPartPrice(),
                addPart.getStockQuantity(),
                addPart.getSupplierId(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        if (addResult == 0) {
            return Result.fail("添加失败", 500);
        }
        return Result.success("添加成功", "添加成功");
    }

    /**
     * 获取供应商列表
     *
     * @return 供应商列表
     */
    @Override
    public Result<List<PartsSupplier>> getSupplierList() {
        List<PartsSupplier> supplierResult = partsMapper.getSupplierList();
        if (supplierResult.isEmpty()) {
            return Result.fail("没有查询到数据", 404);
        }
        return Result.success("查询成功", supplierResult);
    }
}
