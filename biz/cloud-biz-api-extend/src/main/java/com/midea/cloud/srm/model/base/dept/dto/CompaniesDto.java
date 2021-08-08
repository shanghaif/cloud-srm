package com.midea.cloud.srm.model.base.dept.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * <pre>
 *
 * </pre>
 *
 * @author wangpr@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/10/7
 *  修改内容:
 * </pre>
 */
@Data
public class CompaniesDto implements Serializable {

    private static final long serialVersionUID = -2137593583412125741L;
    /**
     * 公司名称
     */
    private String companyDescr;

    /**
     * SRM公司ID
     */
    private String company;
}
