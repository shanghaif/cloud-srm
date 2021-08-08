package com.midea.cloud.srm.model.report.supplier.dto;

import java.util.Date;
import java.util.List;

import com.midea.cloud.srm.model.common.BaseDTO;

import lombok.Data;

/**
 * 
 * 
 * <pre>
 * 。供应商分析入参
 * </pre>
 * 
 * @author  kuangzm
 * @version 1.00.00
 * 
 *<pre>
 * 	修改记录
 * 	修改后版本:
 *	修改人： 
 *	修改日期:2020年11月24日 上午8:33:41
 *	修改内容:
 * </pre>
 */
@Data
public class SupplierParamDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;
    private Integer year;//年
    private Integer season;//季度
    private Date startDate;//起始时间
    private Date endDate;//结束时间
    private String organizationTypeCode;//组织类型编码
    private List<String> list;//fullPathId
    private String fullPathId;//组织路径
    private String Amount;//活跃金额判断
    private Long categoryId;
    private String materialCode;
}
