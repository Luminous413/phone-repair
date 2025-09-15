package com.lmscr.phonerepair.controller;

import com.lmscr.phonerepair.module.ReceptionistVO;
import com.lmscr.phonerepair.module.RepairQueryModule;
import com.lmscr.phonerepair.pojo.Repair;
import com.lmscr.phonerepair.service.RepairService;
import com.lmscr.phonerepair.service.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/repair")
public class RepairController {
    /**
     * 自动注入维修服务层实现
     */
    @Autowired
    private RepairService repairService;

    /**
     * 分页查询所有维修工单
     *
     * @param repairQueryModule 包含分页参数、筛选条件和排序规则的查询对象
     * @return 统一响应格式，包含总记录数和当前页数据列表
     */
    @RequestMapping("/getAllRepair")
    public Result<Map<String, Object>> getAllRepair(RepairQueryModule repairQueryModule) {
        return repairService.getRepairListByCondition(repairQueryModule);
    }

    /**
     * 获取所有接待员
     *
     * @return 统一响应格式，包含所有接待员的列表
     */
    @RequestMapping("/getAllReceptionist")
    public Result<List<ReceptionistVO>> getAllReceptionist() {
        return repairService.getAllReceptionist();
    }

    /**
     * 创建新的维修工单
     *
     * @param repair 通过请求体传递的维修工单对象（JSON格式）
     * @return 统一响应格式，成功返回创建消息，失败返回错误信息及 500 状态码
     */
    @PostMapping("/createRepair")
    public Result<String> createRepair(@RequestBody Repair repair) {
        boolean isCreated = repairService.createRepair(repair);
        if (isCreated) {
            return Result.success("创建成功", "");
        } else {
            // 500 状态码表示参数不完整或系统异常
            return Result.fail("创建失败", 500);
        }
    }

    /**
     * 删除维修工单
     *
     * @param repairId 维修工单 ID
     * @param userId   用户 ID
     * @param password 用户密码
     * @return 统一响应格式，成功返回删除消息，失败返回错误信息及 500 状态码
     */
    @PostMapping("/deleteRepair")
    public Result<String> deleteRepair(@RequestParam(value = "repairId") Integer repairId,
                                       @RequestParam(value = "userId") Integer userId,
                                       @RequestParam(value = "password") String password) {
        boolean isDeleted = repairService.deleteRepair(repairId, userId, password);
        if (isDeleted) {
            return Result.success("删除成功", "");
        } else {
            return Result.fail("删除失败", 500);
        }
    }
}
