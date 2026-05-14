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
import org.jeecg.modules.demo.lab.entity.LabUser;
import org.jeecg.modules.demo.lab.service.ILabUserService;

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
 * @Description: 人员信息（非系统用户）
 * @Author: jeecg-boot
 * @Date:   2026-05-13
 * @Version: V1.0
 */
@Tag(name="人员信息（非系统用户）")
@RestController
@RequestMapping("/lab/labUser")
@Slf4j
public class LabUserController extends JeecgController<LabUser, ILabUserService> {
	@Autowired
	private ILabUserService labUserService;
	
	/**
	 * 分页列表查询
	 *
	 * @param labUser
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "人员信息（非系统用户）-分页列表查询")
	@Operation(summary="人员信息（非系统用户）-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<LabUser>> queryPageList(LabUser labUser,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {


        QueryWrapper<LabUser> queryWrapper = QueryGenerator.initQueryWrapper(labUser, req.getParameterMap());
		Page<LabUser> page = new Page<LabUser>(pageNo, pageSize);
		IPage<LabUser> pageList = labUserService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param labUser
	 * @return
	 */
	@AutoLog(value = "人员信息（非系统用户）-添加")
	@Operation(summary="人员信息（非系统用户）-添加")
	@RequiresPermissions("lab:lab_user:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody LabUser labUser) {
		labUserService.save(labUser);

		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param labUser
	 * @return
	 */
	@AutoLog(value = "人员信息（非系统用户）-编辑")
	@Operation(summary="人员信息（非系统用户）-编辑")
	@RequiresPermissions("lab:lab_user:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody LabUser labUser) {
		labUserService.updateById(labUser);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "人员信息（非系统用户）-通过id删除")
	@Operation(summary="人员信息（非系统用户）-通过id删除")
	@RequiresPermissions("lab:lab_user:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		labUserService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "人员信息（非系统用户）-批量删除")
	@Operation(summary="人员信息（非系统用户）-批量删除")
	@RequiresPermissions("lab:lab_user:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.labUserService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "人员信息（非系统用户）-通过id查询")
	@Operation(summary="人员信息（非系统用户）-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<LabUser> queryById(@RequestParam(name="id",required=true) String id) {
		LabUser labUser = labUserService.getById(id);
		if(labUser==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(labUser);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param labUser
    */
    @RequiresPermissions("lab:lab_user:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, LabUser labUser) {
        return super.exportXls(request, labUser, LabUser.class, "人员信息（非系统用户）");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("lab:lab_user:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, LabUser.class);
    }

}
