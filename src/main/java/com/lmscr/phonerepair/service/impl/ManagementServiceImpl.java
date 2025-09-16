package com.lmscr.phonerepair.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lmscr.phonerepair.mapper.ManagementMapper;
import com.lmscr.phonerepair.module.CreateRepairManagement;
import com.lmscr.phonerepair.module.RepairManagement;
import com.lmscr.phonerepair.module.TechnicianVO;
import com.lmscr.phonerepair.module.UpdateRepairManagement;
import com.lmscr.phonerepair.pojo.Management;
import com.lmscr.phonerepair.service.ManagementService;
import com.lmscr.phonerepair.service.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ManagementServiceImpl extends ServiceImpl<ManagementMapper, Management> implements ManagementService {
    /**
     * 自动注入维修管理映射器
     */
    @Autowired
    private ManagementMapper managementMapper;

    /**
     * 获取所有报修管理信息
     *
     * @param repairManagement 报修管理信息
     * @return 报修管理信息列表
     */
    @Override
    public Result<Map<String, Object>> getAllRepairManagement(RepairManagement repairManagement) {
        // 分页参数处理
        if (repairManagement.getPageNum() == null) {
            repairManagement.setPageNum(1);
        }
        if (repairManagement.getPageSize() == null) {
            repairManagement.setPageSize(10);
        }
        // 排序参数处理
        if (repairManagement.getSortField() == null) {
            repairManagement.setSortField("createdAt");
        }
        if (repairManagement.getSortOrder() == null) {
            repairManagement.setSortOrder("desc");
        }
        // 处理关键字
        if (repairManagement.getSearchKeyword() == null) {
            repairManagement.setSearchKeyword("");
        }

        // 分页
        Page<Management> page = new Page<>(repairManagement.getPageNum(), repairManagement.getPageSize());
        IPage<Management> pageResult = managementMapper.selectAllRepairManagement(
                page,
                repairManagement.getUserId(),
                repairManagement.getSearchKeyword(),
                repairManagement.getSortField(),
                repairManagement.getSortOrder()
        );
        // 分页结果处理
        Map<String, Object> pageResultMap = new HashMap<>();
        pageResultMap.put("count", pageResult.getTotal());
        pageResultMap.put("repairManagementList", pageResult.getRecords());

        // 判断是否查询成功
        if (pageResult.getRecords().isEmpty()) {
            return Result.fail("查询失败，未找到相关报修信息", 404);
        }
        return Result.success("查询成功", pageResultMap);
    }

    /**
     * 更新维修管理
     *
     * @param updateRepairManagement 更新维修管理
     * @return 是否更新成功
     */
    @Override
    public boolean updateRepairManagementById(UpdateRepairManagement updateRepairManagement) {
        return managementMapper.updateRepairManagementById(
                updateRepairManagement.getRepairId(),
                updateRepairManagement.getRepairPrice(),
                updateRepairManagement.getPaymentStatus(),
                updateRepairManagement.getRepairNotes(),
                updateRepairManagement.getTechnicianId(),
                LocalDateTime.now()
        );
    }

    /**
     * 删除维修管理
     *
     * @param repairId 维修管理 ID
     * @return 是否删除成功
     */
    @Override
    public boolean deleteRepairManagementById(Integer repairId) {
        return managementMapper.deleteRepairManagementById(repairId) > 0;
    }

    /**
     * 创建维修管理
     *
     * @param createRepairManagement 创建维修管理
     * @return 是否创建成功
     */
    @Override
    public boolean createRepairManagement(CreateRepairManagement createRepairManagement) {
        return managementMapper.insertRepairManagement(
                createRepairManagement.getRepairRequestId(),
                100.00,
                "待支付",
                createRepairManagement.getRepairNotes(),
                createRepairManagement.getTechnicianId(),
                LocalDateTime.now(),
                LocalDateTime.now()
        ) > 0 && managementMapper.updateRepairRequestStatusByRepairRequestId(createRepairManagement.getRepairRequestId()) > 0;
    }

    /**
     * 获取所有维修技术人员
     *
     * @return Technician列表
     */
    @Override
    public Result<List<TechnicianVO>> getAllTechnician() {
        List<TechnicianVO> technicianVOList = managementMapper.selectAllTechnician();
        if (technicianVOList.isEmpty()) {
            return Result.fail("查询失败，没有查询到技术人员", 404);
        }
        return Result.success("查询成功", technicianVOList);
    }
}
