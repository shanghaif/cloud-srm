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
 *  修改日期: 2020/7/24
 *  修改内容:
 * </pre>
 */
@Data
public class OrgDto implements Serializable {
    // 组织ID
    private Long orgId;
    // 组织名字
    private String orgName;
    // 组织编码
    private String orgCode;
    // 全路径ID
    private String fullPathId;
}
