package com.midea.cloud.srm.model.supplier.change.dto;

import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;

import java.util.Date;


/**
 *  <pre>
 *  公司基本信息 模型
 * </pre>
 *
 * @author zhuwl7@meicloud.com
 * @version 1.00.00
 *
 *  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-11 11:01:27
 *  修改内容:
 * </pre>
 */
@Data
public class ChangeRequestDTO extends BaseDTO {

    /**
     * 单号
     */
    private String changeApplyNo;

    /**
     * 变更状态
     */
    private String changeStatus;

    /**
     * 公司ID
     */
    private Long companyId;

    /**
     * 公司CODE
     */
    private String companyCode;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 三证号码
     */
    private String lcCode;

    /**
     * 法定代表人
     */
    private String legalPerson;

    /**
     * 创建开始时间
     */
    private Date startDate;

    /**
     * 创建结束时间；
     */
    private Date endDate;

}
