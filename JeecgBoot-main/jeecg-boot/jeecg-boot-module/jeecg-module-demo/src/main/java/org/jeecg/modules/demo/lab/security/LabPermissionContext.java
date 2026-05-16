package org.jeecg.modules.demo.lab.security;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.demo.lab.entity.LabUser;
import org.jeecg.modules.demo.lab.mapper.LabUserMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * lab 业务数据权限上下文。
 */
@Component
public class LabPermissionContext {

    private final LabUserMapper labUserMapper;

    public LabPermissionContext(LabUserMapper labUserMapper) {
        this.labUserMapper = labUserMapper;
    }

    public CurrentLabUser getCurrentUser() {
        Object principal = SecurityUtils.getSubject().getPrincipal();
        if (!(principal instanceof LoginUser loginUser)) {
            throw new IllegalStateException("未获取到登录用户信息");
        }

        LabUser labUser = resolveLabUser(loginUser);
        if (labUser == null) {
            // 系统管理员账号没有映射到 lab_user 时，默认按超级管理员处理，避免阻断系统管理。
            boolean isAdminAccount = "admin".equalsIgnoreCase(loginUser.getUsername());
            return new CurrentLabUser(loginUser, null, isAdminAccount, false, null);
        }

        String roleCode = StringUtils.lowerCase(StringUtils.trimToEmpty(labUser.getRoleCode()));
        boolean superAdmin = "super_admin".equals(roleCode);
        boolean groupLeader = "admin".equals(roleCode);
        return new CurrentLabUser(loginUser, labUser, superAdmin, groupLeader, StringUtils.trimToNull(labUser.getGroupId()));
    }

    public void appendGroupScope(QueryWrapper<?> queryWrapper, String groupColumn, CurrentLabUser currentUser) {
        if (currentUser.isSuperAdmin()) {
            return;
        }
        if (StringUtils.isBlank(currentUser.getGroupId())) {
            queryWrapper.apply("1=0");
            return;
        }
        queryWrapper.eq(groupColumn, currentUser.getGroupId());
    }

    public boolean isGroupAccessible(CurrentLabUser currentUser, String targetGroupId) {
        if (currentUser.isSuperAdmin()) {
            return true;
        }
        return StringUtils.isNotBlank(currentUser.getGroupId())
            && Objects.equals(StringUtils.trim(targetGroupId), currentUser.getGroupId());
    }

    public String normalizeWritableGroupId(CurrentLabUser currentUser, String requestGroupId) {
        String trimmed = StringUtils.trimToNull(requestGroupId);
        if (currentUser.isSuperAdmin()) {
            return trimmed;
        }
        String currentGroupId = currentUser.getGroupId();
        if (StringUtils.isBlank(currentGroupId)) {
            return null;
        }
        if (trimmed == null) {
            return currentGroupId;
        }
        return Objects.equals(trimmed, currentGroupId) ? trimmed : null;
    }

    public boolean isLabUserAccessible(CurrentLabUser currentUser, LabUser target) {
        if (target == null) {
            return false;
        }
        if (currentUser.isSuperAdmin()) {
            return true;
        }
        return isGroupAccessible(currentUser, target.getGroupId());
    }

    private LabUser resolveLabUser(LoginUser loginUser) {
        LabUser byId = labUserMapper.selectById(loginUser.getId());
        if (byId != null) {
            return byId;
        }

        LabUser byUsername = findByUsername(loginUser.getUsername());
        if (byUsername != null) {
            return byUsername;
        }

        LabUser byWorkNo = findByWorkNo(loginUser.getUsername());
        if (byWorkNo != null) {
            return byWorkNo;
        }

        return findByUsername(loginUser.getRealname());
    }

    private LabUser findByUsername(String username) {
        if (StringUtils.isBlank(username)) {
            return null;
        }
        LambdaQueryWrapper<LabUser> query = new LambdaQueryWrapper<LabUser>()
            .eq(LabUser::getUsername, username)
            .last("limit 1");
        return labUserMapper.selectOne(query);
    }

    private LabUser findByWorkNo(String workNo) {
        if (StringUtils.isBlank(workNo)) {
            return null;
        }
        LambdaQueryWrapper<LabUser> query = new LambdaQueryWrapper<LabUser>()
                .eq(LabUser::getWorkNo, workNo)
                .last("limit 1");
        return labUserMapper.selectOne(query);
    }

    @Getter
    public static class CurrentLabUser {
        private final LoginUser loginUser;
        private final LabUser labUser;
        private final boolean superAdmin;
        private final boolean groupLeader;
        private final String groupId;

        public CurrentLabUser(LoginUser loginUser, LabUser labUser, boolean superAdmin, boolean groupLeader, String groupId) {
            this.loginUser = loginUser;
            this.labUser = labUser;
            this.superAdmin = superAdmin;
            this.groupLeader = groupLeader;
            this.groupId = groupId;
        }
    }
}
