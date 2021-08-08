package com.midea.cloud.srm.model.report.supplier.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * 
 * 
 * <pre>
 * 供应商合作月数
 * </pre>
 * 
 * @author  kuangzm
 * @version 1.00.00
 * 
 *<pre>
 * 	修改记录
 * 	修改后版本:
 *	修改人： 
 *	修改日期:2020年11月24日 下午12:52:38
 *	修改内容:
 * </pre>
 */
@Data
public class SupplierMonthsDTO implements Serializable {
	private Long companyId;//供应商ID
	private Integer months;//合作月数
	
}
