package com.lmscr.testspring.controller;

import com.lmscr.testspring.module.CreateRepairManagement;
import com.lmscr.testspring.module.RepairManagement;
import com.lmscr.testspring.module.TechnicianVO;
import com.lmscr.testspring.module.UpdateRepairManagement;
import com.lmscr.testspring.service.ManagementService;
import com.lmscr.testspring.service.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/management")
public class ManagementController {
    /**
     * 自动注入维修管理服务
     */
    @Autowired
    private ManagementService managementService;

    /**
     * 获取所有维修管理列表
     *
     * @param repairManagement 维修管理查询条件
     * @return 维修管理列表
     */
    @GetMapping("/getAllRepairManagement")
    public Result<Map<String, Object>> getAllRepairManagement(RepairManagement repairManagement) {
        return managementService.getAllRepairManagement(repairManagement);
    }

    /**
     * 更新维修管理
     *
     * @param updateRepairManagement 更新维修管理
     * @return 是否更新成功
     */
    @PostMapping("/updateRepairManagement")
    public Result<String> updateRepairManagement(@RequestBody UpdateRepairManagement updateRepairManagement) {
        boolean isSuccess = managementService.updateRepairManagementById(updateRepairManagement);
        if (isSuccess) {
            return Result.success("更新成功", "");
        } else {
            return Result.fail("更新失败", 500);
        }
    }

    /**
     * 删除维修管理
     *
     * @param repairId   维修管理 ID
     * @param userId     用户 ID
     * @param userPasswd 用户密码
     * @return 是否删除成功
     */
    @PostMapping("/deleteRepairManagement")
    public Result<String> deleteRepairManagement(@RequestParam(value = "repairId") Integer repairId,
                                                 @RequestParam(value = "userId") Integer userId,
                                                 @RequestParam(value = "userPasswd") String userPasswd) {
        boolean isSuccess = managementService.deleteRepairManagementById(repairId);
        if (isSuccess) {
            return Result.success("删除成功", "");
        } else {
            return Result.fail("删除失败", 500);
        }
    }

    /**
     * 创建维修管理
     *
     * @param createRepairManagement 创建维修管理
     * @return 是否创建成功
     */
    @PostMapping("/createRepairManagement")
    public Result<String> createRepairManagement(@RequestBody CreateRepairManagement createRepairManagement) {
        boolean isSuccess = managementService.createRepairManagement(createRepairManagement);
        if (isSuccess) {
            return Result.success("创建成功", "");
        } else {
            return Result.fail("创建失败", 500);
        }
    }

    /**
     * 获取所有维修技术人员
     *
     * @return Technician列表
     */
    @GetMapping("/getAllTechnician")
    public Result<List<TechnicianVO>> getAllTechnician() {
        return managementService.getAllTechnician();
    }
}
