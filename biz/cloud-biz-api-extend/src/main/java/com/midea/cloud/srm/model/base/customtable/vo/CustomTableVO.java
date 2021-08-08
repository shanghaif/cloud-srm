package com.midea.cloud.srm.model.base.customtable.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 
 * 
 * <pre>
 * 自定义表格
 * </pre>
 * 
 * @author zhizhao1.fan@meicloud.com
 * @version 1.00.00
 * 
 *          <pre>
 *  修改记录
 *  修改后版本: 
 *  修改人: 
 *  修改日期: 2020年4月22日 上午11:25:52  
 *  修改内容:
 *          </pre>
 */
@Data
@Accessors(chain = true)
public class CustomTableVO {

	/**
	 * 列编码
	 */
	private String columnCode;

	/**
	 * 列名称
	 */
	private String columnName;

	/**
	 * 是否勾选
	 */
	private String showFlag;

	/**
	 * 排序号
	 */
	private Integer orderNum;

}
