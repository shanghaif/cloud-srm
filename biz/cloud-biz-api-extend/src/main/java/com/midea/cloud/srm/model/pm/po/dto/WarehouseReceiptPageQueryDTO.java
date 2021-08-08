package com.midea.cloud.srm.model.pm.po.dto;

import com.midea.cloud.srm.model.common.BasePage;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 *
 * <pre>
 * 订单入库分页查询DTO
 * </pre>
 *
 * @author zhizhao1.fan@meicloud.com
 * @version 1.00.00
 *
 *          <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: May 25, 20203:10:59 PM
 *  修改内容:
 *          </pre>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class WarehouseReceiptPageQueryDTO extends BasePage {

	private static final long serialVersionUID = -7351392455486052259L;


	private Long vendorId;// 供应商ID
	private Long organizationId;// 采购组织ID
	private String fullPathId; // 组织全路径虚拟ID
	private String organizationName;// 采购组织
	private Long materialId;// 物料ID
	private String receivedFactory;// 收货工厂
	private String happenDateBegin;// 发生日期开始
	private String happenDateEnd;// 发生日期结算
	private String writeOff;// 冲销标志

	/**
	 * 业务实体id
	 */
	private Long orgId;

	/**
	 * 供应商名称
	 */
	private String vendorName;

	/**
	 * 采购订单编号
	 */
	private String orderNumber;

	/**
	 * 物料编码
	 */
	private String materialCode;

	/**
	 * 物料名称
	 */
	private String materialName;

	/**
	 * 入库单号
	 */
	private String warehouseReceiptNumber;

	/**
	 * 送货单号
	 */
	private String deliveryNumber;

	/**
	 * 订单入库状态
	 */
	private String warehouseReceiptStatus;
}
