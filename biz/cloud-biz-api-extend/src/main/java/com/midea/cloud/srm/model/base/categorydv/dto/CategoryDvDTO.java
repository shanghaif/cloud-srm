package com.midea.cloud.srm.model.base.categorydv.dto;

import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

/**
*  <pre>
 *  品类分工 模型
 * </pre>
*
* @author zhuwl7@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-06 10:04:24
 *  修改内容:
 * </pre>
*/
@Data
public class CategoryDvDTO extends BaseDTO {


    /**
     * 品类分工ID
     */
    private Long categoryDvId;

    /**
     * 租户
     */
    private Long tenantId;



    /**
     * 组织ID
     */
    private Long orgId;

    /**
     * 组织全路径虚拟ID
     */
    private String fullPathId;

    /**
     * 组织名称
     */
    private String orgName;

    /**
     * 品类ID
     */
    private Long categoryId;

    /**
     * 品类ID
     */
    private String categoryCode;

    /**
     * 品类名称
     */
    private String categoryName;

    /**
     * 采购分类全名
     */
    private String categoryFullName;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户账号
     */
    private String userName;

    /**
     * 用户姓名
     */
    private String fullName;

    /**
     * 生效时间
     */
    private LocalDate startDate;

    /**
     * 失效时间
     */
    private LocalDate endDate;

    /**
     * 版本号
     */
    private Long version;

    /**
     * 创建人ID
     */
    private Long createdId;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 创建时间
     */
    private Date creationDate;

    /**
     * 创建人IP
     */
    private String createdByIp;

    /**
     * 最后更新人ID
     */
    private Long lastUpdatedId;

    /**
     * 最后更新人
     */
    private String lastUpdatedBy;

    /**
     * 最后更新时间
     */
    private Date lastUpdateDate;

    /**
     * 最后更新人IP
     */
    private String lastUpdatedByIp;


}
