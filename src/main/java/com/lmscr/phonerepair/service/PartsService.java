package com.lmscr.phonerepair.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lmscr.phonerepair.module.AddPart;
import com.lmscr.phonerepair.module.PartsListQueryModule;
import com.lmscr.phonerepair.module.PartsSupplier;
import com.lmscr.phonerepair.module.UpdatePart;
import com.lmscr.phonerepair.pojo.Parts;
import com.lmscr.phonerepair.util.Result;

import java.util.List;
import java.util.Map;

public interface PartsService extends IService<Parts> {
    /**
     * 获取配件列表
     *
     * @param partsListQueryModule 配件列表查询模块
     * @return 配件列表
     */
    Result<Map<String, Object>> getPartsList(PartsListQueryModule partsListQueryModule);

    /**
     * 更新配件
     *
     * @param updatePart 更新配件模块
     * @return 更新结果
     */
    Result<String> updatePartByPartId(UpdatePart updatePart);

    /**
     * 删除配件
     *
     * @param partId 配件ID
     * @return 删除结果
     */
    Result<String> deletePartByPartId(Integer partId);

    /**
     * 添加配件
     *
     * @param addPart 添加配件模块
     * @return 添加结果
     */
    Result<String> addPart(AddPart addPart);

    /**
     * 获取供应商列表
     *
     * @return 供应商列表
     */
    Result<List<PartsSupplier>> getSupplierList();
}
