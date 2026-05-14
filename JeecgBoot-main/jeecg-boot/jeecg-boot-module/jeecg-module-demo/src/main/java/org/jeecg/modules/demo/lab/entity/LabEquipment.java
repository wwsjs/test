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
 * @Description: 实验室设备信息
 * @Author: jeecg-boot
 * @Date:   2026-05-13
 * @Version: V1.0
 */
@Data
@TableName("lab_equipment")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="实验室设备信息")
public class LabEquipment implements Serializable {
    private static final long serialVersionUID = 1L;

	/**设备ID*/
	@TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "设备ID")
    private java.lang.String id;
	/**设备编号*/
	@Excel(name = "设备编号", width = 15)
    @Schema(description = "设备编号")
    private java.lang.String equipmentCode;
	/**设备名称*/
	@Excel(name = "设备名称", width = 15)
    @Schema(description = "设备名称")
    private java.lang.String equipmentName;
	/**设备状态*/
	@Excel(name = "设备状态", width = 15, dicCode = "equipment_status")
	@Dict(dicCode = "equipment_status")
    @Schema(description = "设备状态")
    private java.lang.String status;
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
