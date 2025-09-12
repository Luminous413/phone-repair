package com.lmscr.testspring.controller;

import com.lmscr.testspring.module.AddPart;
import com.lmscr.testspring.module.PartsListQueryModule;
import com.lmscr.testspring.module.PartsSupplier;
import com.lmscr.testspring.module.UpdatePart;
import com.lmscr.testspring.service.PartsService;
import com.lmscr.testspring.service.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/parts")
public class PartsController {
    /**
     * 配件服务
     */
    @Autowired
    private PartsService partsService;

    /**
     * 获取配件列表
     *
     * @param partsListQueryModule 配件列表查询模块
     * @return 配件列表
     */
    @GetMapping("/list")
    public Result<Map<String, Object>> getPartsList(PartsListQueryModule partsListQueryModule) {
        return partsService.getPartsList(partsListQueryModule);
    }

    /**
     * 更新配件
     *
     * @param updatePart 更新配件模块
     * @return 更新结果
     */
    @PostMapping("/updatePart")
    public Result<String> updatePart(@RequestBody UpdatePart updatePart) {
        return partsService.updatePartByPartId(updatePart);
    }

    /**
     * 删除配件
     *
     * @param partId 配件 ID
     * @return 删除结果
     */
    @DeleteMapping("/delete/{partId}")
    public Result<String> deletePart(@PathVariable Integer partId) {
        return partsService.deletePartByPartId(partId);
    }

    /**
     * 添加配件
     *
     * @param addPart 添加配件模块
     * @return 添加结果
     */
    @PostMapping("/addPart")
    public Result<String> addPart(@RequestBody AddPart addPart) {
        return partsService.addPart(addPart);
    }

    @GetMapping("/getSupplierList")
    public Result<List<PartsSupplier>> getSupplierList() {
        return partsService.getSupplierList();
    }
}
