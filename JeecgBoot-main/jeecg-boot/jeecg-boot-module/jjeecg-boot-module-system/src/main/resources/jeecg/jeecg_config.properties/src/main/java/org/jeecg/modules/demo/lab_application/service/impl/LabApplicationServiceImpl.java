package org.jeecg.modules.demo.lab_application.service.impl;

import org.jeecg.modules.demo.lab_application.entity.LabApplication;
import org.jeecg.modules.demo.lab_application.mapper.LabApplicationMapper;
import org.jeecg.modules.demo.lab_application.service.ILabApplicationService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 该表存储申请信息
 * @Author: jeecg-boot
 * @Date:   2026-05-14
 * @Version: V1.0
 */
@Service
public class LabApplicationServiceImpl extends ServiceImpl<LabApplicationMapper, LabApplication> implements ILabApplicationService {

}
