package com.lmscr.testspring.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lmscr.testspring.mapper.RepairMapper;
import com.lmscr.testspring.module.ReceptionistVO;
import com.lmscr.testspring.module.RepairQueryModule;
import com.lmscr.testspring.pojo.Repair;
import com.lmscr.testspring.pojo.User;
import com.lmscr.testspring.service.RepairService;
import com.lmscr.testspring.service.UserService;
import com.lmscr.testspring.service.util.Md5Password;
import com.lmscr.testspring.service.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 维修记录服务实现类
 * 提供维修记录的查询、创建、更新、删除等操作
 * 实现了 RepairService 接口，定义了具体的业务逻辑
 * 包括查询维修记录列表、根据条件查询维修记录列表、创建维修记录、更新维修记录、删除维修记录等操作
 * 依赖于 RepairMapper 和 RepairService 接口
 */
@Service
public class RepairServiceImpl extends ServiceImpl<RepairMapper, Repair> implements RepairService {
    /**
     * 维修记录映射器，用于数据库操作
     */
    @Autowired
    private RepairMapper repairMapper;

    /**
     * 用户服务，用于查询用户信息
     */
    @Autowired
    private UserService userService;

    /**
     * 根据条件查询维修记录列表
     *
     * @param repairQueryModule 维修记录查询模块，包含查询条件
     * @return 包含维修记录列表和分页信息的结果对象
     */
    @Override
    public Result<Map<String, Object>> getRepairListByCondition(RepairQueryModule repairQueryModule) {
        // 处理分页参数（页码默认 1，页大小默认 10）
        int pageNum = repairQueryModule.getPageNum() == null ? 1 : repairQueryModule.getPageNum();
        int pageSize = repairQueryModule.getPageSize() == null ? 10 : repairQueryModule.getPageSize();

        // 处理搜索参数（搜索关键词默认为空字符串）
        String searchKeyword = repairQueryModule.getSearchKeyword() == null ? "" : repairQueryModule.getSearchKeyword();
        // 处理排序字段（默认按创建时间排序）
        String sortFiled = repairQueryModule.getSortField() == null ? "created_at" : repairQueryModule.getSortField();
        // 处理排序顺序（默认降序排列）
        String sortOrder = repairQueryModule.getSortOrder() == null ? "desc" : repairQueryModule.getSortOrder();

        // 创建 MyBatis-Plus 分页对象
        Page<Repair> page = new Page<>(pageNum, pageSize);

        // 执行分页查询，获取维修记录列表
        IPage<Repair> pageResult = repairMapper.selectRepairByCondition(
                page,
                repairQueryModule.getUserId(),
                searchKeyword,
                sortFiled,
                sortOrder
        );

        // 遍历维修记录列表，查询每个记录的维修状态名称
        pageResult.getRecords().forEach(repair -> {
            // 维修状态
            repair.setRequestStatusName(repairMapper.getRepairStatus(repair.getRequestStatus()));
        });

        // 构建返回结果数据结构
        Map<String, Object> responseMap = new HashMap<>();
        // 总记录数
        responseMap.put("count", pageResult.getTotal());
        // 当前页数据列表
        responseMap.put("repairRequest", pageResult.getRecords());

        return Result.success("查询成功", responseMap);
    }

    /**
     * 获取所有维修接待员
     *
     * @return 包含所有维修接待员信息的列表
     */
    @Override
    public Result<List<ReceptionistVO>> getAllReceptionist() {
        List<ReceptionistVO> receptionistVOList = repairMapper.getAllReceptionistList();
        if (receptionistVOList == null || receptionistVOList.isEmpty()) {
            return Result.fail("查询失败", 500);
        }
        return Result.success("查询成功", receptionistVOList);
    }

    /**
     * 创建维修记录
     *
     * @param repair 维修记录对象，包含维修请求的详细信息
     * @return 创建成功返回true，否则返回false
     */
    @Override
    public boolean createRepair(Repair repair) {
        // 参数的非空校验
        if (repair.getUserId() == null || repair.getPhoneModel() == null || repair.getPhoneIssueDescription() == null) {
            return false;
        }

        // 设置维修请求初始状态为 1（通常表示"待处理"状态）
        repair.setRequestStatus(1);
        // 记录创建时间为当前系统时间
        repair.setCreatedAt(LocalDateTime.now());
        // 记录最后更新时间为当前系统时间（新建时与创建时间一致）
        repair.setUpdatedAt(LocalDateTime.now());

        return save(repair);
    }

    /**
     * 删除维修记录
     *
     * @param repairId 维修记录 ID
     * @param userId   用户 ID
     * @param password 用户密码
     * @return 删除成功返回 true，否则返回 false
     */
    @Override
    public boolean deleteRepair(Integer repairId, Integer userId, String password) {
        // 判断是否存在
        if (repairId == null) {
            return false;
        }
        // 判断 userId 是否存在
        if (userId == null) {
            return false;
        }
        // 校验用户是否存在
        User user = userService.getById(userId);
        if (user == null) {
            return false;
        }
        // 校验 MD5 加密的密码
        if (!Md5Password.generateMD5(password).equals(user.getUserPasswordHash())) {
            return false;
        }
        return repairMapper.deleteRepairByIdAndUserId(repairId, userId) > 0;
    }
}
