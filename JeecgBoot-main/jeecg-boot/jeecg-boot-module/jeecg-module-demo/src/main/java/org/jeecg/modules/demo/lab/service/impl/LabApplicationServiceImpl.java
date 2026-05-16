package org.jeecg.modules.demo.lab.service.impl;

import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.demo.lab.entity.LabApplication;
import org.jeecg.modules.demo.lab.entity.LabUseRecord;
import org.jeecg.modules.demo.lab.mapper.LabApplicationMapper;
import org.jeecg.modules.demo.lab.service.ILabApplicationService;
import org.jeecg.modules.demo.lab.service.ILabUseRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;

/**
 * @Description: 申请信息
 * @Author: jeecg-boot
 * @Date:   2026-05-08
 * @Version: V1.0
 */
@Service("labLabApplicationServiceImpl")
public class LabApplicationServiceImpl extends ServiceImpl<LabApplicationMapper, LabApplication> implements ILabApplicationService {
    @Autowired
    private ILabUseRecordService labUseRecordService;

    @Override
    public boolean save(LabApplication entity) {
        // 默认状态
        entity.setStatus("pending");
        boolean saved = super.save(entity);
        if (saved) {
            syncUseRecord(entity);
        }
        return saved;
    }

    @Override
    public boolean updateById(LabApplication entity) {
        boolean updated = super.updateById(entity);
        if (updated) {
            LabApplication latest = this.getById(entity.getId());
            if (latest != null) {
                syncUseRecord(latest);
            }
        }
        return updated;
    }

    private void syncUseRecord(LabApplication source) {
        LambdaQueryWrapper<LabUseRecord> wrapper = new LambdaQueryWrapper<LabUseRecord>()
                .eq(LabUseRecord::getUserId, source.getUserId())
                .eq(LabUseRecord::getEquipmentId, source.getEquipmentId())
                .eq(LabUseRecord::getProjectId, source.getProjectId())
                .eq(LabUseRecord::getStartDate, source.getStartDate())
                .eq(LabUseRecord::getEndDate, source.getEndDate())
                .last("limit 1");
        if (StringUtils.isNotBlank(source.getPurpose())) {
            wrapper.eq(LabUseRecord::getPurpose, source.getPurpose());
        }
        LabUseRecord record = labUseRecordService.getOne(wrapper, false);
        if (record == null) {
            record = new LabUseRecord();
        }
        record.setUserId(source.getUserId());
        record.setEquipmentId(source.getEquipmentId());
        record.setProjectId(source.getProjectId());
        record.setStartDate(source.getStartDate());
        record.setEndDate(source.getEndDate());
        record.setPurpose(source.getPurpose());
        record.setActualHours(source.getActualHours());
        labUseRecordService.saveOrUpdate(record);
    }
}
