package org.jeecg.modules.demo.Application.service.impl;

import org.jeecg.modules.demo.Application.entity.LabApplication;
import org.jeecg.modules.demo.Application.mapper.LabApplicationMapper;
import org.jeecg.modules.demo.Application.service.ILabApplicationService;
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
