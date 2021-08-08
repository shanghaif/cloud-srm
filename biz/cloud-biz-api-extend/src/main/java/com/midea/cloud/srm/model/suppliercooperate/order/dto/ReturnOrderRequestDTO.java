package com.midea.cloud.srm.model.suppliercooperate.order.dto;

import com.midea.cloud.srm.model.common.BasePage;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <pre>
 *  退货单 数据请求传输对象
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 *          <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/4/18 10:49
 *  修改内容:
 *          </pre>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = false)
public class ReturnOrderRequestDTO extends BasePage {

	private static final long serialVersionUID = 7552256271879287583L;
	private String returnOrderNumber;// 退货单号
	private String startReturnDate;// 退货时间开始
	private String endReturnDate;// 退货时间结束
	private Long materialId;// 物料ID
	private String materialName;// 物料名称
	private String materialCode;// 物料编码
	private Long organizationId;// 采购组织ID
	private String fullPathId; // 组织全路径虚拟ID
	private String organizationCode;// 采购组织编码
	private String organizationName;// 采购组织名称
	private String orderNumber;// 采购订单编号
	private String deliveryNumber;// 送货单编号
	private Long vendorId;// 供应商ID
	private String vendorCode;// 供应商编码
	private String vendorName;// 供应商名称
	private Long returnOrderId;// 退货单ID
	private String returnStatus;// 退货状态
	private String happenDateBegin;// 发生日期开始
	private String happenDateEnd;// 发生日期结束



}
