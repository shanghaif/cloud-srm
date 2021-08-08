package com.midea.cloud.srm.model.base.questionairesurvey.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;

import java.util.Date;

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
 *  修改日期: 2021/4/19 13:56
 *  修改内容:
 * </pre>
 */
@Data
public class SurveyResultDto extends BaseDTO {
    /**
     * 选项ID
     */
    private Long selectionId;

    /**
     * 发布供应商范围ID
     */
    private Long vendorScopeId;
    /**
     * 问题结果内容；如选项A、B、C、D……
     */
    private String resultValue;
    /**
     * 问题ID
     */
    private Long questionId;
    /**
     * 员工范围ID
     */
    private Long employeeScopeId;
    /**
     * 文件上传ID
     */
    private Long fileUploadId;

}
