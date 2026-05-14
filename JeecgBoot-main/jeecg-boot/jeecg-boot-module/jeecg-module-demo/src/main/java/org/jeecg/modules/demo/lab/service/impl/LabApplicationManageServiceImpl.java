package org.jeecg.modules.demo.lab.service.impl;

import org.jeecg.modules.demo.lab.entity.LabApplicationManage;
import org.jeecg.modules.demo.lab.entity.LabUseRecord;
import org.jeecg.modules.demo.lab.mapper.LabApplicationManageMapper;
import org.jeecg.modules.demo.lab.service.ILabApplicationManageService;
import org.jeecg.modules.demo.lab.service.ILabUseRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.math.BigDecimal;
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

      if (!"approved".equals(old.getStatus())
              && "approved".equals(entity.getStatus())) {

          LabUseRecord record = new LabUseRecord();
          record.setUserId(entity.getUserId());
          record.setEquipmentId(entity.getEquipmentId());
          record.setProjectId(entity.getProjectId());
          record.setStartDate(entity.getStartDate());
          record.setEndDate(entity.getEndDate());
          record.setPurpose(entity.getPurpose());
          record.setActualHours(entity.getActualHours() == null ? BigDecimal.ZERO : entity.getActualHours());

          labUseRecordService.save(record);
      }

      return super.updateById(entity);
  }
}
