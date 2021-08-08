package com.midea.cloud.srm.model.suppliercooperate.order.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.Order;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <pre>
 *  采购订单 数据请求传输对象
 * </pre>
 *
 * @author chenwj92
 * @version 1.00.00
 *
 *          <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/8/03 15:44
 *  修改内容:
 *          </pre>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class OrderRequestDTO extends Order {

	private static final long serialVersionUID = 1L;



	/**
	 * 业务实体id
	 */
	private Long orgId;
	/**
	 * 交货地点
	 */
	private List<String> ceeaDeliveryPlaces;
	/**
	 * 物料编码或者物料名称 用物料id的List接收参数 多选
	 */
	private List<Long> materialIds;

	/**
	 * 供应商编码
	 */
	private List<String> vendorCodes;

	/**
	 * 库存组织集合
	 */
	private List<Long> organizationIdList;
	/**
	 * 订单状态list
	 */
	private List<String> orderStatusList;
	/**
	 * 物料ID
	 */
	@TableField("MATERIAL_ID")
	private Long materialId;

	/**
	 * 物料编码
	 */
	@TableField("MATERIAL_CODE")
	private String materialCode;
	/**
	 * 物料名称
	 */
	@TableField("MATERIAL_NAME")
	private String materialName;
	/**
	 * 采购分类名称(物料小类名称)(确认后弃用)
	 */
	@TableField("CEEA_CATEGORY_NAME")
	private String ceeaCategoryName;

	/**
	 * 剩余收货数量
	 */
	private BigDecimal unreceivedSum;
	/**
	 * 采购订单明细ID
	 */
	private Long orderDetailId;

	/*隆基使用*/

	/**
	 * 订单编号
	 */
	private String orderNumber;

	/**
	 * 订单类型
	 */
	private String orderType;

	/**
	 * 范围日期-开始
	 */
	private String startTime;
	/**
	 * 范围日期-结束
	 */
	private String endTime;

	/**
	 * 业务实体id多选
	 */
	private List<Long> orgIds;

	/**
	 * 供应商编号或名称
	 */
	private String vendorKey;

	/**
	 * 成本类型
	 */
	private String costType;

	/**
	 * 订单状态
	 */
	private String orderStatus;

	/**
	 * 是否寄售
	 */
	private String ceeaIfConSignment;

	/**
	 * 是否电站业务
	 */
	private String ceeaIfPowerStationBusiness;

	/**
	 * 是否供方确认
	 */
	private String ceeaIfSupplierConfirm;

	/**
	 * 采购员id多选
	 */
	private List<Long> userIds;

	/**
	 * 版本
	 */
	private Long version;

	/**
	 * 订单变更是否完成
	 */
	private String ceeaIfEditDone;
	/**
	 * 已勾选的订单id
	 */
	private List<Long> orderDetailIdList;
	/**
	 * 业务实体集合
	 */
	private List<Long> orgIdList;
	/**
	 * 合同编号(longi)
	 */
	@TableField("CEEA_CONTRACT_NO")
	private String ceeaContractNo;
	/**
	 * 合同资产类别
	 */
	private  String assetType;
	/**
	 * 采购申请编号(longi)
	 */
	@TableField("CEEA_REQUIREMENT_HEAD_NUM")
	private String ceeaRequirementHeadNum;
	/**
	 * 查看类型（Y是查看。N是添加）
	 */
	private String viewType;
	/**
	 * 到货计划号
	 */
	private String deliverPlanNum;
	/**
	 * 采购分类ID(物料小类id)
	 */
	private Long categoryId;

	/**
	 * 采购分类编码(物料小类编码)
	 */
	private String categoryCode;

	/**
	 * 采购分类全名(物料小类名称)
	 */
	private String categoryName;

	/**
	 * 订单行状态
	 */
	private String orderDetailStatus;

	private List<Long> ids;
	private String refusedReason;
	private String keyWord;

	/**
	 * 物料小类id
	 */
	private List<Long> purchaseCategoryIds;
}
