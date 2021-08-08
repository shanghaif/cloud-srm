package com.midea.cloud.srm.model.log.biz.dto;

import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author tanjl11@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/8/5 13:36
 *  修改内容:
 * </pre>
 */
@Data
public class BizOperateLogInfoDto extends BaseDTO {

    private String userAccount;

    private String beginTime;

    private String endTime;

    @NotEmpty(message="业务单据id不能为空")
    private String businessId;
    /**
     * 操作类型
     */
    @NotEmpty(message = "操作类型不能为空")
    private String operateType;
    /**
     * 操作说明
     */
    private String operateInfo;
    /**
     * 业务单据号名
     */
    @NotEmpty(message = "业务单据所属tab")
    private String businessTab;

    @NotEmpty(message = "业务单据号不能为空")
    private String businessNo;

    @NotEmpty(message = "所属模块不能为空")
    private String model;

}
