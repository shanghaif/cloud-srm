package com.midea.cloud.srm.model.base.organization.dto;

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
 *  修改日期: 2020/7/23
 *  修改内容:
 * </pre>
 */
@Data
public class OrgTypeDto implements Serializable {
    /**
     * 组织类型编码
     */
    private String organizationTypeCode;
    /**
     * 组织类型名字
     */
    private String organizationTypeName;
}
