package org.jeecg.modules.demo.application.service.impl;

import org.jeecg.modules.demo.application.entity.LabApplication;
import org.jeecg.modules.demo.application.mapper.LabApplicationMapper;
import org.jeecg.modules.demo.application.service.ILabApplicationService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 该表存储申请信息
 * @Author: jeecg-boot
 * @Date:   2026-05-14
 * @Version: V1.0
 */
@Service("applicationLabApplicationServiceImpl")
public class LabApplicationServiceImpl extends ServiceImpl<LabApplicationMapper, LabApplication> implements ILabApplicationService {

}
