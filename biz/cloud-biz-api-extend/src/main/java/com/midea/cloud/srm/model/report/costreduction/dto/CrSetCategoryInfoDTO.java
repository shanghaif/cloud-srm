package com.midea.cloud.srm.model.report.costreduction.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

/**
 * 
 * 降本分析配置
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
public class CrSetCategoryInfoDTO implements Serializable{

    private static final long serialVersionUID = 1L;
    
    /**
     * ID
     */
    private Long infoId;
    
    /**
     * 物料配置ID
     */
    private Long setCategoryId;
    
    /**
     * 物料名称
     */
    private String categoryName;
    /**
     * 物料ID
     */
    private Long categoryId;

    /**
     * 冻结价格
     */
    private BigDecimal rate;
}
