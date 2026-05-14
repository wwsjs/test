package org.jeecg.modules.demo.lab.service.impl;

import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.demo.lab.entity.LabApplication;
import org.jeecg.modules.demo.lab.mapper.LabApplicationMapper;
import org.jeecg.modules.demo.lab.service.ILabApplicationService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 申请信息
 * @Author: jeecg-boot
 * @Date:   2026-05-08
 * @Version: V1.0
 */
@Service
public class LabApplicationServiceImpl extends ServiceImpl<LabApplicationMapper, LabApplication> implements ILabApplicationService {
    @Override
    public boolean save(LabApplication entity) {
        // 默认状态
        entity.setStatus("pending");
        return super.save(entity);
    }
}
