package org.jeecg.modules.legacy.application.service.impl;

import org.jeecg.modules.legacy.application.entity.LabApplication;
import org.jeecg.modules.legacy.application.mapper.LabApplicationMapper;
import org.jeecg.modules.legacy.application.service.ILabApplicationService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 该表存储申请信息
 * @Author: jeecg-boot
 * @Date:   2026-05-14
 * @Version: V1.0
 */
@Service("legacyApplicationLabApplicationServiceImpl")
public class LabApplicationServiceImpl extends ServiceImpl<LabApplicationMapper, LabApplication> implements ILabApplicationService {

}
