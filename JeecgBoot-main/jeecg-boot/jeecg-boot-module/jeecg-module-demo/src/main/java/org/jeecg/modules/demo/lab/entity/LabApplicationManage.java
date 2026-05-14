package org.jeecg.modules.demo.lab.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableLogic;
import org.jeecg.common.constant.ProvinceCityArea;
import org.jeecg.common.util.SpringContextUtils;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 申请管理
 * @Author: jeecg-boot
 * @Date:   2026-05-08
 * @Version: V1.0
 */
@Data
@TableName("lab_application")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="申请管理")
public class LabApplicationManage implements Serializable {
    private static final long serialVersionUID = 1L;

	/**使用记录ID*/
	@TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "使用记录ID")
    private java.lang.String id;
	/**用户ID*/
	@Excel(name = "用户ID", width = 15, dictTable = "sys_user", dicText = "realname", dicCode = "id")
	@Dict(dictTable = "sys_user", dicText = "realname", dicCode = "id")
    @Schema(description = "用户ID")
    private java.lang.String userId;
	/**设备ID*/
	@Excel(name = "设备ID", width = 15, dictTable = "lab_equipment", dicText = "equipment_name", dicCode = "id")
	@Dict(dictTable = "lab_equipment", dicText = "equipment_name", dicCode = "id")
    @Schema(description = "设备ID")
    private java.lang.String equipmentId;
	/**项目ID*/
	@Excel(name = "项目ID", width = 15, dictTable = "lab_project", dicText = "project_name", dicCode = "id")
	@Dict(dictTable = "lab_project", dicText = "project_name", dicCode = "id")
    @Schema(description = "项目ID")
    private java.lang.String projectId;
	/**开始日期*/
	@Excel(name = "开始日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Schema(description = "开始日期")
    private java.util.Date startDate;
	/**结束日期*/
	@Excel(name = "结束日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Schema(description = "结束日期")
    private java.util.Date endDate;
	/**用途或项目内容*/
	@Excel(name = "用途或项目内容", width = 15)
    @Schema(description = "用途或项目内容")
    private java.lang.String purpose;
	/**申请状态*/
	@Excel(name = "申请状态", width = 15, dicCode = "application_status")
	@Dict(dicCode = "application_status")
    @Schema(description = "申请状态")
    private java.lang.String status;
	/**createTime*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Schema(description = "createTime")
    private java.util.Date createTime;
}
