package com.lmscr.phonerepair.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lmscr.phonerepair.module.CreateRepairManagement;
import com.lmscr.phonerepair.module.RepairManagement;
import com.lmscr.phonerepair.module.UpdateRepairManagement;
import com.lmscr.phonerepair.pojo.Management;
import com.lmscr.phonerepair.module.TechnicianVO;
import com.lmscr.phonerepair.service.util.Result;

import java.util.List;
import java.util.Map;

public interface ManagementService extends IService<Management> {
    /**
     * 获取所有维修管理列表
     *
     * @param repairManagement 维修管理查询条件
     * @return 维修管理列表
     */
    Result<Map<String, Object>> getAllRepairManagement(RepairManagement repairManagement);

    /**
     * 更新维修管理
     *
     * @param updateRepairManagement 更新维修管理
     * @return 是否更新成功
     */
    boolean updateRepairManagementById(UpdateRepairManagement updateRepairManagement);

    /**
     * 删除维修管理
     *
     * @param repairId   维修管理 ID
     * @return 是否删除成功
     */
    boolean deleteRepairManagementById(Integer repairId);

    /**
     * 创建维修管理
     *
     * @param createRepairManagement 创建维修管理
     * @return 是否创建成功
     */
    boolean createRepairManagement(CreateRepairManagement createRepairManagement);

    /**
     * 获取所有维修技术人员
     *
     * @return Technician列表
     */
    Result<List<TechnicianVO>> getAllTechnician();
}
