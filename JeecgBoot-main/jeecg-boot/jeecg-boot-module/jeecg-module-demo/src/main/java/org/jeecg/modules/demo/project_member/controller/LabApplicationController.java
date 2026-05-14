package org.jeecg.modules.demo.project_member.controller;

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
import org.jeecg.modules.demo.project_member.entity.LabApplication;
import org.jeecg.modules.demo.project_member.service.ILabApplicationService;

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
 /**
 * @Description: 该表存储申请信息
 * @Author: jeecg-boot
 * @Date:   2026-05-14
 * @Version: V1.0
 */
@Tag(name="该表存储申请信息")
@RestController
@RequestMapping("/project_member/labApplication")
@Slf4j
public class LabApplicationController extends JeecgController<LabApplication, ILabApplicationService> {
	@Autowired
	private ILabApplicationService labApplicationService;
	
	/**
	 * 分页列表查询
	 *
	 * @param labApplication
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "该表存储申请信息-分页列表查询")
	@Operation(summary="该表存储申请信息-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<LabApplication>> queryPageList(LabApplication labApplication,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {


        QueryWrapper<LabApplication> queryWrapper = QueryGenerator.initQueryWrapper(labApplication, req.getParameterMap());
		Page<LabApplication> page = new Page<LabApplication>(pageNo, pageSize);
		IPage<LabApplication> pageList = labApplicationService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param labApplication
	 * @return
	 */
	@AutoLog(value = "该表存储申请信息-添加")
	@Operation(summary="该表存储申请信息-添加")
	@RequiresPermissions("project_member:lab_application:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody LabApplication labApplication) {
		labApplicationService.save(labApplication);

		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param labApplication
	 * @return
	 */
	@AutoLog(value = "该表存储申请信息-编辑")
	@Operation(summary="该表存储申请信息-编辑")
	@RequiresPermissions("project_member:lab_application:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody LabApplication labApplication) {
		labApplicationService.updateById(labApplication);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "该表存储申请信息-通过id删除")
	@Operation(summary="该表存储申请信息-通过id删除")
	@RequiresPermissions("project_member:lab_application:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		labApplicationService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "该表存储申请信息-批量删除")
	@Operation(summary="该表存储申请信息-批量删除")
	@RequiresPermissions("project_member:lab_application:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.labApplicationService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "该表存储申请信息-通过id查询")
	@Operation(summary="该表存储申请信息-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<LabApplication> queryById(@RequestParam(name="id",required=true) String id) {
		LabApplication labApplication = labApplicationService.getById(id);
		if(labApplication==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(labApplication);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param labApplication
    */
    @RequiresPermissions("project_member:lab_application:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, LabApplication labApplication) {
        return super.exportXls(request, labApplication, LabApplication.class, "该表存储申请信息");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("project_member:lab_application:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, LabApplication.class);
    }

}
