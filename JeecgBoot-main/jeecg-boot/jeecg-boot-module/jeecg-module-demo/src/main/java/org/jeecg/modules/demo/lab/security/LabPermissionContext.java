package org.jeecg.modules.demo.lab.security;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.demo.lab.entity.LabUser;
import org.jeecg.modules.demo.lab.mapper.LabUserMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * lab 业务数据权限上下文。
 */
@Component
public class LabPermissionContext {

    private final LabUserMapper labUserMapper;
    private final JdbcTemplate jdbcTemplate;

    public LabPermissionContext(LabUserMapper labUserMapper, JdbcTemplate jdbcTemplate) {
        this.labUserMapper = labUserMapper;
        this.jdbcTemplate = jdbcTemplate;
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

        String roleToken = StringUtils.lowerCase(StringUtils.trimToEmpty(
                StringUtils.defaultIfBlank(labUser.getRoleCode(), labUser.getPersonType())
        ));
        boolean isAdminAccount = "admin".equalsIgnoreCase(loginUser.getUsername());
        boolean superAdmin = isAdminAccount || matchesAny(roleToken,
                "super_admin", "superadmin", "platform_admin", "root", "sa");
        boolean groupLeader = matchesAny(roleToken,
                "group_leader", "groupleader", "leader", "master", "admin", "mentor");
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

    public String currentSysUserId(CurrentLabUser currentUser) {
        return StringUtils.trimToNull(currentUser.getLoginUser().getId());
    }

    public String currentLabUserId(CurrentLabUser currentUser) {
        if (currentUser == null || currentUser.getLabUser() == null) {
            return null;
        }
        return StringUtils.trimToNull(currentUser.getLabUser().getId());
    }

    /**
     * 将前端传入的申请用户标识统一规范为 sys_user.id。
     * 普通组员只能提交本人，组长/超管可代填但也必须映射为 sys_user.id。
     */
    public String normalizeApplicationUserId(CurrentLabUser currentUser, String rawUserId) {
        if (currentUser == null) {
            return null;
        }

        if (!currentUser.isSuperAdmin() && !currentUser.isGroupLeader()) {
            return currentSysUserId(currentUser);
        }

        String candidate = StringUtils.trimToNull(rawUserId);
        if (candidate == null) {
            return null;
        }

        if (isValidSysUserId(candidate)) {
            return candidate;
        }

        LabUser byLabUserId = labUserMapper.selectById(candidate);
        if (byLabUserId != null) {
            return resolveSysUserIdFromLabUser(byLabUserId);
        }

        LabUser byUsername = findByUsername(candidate);
        if (byUsername != null) {
            return resolveSysUserIdFromLabUser(byUsername);
        }

        LabUser byWorkNo = findByWorkNo(candidate);
        if (byWorkNo != null) {
            return resolveSysUserIdFromLabUser(byWorkNo);
        }

        LabUser byPhone = findByPhone(candidate);
        if (byPhone != null) {
            return resolveSysUserIdFromLabUser(byPhone);
        }

        LabUser byEmail = findByEmail(candidate);
        if (byEmail != null) {
            return resolveSysUserIdFromLabUser(byEmail);
        }

        return null;
    }

    /**
     * 根据当前组信息解析当前组全部可访问 sys_user.id。
     */
    public Set<String> resolveGroupSysUserIds(CurrentLabUser currentUser) {
        LinkedHashSet<String> result = new LinkedHashSet<>();
        if (currentUser == null || StringUtils.isBlank(currentUser.getGroupId())) {
            return result;
        }

        List<LabUser> labUsers = labUserMapper.selectList(
                new LambdaQueryWrapper<LabUser>()
                        .eq(LabUser::getGroupId, currentUser.getGroupId())
                        .select(LabUser::getId, LabUser::getUsername, LabUser::getWorkNo, LabUser::getPhone, LabUser::getEmail)
        );

        for (LabUser labUser : labUsers) {
            String labUserId = StringUtils.trimToNull(labUser.getId());
            if (StringUtils.isNotBlank(labUserId)) {
                result.add(labUserId);
            }
            String sysUserId = resolveSysUserIdFromLabUser(labUser);
            if (StringUtils.isNotBlank(sysUserId)) {
                result.add(sysUserId);
            }
        }
        return result;
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

        LabUser byWorkNo = findByWorkNo(loginUser.getWorkNo());
        if (byWorkNo != null) {
            return byWorkNo;
        }

        LabUser byWorkNoFromUsername = findByWorkNo(loginUser.getUsername());
        if (byWorkNoFromUsername != null) {
            return byWorkNoFromUsername;
        }

        LabUser byPhone = findByPhone(loginUser.getPhone());
        if (byPhone != null) {
            return byPhone;
        }

        LabUser byEmail = findByEmail(loginUser.getEmail());
        if (byEmail != null) {
            return byEmail;
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

    private LabUser findByPhone(String phone) {
        if (StringUtils.isBlank(phone)) {
            return null;
        }
        LambdaQueryWrapper<LabUser> query = new LambdaQueryWrapper<LabUser>()
                .eq(LabUser::getPhone, phone)
                .last("limit 1");
        return labUserMapper.selectOne(query);
    }

    private LabUser findByEmail(String email) {
        if (StringUtils.isBlank(email)) {
            return null;
        }
        LambdaQueryWrapper<LabUser> query = new LambdaQueryWrapper<LabUser>()
                .eq(LabUser::getEmail, email)
                .last("limit 1");
        return labUserMapper.selectOne(query);
    }

    private String resolveSysUserIdFromLabUser(LabUser labUser) {
        if (labUser == null) {
            return null;
        }

        String labUserId = StringUtils.trimToNull(labUser.getId());
        if (labUserId != null && isValidSysUserId(labUserId)) {
            return labUserId;
        }

        String byUsername = findSysUserIdByColumn("username", labUser.getUsername());
        if (byUsername != null) {
            return byUsername;
        }

        String byRealname = findSysUserIdByColumn("realname", labUser.getUsername());
        if (byRealname != null) {
            return byRealname;
        }

        String byWorkNo = findSysUserIdByColumn("work_no", labUser.getWorkNo());
        if (byWorkNo != null) {
            return byWorkNo;
        }

        String byPhone = findSysUserIdByColumn("phone", labUser.getPhone());
        if (byPhone != null) {
            return byPhone;
        }

        return findSysUserIdByColumn("email", labUser.getEmail());
    }

    private String findSysUserIdByColumn(String columnName, String value) {
        String normalized = StringUtils.trimToNull(value);
        if (normalized == null || !hasSysUserColumn(columnName)) {
            return null;
        }
        String sql = "SELECT id FROM sys_user WHERE " + columnName + " = ? LIMIT 1";
        return queryOneString(sql, normalized);
    }

    private boolean isValidSysUserId(String userId) {
        String normalized = StringUtils.trimToNull(userId);
        if (normalized == null) {
            return false;
        }
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(1) FROM sys_user WHERE id = ?",
                Integer.class,
                normalized
        );
        return count != null && count > 0;
    }

    private boolean hasSysUserColumn(String columnName) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(1) FROM information_schema.columns WHERE table_schema = database() AND table_name = 'sys_user' AND column_name = ?",
                Integer.class,
                columnName
        );
        return count != null && count > 0;
    }

    private String queryOneString(String sql, Object... args) {
        List<String> rows = jdbcTemplate.query(sql, (rs, rowNum) -> rs.getString(1), args);
        if (rows == null || rows.isEmpty()) {
            return null;
        }
        return StringUtils.trimToNull(rows.get(0));
    }

    private boolean matchesAny(String value, String... candidates) {
        if (StringUtils.isBlank(value)) {
            return false;
        }
        for (String candidate : candidates) {
            if (Objects.equals(value, candidate)) {
                return true;
            }
        }
        return false;
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
