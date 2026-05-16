package org.jeecg.modules.demo.lab.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.lab.entity.LabApplication;
import org.jeecg.modules.demo.lab.security.LabPermissionContext;
import org.jeecg.modules.demo.lab.service.ILabApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Set;

/**
 * @Description: 申请信息
 * @Author: jeecg-boot
 * @Date:   2026-05-08
 * @Version: V1.0
 */
@Tag(name = "申请信息")
@RestController("labLabApplicationController")
@RequestMapping("/lab/labApplication")
@Slf4j
public class LabApplicationController extends JeecgController<LabApplication, ILabApplicationService> {
    @Autowired
    private ILabApplicationService labApplicationService;
    @Autowired
    private LabPermissionContext labPermissionContext;

    /**
     * 分页列表查询
     */
    @Operation(summary = "申请信息-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<LabApplication>> queryPageList(LabApplication labApplication,
                                                        @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                        @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                        HttpServletRequest req) {

        QueryWrapper<LabApplication> queryWrapper = QueryGenerator.initQueryWrapper(labApplication, req.getParameterMap());
        LabPermissionContext.CurrentLabUser currentUser = labPermissionContext.getCurrentUser();
        applyApplicationScope(queryWrapper, currentUser);
        Page<LabApplication> page = new Page<>(pageNo, pageSize);
        IPage<LabApplication> pageList = labApplicationService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     * 添加
     */
    @AutoLog(value = "申请信息-添加")
    @Operation(summary = "申请信息-添加")
    @RequiresPermissions("lab:lab_application:add")
    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody LabApplication labApplication) {
        LabPermissionContext.CurrentLabUser currentUser = labPermissionContext.getCurrentUser();
        Result<String> normalized = normalizeAndValidateApplication(labApplication, currentUser);
        if (!normalized.isSuccess()) {
            return normalized;
        }
        if (StringUtils.isBlank(labApplication.getStatus())) {
            labApplication.setStatus("pending");
        }
        labApplicationService.save(labApplication);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     */
    @AutoLog(value = "申请信息-编辑")
    @Operation(summary = "申请信息-编辑")
    @RequiresPermissions("lab:lab_application:edit")
    @RequestMapping(value = "/edit", method = {RequestMethod.PUT, RequestMethod.POST})
    public Result<String> edit(@RequestBody LabApplication labApplication) {
        LabPermissionContext.CurrentLabUser currentUser = labPermissionContext.getCurrentUser();
        LabApplication dbEntity = labApplicationService.getById(labApplication.getId());
        if (dbEntity == null) {
            return Result.error("未找到对应数据");
        }
        if (!canAccessApplication(currentUser, dbEntity.getUserId())) {
            return Result.error("无权编辑该申请");
        }

        Result<String> normalized = normalizeAndValidateApplication(labApplication, currentUser);
        if (!normalized.isSuccess()) {
            return normalized;
        }
        labApplicationService.updateById(labApplication);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     */
    @AutoLog(value = "申请信息-通过id删除")
    @Operation(summary = "申请信息-通过id删除")
    @RequiresPermissions("lab:lab_application:delete")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam(name = "id", required = true) String id) {
        LabPermissionContext.CurrentLabUser currentUser = labPermissionContext.getCurrentUser();
        LabApplication dbEntity = labApplicationService.getById(id);
        if (dbEntity == null) {
            return Result.error("未找到对应数据");
        }
        if (!canAccessApplication(currentUser, dbEntity.getUserId())) {
            return Result.error("无权删除该申请");
        }
        labApplicationService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     */
    @AutoLog(value = "申请信息-批量删除")
    @Operation(summary = "申请信息-批量删除")
    @RequiresPermissions("lab:lab_application:deleteBatch")
    @DeleteMapping(value = "/deleteBatch")
    public Result<String> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        LabPermissionContext.CurrentLabUser currentUser = labPermissionContext.getCurrentUser();
        for (String id : ids.split(",")) {
            LabApplication dbEntity = labApplicationService.getById(id);
            if (dbEntity != null && !canAccessApplication(currentUser, dbEntity.getUserId())) {
                return Result.error("无权批量删除其他用户申请");
            }
        }
        this.labApplicationService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     */
    @Operation(summary = "申请信息-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<LabApplication> queryById(@RequestParam(name = "id", required = true) String id) {
        LabPermissionContext.CurrentLabUser currentUser = labPermissionContext.getCurrentUser();
        LabApplication labApplication = labApplicationService.getById(id);
        if (labApplication == null) {
            return Result.error("未找到对应数据");
        }
        if (!canAccessApplication(currentUser, labApplication.getUserId())) {
            return Result.error("无权查看该申请");
        }
        return Result.OK(labApplication);
    }

    /**
     * 导出excel
     */
    @RequiresPermissions("lab:lab_application:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, LabApplication labApplication) {
        LabPermissionContext.CurrentLabUser currentUser = labPermissionContext.getCurrentUser();
        if (!currentUser.isSuperAdmin() && !currentUser.isGroupLeader()) {
            labApplication.setUserId(labPermissionContext.currentSysUserId(currentUser));
        }
        return super.exportXls(request, labApplication, LabApplication.class, "申请信息");
    }

    /**
     * 通过excel导入数据
     */
    @RequiresPermissions("lab:lab_application:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, LabApplication.class);
    }

    private Result<String> normalizeAndValidateApplication(LabApplication labApplication,
                                                           LabPermissionContext.CurrentLabUser currentUser) {
        String normalizedUserId = labPermissionContext.normalizeApplicationUserId(currentUser, labApplication.getUserId());
        if (oConvertUtils.isEmpty(normalizedUserId)) {
            return Result.error("请填写有效的使用人");
        }
        labApplication.setUserId(normalizedUserId);

        if (!canAccessApplication(currentUser, normalizedUserId)) {
            return Result.error("无权提交非本组成员申请");
        }

        BigDecimal actualHours = labApplication.getActualHours();
        if (actualHours == null) {
            return Result.error("请填写使用时间");
        }
        if (actualHours.compareTo(BigDecimal.ZERO) <= 0) {
            return Result.error("使用时间必须大于0");
        }
        return Result.OK();
    }

    private boolean canAccessApplication(LabPermissionContext.CurrentLabUser currentUser, String targetSysUserId) {
        if (currentUser.isSuperAdmin()) {
            return true;
        }

        String target = StringUtils.trimToNull(targetSysUserId);
        if (target == null) {
            return false;
        }

        if (!currentUser.isGroupLeader()) {
            String currentSysUserId = labPermissionContext.currentSysUserId(currentUser);
            String currentLabUserId = labPermissionContext.currentLabUserId(currentUser);
            return StringUtils.equals(target, currentSysUserId) || StringUtils.equals(target, currentLabUserId);
        }

        Set<String> groupUserIds = labPermissionContext.resolveGroupSysUserIds(currentUser);
        return !groupUserIds.isEmpty() && groupUserIds.contains(target);
    }

    private void applyApplicationScope(QueryWrapper<LabApplication> queryWrapper,
                                       LabPermissionContext.CurrentLabUser currentUser) {
        if (currentUser.isSuperAdmin()) {
            return;
        }

        if (!currentUser.isGroupLeader()) {
            String sysUserId = labPermissionContext.currentSysUserId(currentUser);
            String labUserId = labPermissionContext.currentLabUserId(currentUser);
            if (StringUtils.isBlank(sysUserId) && StringUtils.isBlank(labUserId)) {
                queryWrapper.apply("1=0");
            } else if (StringUtils.isNotBlank(sysUserId) && StringUtils.isNotBlank(labUserId) && !StringUtils.equals(sysUserId, labUserId)) {
                queryWrapper.and(wrapper -> wrapper.eq("user_id", sysUserId).or().eq("user_id", labUserId));
            } else if (StringUtils.isNotBlank(sysUserId)) {
                queryWrapper.eq("user_id", sysUserId);
            } else {
                queryWrapper.eq("user_id", labUserId);
            }
            return;
        }

        Set<String> groupUserIds = labPermissionContext.resolveGroupSysUserIds(currentUser);
        if (groupUserIds.isEmpty()) {
            queryWrapper.apply("1=0");
            return;
        }
        queryWrapper.in("user_id", groupUserIds);
    }
}
