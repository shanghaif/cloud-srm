package com.midea.cloud.srm.model.report.costreduction.dto;

import java.util.Date;
import java.util.List;

import com.midea.cloud.srm.model.common.BaseDTO;

import lombok.Data;

@Data
public class CostReductionParamDTO extends BaseDTO {
	private static final long serialVersionUID = 1L;
    private Integer year;//年
    private Integer season;//季度
    private Date comfirmTimeBegin;//起始时间
    private Date comfirmTimeEnd;//结束时间
    private String organizationTypeCode;//组织类型编码
    private List<String> list;//fullPathId
    private String fullPathId;//组织路径
    private Integer level; //品类等级
    
    private String orderStatus;//订单状态
    private String orderType;//订单类型
    private String warehouseReceiptStatus;//送货状态
    
    private String type;//类型
    
    private Long categoryId;
    
    private String materialCode;//物料编码
    
    private String startMonth;//开始月份
    
    private String endMonth;//结束月份
}
