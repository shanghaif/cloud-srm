package com.midea.cloud.srm.model.base.questionairesurvey.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.midea.cloud.srm.model.base.questionairesurvey.entity.SurveyScopeEmployee;
import com.midea.cloud.srm.model.base.questionairesurvey.entity.SurveyScopeJob;
import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author liuzh163@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021/4/16 14:09
 *  修改内容:
 * </pre>
 */
@Data
public class JobEmployeeDto extends BaseDTO {

    private String job;
    private List<EmployeeResultDto> employeeResultDtoList;
}
