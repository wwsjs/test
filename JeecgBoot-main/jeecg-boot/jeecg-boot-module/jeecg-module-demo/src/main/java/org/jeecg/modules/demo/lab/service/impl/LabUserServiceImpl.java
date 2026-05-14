package org.jeecg.modules.demo.lab.service.impl;

import org.jeecg.modules.demo.lab.entity.LabUser;
import org.jeecg.modules.demo.lab.mapper.LabUserMapper;
import org.jeecg.modules.demo.lab.service.ILabUserService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 人员信息（非系统用户）
 * @Author: jeecg-boot
 * @Date:   2026-05-13
 * @Version: V1.0
 */
@Service
public class LabUserServiceImpl extends ServiceImpl<LabUserMapper, LabUser> implements ILabUserService {

}
