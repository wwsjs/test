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
 * @Description: 人员信息（非系统用户）
 * @Author: jeecg-boot
 * @Date:   2026-05-13
 * @Version: V1.0
 */
@Data
@TableName("lab_user")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="人员信息（非系统用户）")
public class LabUser implements Serializable {
    private static final long serialVersionUID = 1L;

	/**用户ID*/
	@TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "用户ID")
    private java.lang.String id;
	/**工号/学号*/
	@Excel(name = "工号/学号", width = 15)
    @Schema(description = "工号/学号")
    private java.lang.String workNo;
	/**姓名*/
	@Excel(name = "姓名", width = 15)
    @Schema(description = "姓名")
    private java.lang.String username;
	/**联系电话*/
	@Excel(name = "联系电话", width = 15)
    @Schema(description = "联系电话")
    private java.lang.String phone;
	/**邮箱*/
	@Excel(name = "邮箱", width = 15)
    @Schema(description = "邮箱")
    private java.lang.String email;
	/**办公室/实验室*/
	@Excel(name = "办公室/实验室", width = 15)
    @Schema(description = "办公室/实验室")
    private java.lang.String roomNo;
	/**人员类别*/
	@Excel(name = "人员类别", width = 15, dicCode = "person_type")
	@Dict(dicCode = "person_type")
    @Schema(description = "人员类别")
    private java.lang.String personType;
	/**年级*/
	@Excel(name = "年级", width = 15)
    @Schema(description = "年级")
    private java.lang.String grade;
	/**课题组编号*/
	@Excel(name = "课题组编号", width = 15, dictTable = "lab_group", dicText = "mentor", dicCode = "group_id")
	@Dict(dictTable = "lab_group", dicText = "mentor", dicCode = "group_id")
    @Schema(description = "课题组编号")
    private java.lang.String groupId;
	/**管理员/组长/用户*/
	@Excel(name = "管理员/组长/用户", width = 15, dicCode = "lab_role_code")
	@Dict(dicCode = "lab_role_code")
    @Schema(description = "管理员/组长/用户")
    private java.lang.String roleCode;
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
