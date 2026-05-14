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
 * @Description: 项目信息
 * @Author: jeecg-boot
 * @Date:   2026-05-13
 * @Version: V1.0
 */
@Data
@TableName("lab_project")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="项目信息")
public class LabProject implements Serializable {
    private static final long serialVersionUID = 1L;

	/**项目ID*/
	@TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "项目ID")
    private java.lang.String id;
	/**项目编号*/
	@Excel(name = "项目编号", width = 15)
    @Schema(description = "项目编号")
    private java.lang.String projectNo;
	/**项目名称*/
	@Excel(name = "项目名称", width = 15)
    @Schema(description = "项目名称")
    private java.lang.String projectName;
	/**开始时间*/
	@Excel(name = "开始时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Schema(description = "开始时间")
    private java.util.Date startDate;
	/**结束时间*/
	@Excel(name = "结束时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Schema(description = "结束时间")
    private java.util.Date endDate;
	/**项目负责人ID*/
	@Excel(name = "项目负责人ID", width = 15, dictTable = "lab_user", dicText = "username", dicCode = "id")
	@Dict(dictTable = "lab_user", dicText = "username", dicCode = "id")
    @Schema(description = "项目负责人ID")
    private java.lang.String leaderId;
	/**项目成员ID*/
	@Excel(name = "项目成员ID", width = 15, dictTable = "lab_user", dicText = "username", dicCode = "id")
	@Dict(dictTable = "lab_user", dicText = "username", dicCode = "id")
    @Schema(description = "项目成员ID")
    private java.lang.String memberId;
	/**合同经费*/
	@Excel(name = "合同经费", width = 15)
    @Schema(description = "合同经费")
    private java.lang.String contractAmount;
	/**所属组*/
	@Excel(name = "所属组", width = 15, dictTable = "lab_group", dicText = "mentor", dicCode = "group_id")
	@Dict(dictTable = "lab_group", dicText = "mentor", dicCode = "group_id")
    @Schema(description = "所属组")
    private java.lang.String groupId;
	/**createTime*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Schema(description = "createTime")
    private java.util.Date createTime;
	/**updateTime*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Schema(description = "updateTime")
    private java.util.Date updateTime;
}
