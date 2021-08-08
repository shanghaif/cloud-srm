package com.midea.cloud.srm.model.report.costreduction.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.midea.cloud.srm.model.common.BaseDTO;

import lombok.Data;

/**
 * 
 * 降本分析配置品类
 * <pre>
 * 。
 * </pre>
 * 
 * @author  kuangzm
 * @version 1.00.00
 * 
 *<pre>
 * 	修改记录
 * 	修改后版本:
 *	修改人： 
 *	修改日期:2020年12月8日 下午14:00:06
 *	修改内容:
 * </pre>
 */
@Data
public class CrSetCategoryDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;
    
    /**
     * 品类配置ID
     */
    private Long setCategoryId;
    
    /**
     * 配置ID
     */
    private Long setId;
    
    /**
     * 品类名称
     */
    private String categoryNames;
    
    /**
     * 品类ID
     */
    private String categoryIds;
    
    /**
     * 年度总体降本率
     */
    private BigDecimal rate;

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
     * 最后更新时间
     */
    private Date lastUpdateDate;

    /**
     * 最后更新人ID
     */
    private Long lastUpdatedId;

    /**
     * 最后更新人
     */
    private String lastUpdatedBy;

    /**
     * 最后更新人IP
     */
    private String lastUpdatedByIp;

    /**
     * 租户
     */
    private Long tenantId;

    /**
     * 版本号
     */
    private Long version;

}
