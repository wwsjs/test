package org.jeecg.modules.demo.lab.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.query.QueryRuleEnum;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.lab.entity.LabApplicationManage;
import org.jeecg.modules.demo.lab.security.LabPermissionContext;
import org.jeecg.modules.demo.lab.service.ILabApplicationManageService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import java.math.BigDecimal;
 /**
 * @Description: 申请管理
 * @Author: jeecg-boot
 * @Date:   2026-05-08
 * @Version: V1.0
 */
@Tag(name="申请管理")
@RestController
@RequestMapping("/lab/labApplicationManage")
@Slf4j
public class LabApplicationManageController extends JeecgController<LabApplicationManage, ILabApplicationManageService> {
	@Autowired
	private ILabApplicationManageService labApplicationManageService;
	@Autowired
	private LabPermissionContext labPermissionContext;
	
	/**
	 * 分页列表查询
	 *
	 * @param labApplicationManage
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "申请管理-分页列表查询")
	@Operation(summary="申请管理-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<LabApplicationManage>> queryPageList(LabApplicationManage labApplicationManage,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {


        QueryWrapper<LabApplicationManage> queryWrapper = QueryGenerator.initQueryWrapper(labApplicationManage, req.getParameterMap());
		Page<LabApplicationManage> page = new Page<LabApplicationManage>(pageNo, pageSize);
		IPage<LabApplicationManage> pageList = labApplicationManageService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param labApplicationManage
	 * @return
	 */
	@AutoLog(value = "申请管理-添加")
	@Operation(summary="申请管理-添加")
	@RequiresPermissions("lab:lab_application:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody LabApplicationManage labApplicationManage) {
		LabPermissionContext.CurrentLabUser currentUser = labPermissionContext.getCurrentUser();
		if (!canManageApplications(currentUser)) {
			return Result.error("仅超级管理员或组长可维护申请管理");
		}
		if (!isValidActualHours(labApplicationManage.getActualHours())) {
			return Result.error("使用时间必须大于0");
		}
		labApplicationManageService.save(labApplicationManage);

		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param labApplicationManage
	 * @return
	 */
	@AutoLog(value = "申请管理-编辑")
	@Operation(summary="申请管理-编辑")
	@RequiresPermissions("lab:lab_application:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody LabApplicationManage labApplicationManage) {
		LabPermissionContext.CurrentLabUser currentUser = labPermissionContext.getCurrentUser();
		if (!canManageApplications(currentUser)) {
			return Result.error("仅超级管理员或组长可维护申请管理");
		}
		if (!isValidActualHours(labApplicationManage.getActualHours())) {
			return Result.error("使用时间必须大于0");
		}
		if ("approved".equals(labApplicationManage.getStatus())) {
			if (oConvertUtils.isEmpty(labApplicationManage.getUserId())) {
				return Result.error("审批通过时用户ID不能为空");
			}
			if (labApplicationManage.getActualHours() == null
					|| labApplicationManage.getActualHours().compareTo(BigDecimal.ZERO) <= 0) {
				return Result.error("审批通过时使用时间必须大于0");
			}
		}
		labApplicationManageService.updateById(labApplicationManage);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "申请管理-通过id删除")
	@Operation(summary="申请管理-通过id删除")
	@RequiresPermissions("lab:lab_application:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		LabPermissionContext.CurrentLabUser currentUser = labPermissionContext.getCurrentUser();
		if (!canManageApplications(currentUser)) {
			return Result.error("仅超级管理员或组长可维护申请管理");
		}
		labApplicationManageService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "申请管理-批量删除")
	@Operation(summary="申请管理-批量删除")
	@RequiresPermissions("lab:lab_application:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		LabPermissionContext.CurrentLabUser currentUser = labPermissionContext.getCurrentUser();
		if (!canManageApplications(currentUser)) {
			return Result.error("仅超级管理员或组长可维护申请管理");
		}
		this.labApplicationManageService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "申请管理-通过id查询")
	@Operation(summary="申请管理-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<LabApplicationManage> queryById(@RequestParam(name="id",required=true) String id) {
		LabApplicationManage labApplicationManage = labApplicationManageService.getById(id);
		if(labApplicationManage==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(labApplicationManage);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param labApplicationManage
    */
    @RequiresPermissions("lab:lab_application:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, LabApplicationManage labApplicationManage) {
        return super.exportXls(request, labApplicationManage, LabApplicationManage.class, "申请管理");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("lab:lab_application:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
		LabPermissionContext.CurrentLabUser currentUser = labPermissionContext.getCurrentUser();
		if (!canManageApplications(currentUser)) {
			return Result.error("仅超级管理员或组长可维护申请管理");
		}
        return super.importExcel(request, response, LabApplicationManage.class);
    }

	private boolean canManageApplications(LabPermissionContext.CurrentLabUser currentUser) {
		return currentUser.isSuperAdmin() || currentUser.isGroupLeader();
	}

	private boolean isValidActualHours(BigDecimal actualHours) {
		return actualHours != null && actualHours.compareTo(BigDecimal.ZERO) > 0;
	}

}
