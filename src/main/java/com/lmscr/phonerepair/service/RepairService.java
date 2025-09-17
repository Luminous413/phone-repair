package com.lmscr.phonerepair.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lmscr.phonerepair.module.ReceptionistVO;
import com.lmscr.phonerepair.module.RepairQueryModule;
import com.lmscr.phonerepair.pojo.Repair;
import com.lmscr.phonerepair.util.Result;

import java.util.List;
import java.util.Map;

/**
 * 报修服务接口
 */
public interface RepairService extends IService<Repair> {

    /**
     * 根据条件查询报修列表
     *
     * @param repairQueryModule 报修查询模块
     * @return 报修列表
     */
    Result<Map<String, Object>> getRepairListByCondition(RepairQueryModule repairQueryModule);

    /**
     * 获取所有接待员
     *
     * @return 接待员列表
     */
    Result<List<ReceptionistVO>> getAllReceptionist();

    /**
     * 创建报修
     *
     * @param repair 报修信息
     * @return 是否创建成功
     */
    boolean createRepair(Repair repair);

    /**
     * 删除报修
     *
     * @param repairId 报修 ID
     * @param userId   用户 ID
     * @param password 用户密码
     * @return 是否删除成功
     */
    boolean deleteRepair(Integer repairId, Integer userId, String password);
}
