package org.jeecg.modules.demo.lab.service.impl;

import org.jeecg.modules.demo.lab.entity.LabApplicationManage;
import org.jeecg.modules.demo.lab.entity.LabUseRecord;
import org.jeecg.modules.demo.lab.mapper.LabApplicationManageMapper;
import org.jeecg.modules.demo.lab.service.ILabApplicationManageService;
import org.jeecg.modules.demo.lab.service.ILabUseRecordService;
import org.jeecg.common.util.oConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.math.BigDecimal;
import org.apache.commons.lang3.StringUtils;
/**
 * @Description: 申请管理
 * @Author: jeecg-boot
 * @Date:   2026-05-08
 * @Version: V1.0
 */
@Service
public class LabApplicationManageServiceImpl extends ServiceImpl<LabApplicationManageMapper, LabApplicationManage> implements ILabApplicationManageService {
  @Autowired
  private ILabUseRecordService labUseRecordService;
  @Override
  public boolean updateById(LabApplicationManage entity) {

      LabApplicationManage old = this.getById(entity.getId());
      if (old == null) {
          return false;
      }
      if (oConvertUtils.isEmpty(entity.getUserId())) {
          entity.setUserId(old.getUserId());
      }
      if (entity.getActualHours() == null) {
          entity.setActualHours(old.getActualHours());
      }

      if (!"approved".equals(old.getStatus())
              && "approved".equals(entity.getStatus())) {
          if (oConvertUtils.isEmpty(entity.getUserId())) {
              throw new IllegalArgumentException("审批通过时用户ID不能为空");
          }
          if (entity.getActualHours() == null || entity.getActualHours().compareTo(BigDecimal.ZERO) <= 0) {
              throw new IllegalArgumentException("审批通过时使用时间必须大于0");
          }

          syncUseRecord(entity);
      }

      return super.updateById(entity);
  }

  private void syncUseRecord(LabApplicationManage source) {
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
