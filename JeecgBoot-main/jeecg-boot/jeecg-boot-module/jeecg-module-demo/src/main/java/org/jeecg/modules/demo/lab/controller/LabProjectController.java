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
import org.jeecg.modules.demo.lab.entity.LabProject;
import org.jeecg.modules.demo.lab.security.LabPermissionContext;
import org.jeecg.modules.demo.lab.service.ILabProjectService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

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
 /**
 * @Description: 项目信息
 * @Author: jeecg-boot
 * @Date:   2026-05-13
 * @Version: V1.0
 */
@Tag(name="项目信息")
@RestController
@RequestMapping("/lab/labProject")
@Slf4j
public class LabProjectController extends JeecgController<LabProject, ILabProjectService> {
	@Autowired
	private ILabProjectService labProjectService;
	@Autowired
	private LabPermissionContext labPermissionContext;
	
	/**
	 * 分页列表查询
	 *
	 * @param labProject
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "项目信息-分页列表查询")
	@Operation(summary="项目信息-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<LabProject>> queryPageList(LabProject labProject,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {


        QueryWrapper<LabProject> queryWrapper = QueryGenerator.initQueryWrapper(labProject, req.getParameterMap());
		LabPermissionContext.CurrentLabUser currentUser = labPermissionContext.getCurrentUser();
		labPermissionContext.appendGroupScope(queryWrapper, "group_id", currentUser);
		Page<LabProject> page = new Page<LabProject>(pageNo, pageSize);
		IPage<LabProject> pageList = labProjectService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param labProject
	 * @return
	 */
	@AutoLog(value = "项目信息-添加")
	@Operation(summary="项目信息-添加")
	@RequiresPermissions("lab:lab_project:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody LabProject labProject) {
		LabPermissionContext.CurrentLabUser currentUser = labPermissionContext.getCurrentUser();
		String writableGroupId = labPermissionContext.normalizeWritableGroupId(currentUser, labProject.getGroupId());
		if (!currentUser.isSuperAdmin() && StringUtils.isBlank(writableGroupId)) {
			return Result.error("仅允许维护本组项目信息");
		}
		labProject.setGroupId(writableGroupId);
		labProjectService.save(labProject);

		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param labProject
	 * @return
	 */
	@AutoLog(value = "项目信息-编辑")
	@Operation(summary="项目信息-编辑")
	@RequiresPermissions("lab:lab_project:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody LabProject labProject) {
		LabPermissionContext.CurrentLabUser currentUser = labPermissionContext.getCurrentUser();
		LabProject dbEntity = labProjectService.getById(labProject.getId());
		if (dbEntity == null) {
			return Result.error("未找到对应数据");
		}
		if (!labPermissionContext.isGroupAccessible(currentUser, dbEntity.getGroupId())) {
			return Result.error("无权编辑其他组项目");
		}
		String writableGroupId = labPermissionContext.normalizeWritableGroupId(currentUser, labProject.getGroupId());
		if (!currentUser.isSuperAdmin() && StringUtils.isBlank(writableGroupId)) {
			return Result.error("仅允许维护本组项目信息");
		}
		labProject.setGroupId(writableGroupId);
		labProjectService.updateById(labProject);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "项目信息-通过id删除")
	@Operation(summary="项目信息-通过id删除")
	@RequiresPermissions("lab:lab_project:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		LabPermissionContext.CurrentLabUser currentUser = labPermissionContext.getCurrentUser();
		LabProject dbEntity = labProjectService.getById(id);
		if (dbEntity == null) {
			return Result.error("未找到对应数据");
		}
		if (!labPermissionContext.isGroupAccessible(currentUser, dbEntity.getGroupId())) {
			return Result.error("无权删除其他组项目");
		}
		labProjectService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "项目信息-批量删除")
	@Operation(summary="项目信息-批量删除")
	@RequiresPermissions("lab:lab_project:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		LabPermissionContext.CurrentLabUser currentUser = labPermissionContext.getCurrentUser();
		List<LabProject> list = labProjectService.listByIds(Arrays.asList(ids.split(",")));
		for (LabProject project : list) {
			if (!labPermissionContext.isGroupAccessible(currentUser, project.getGroupId())) {
				return Result.error("无权批量删除其他组项目");
			}
		}
		this.labProjectService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "项目信息-通过id查询")
	@Operation(summary="项目信息-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<LabProject> queryById(@RequestParam(name="id",required=true) String id) {
		LabPermissionContext.CurrentLabUser currentUser = labPermissionContext.getCurrentUser();
		LabProject labProject = labProjectService.getById(id);
		if(labProject==null) {
			return Result.error("未找到对应数据");
		}
		if (!labPermissionContext.isGroupAccessible(currentUser, labProject.getGroupId())) {
			return Result.error("无权查看其他组项目");
		}
		return Result.OK(labProject);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param labProject
    */
    @RequiresPermissions("lab:lab_project:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, LabProject labProject) {
        LabPermissionContext.CurrentLabUser currentUser = labPermissionContext.getCurrentUser();
		if (!currentUser.isSuperAdmin()) {
			labProject.setGroupId(currentUser.getGroupId());
		}
        return super.exportXls(request, labProject, LabProject.class, "项目信息");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("lab:lab_project:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, LabProject.class);
    }

}
