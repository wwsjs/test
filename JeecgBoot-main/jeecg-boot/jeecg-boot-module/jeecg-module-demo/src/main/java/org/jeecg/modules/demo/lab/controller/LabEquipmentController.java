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
import org.jeecg.modules.demo.lab.entity.LabEquipment;
import org.jeecg.modules.demo.lab.service.ILabEquipmentService;

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
 * @Description: 实验室设备信息
 * @Author: jeecg-boot
 * @Date:   2026-05-13
 * @Version: V1.0
 */
@Tag(name="实验室设备信息")
@RestController
@RequestMapping("/lab/labEquipment")
@Slf4j
public class LabEquipmentController extends JeecgController<LabEquipment, ILabEquipmentService> {
	@Autowired
	private ILabEquipmentService labEquipmentService;
	
	/**
	 * 分页列表查询
	 *
	 * @param labEquipment
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "实验室设备信息-分页列表查询")
	@Operation(summary="实验室设备信息-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<LabEquipment>> queryPageList(LabEquipment labEquipment,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {


        QueryWrapper<LabEquipment> queryWrapper = QueryGenerator.initQueryWrapper(labEquipment, req.getParameterMap());
		Page<LabEquipment> page = new Page<LabEquipment>(pageNo, pageSize);
		IPage<LabEquipment> pageList = labEquipmentService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param labEquipment
	 * @return
	 */
	@AutoLog(value = "实验室设备信息-添加")
	@Operation(summary="实验室设备信息-添加")
	@RequiresPermissions("lab:lab_equipment:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody LabEquipment labEquipment) {
		labEquipmentService.save(labEquipment);

		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param labEquipment
	 * @return
	 */
	@AutoLog(value = "实验室设备信息-编辑")
	@Operation(summary="实验室设备信息-编辑")
	@RequiresPermissions("lab:lab_equipment:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody LabEquipment labEquipment) {
		labEquipmentService.updateById(labEquipment);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "实验室设备信息-通过id删除")
	@Operation(summary="实验室设备信息-通过id删除")
	@RequiresPermissions("lab:lab_equipment:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		labEquipmentService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "实验室设备信息-批量删除")
	@Operation(summary="实验室设备信息-批量删除")
	@RequiresPermissions("lab:lab_equipment:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.labEquipmentService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "实验室设备信息-通过id查询")
	@Operation(summary="实验室设备信息-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<LabEquipment> queryById(@RequestParam(name="id",required=true) String id) {
		LabEquipment labEquipment = labEquipmentService.getById(id);
		if(labEquipment==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(labEquipment);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param labEquipment
    */
    @RequiresPermissions("lab:lab_equipment:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, LabEquipment labEquipment) {
        return super.exportXls(request, labEquipment, LabEquipment.class, "实验室设备信息");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("lab:lab_equipment:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, LabEquipment.class);
    }

}
