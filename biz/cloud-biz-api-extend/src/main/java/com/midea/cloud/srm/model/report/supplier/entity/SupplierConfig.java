package com.midea.cloud.srm.model.report.supplier.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.util.Date;
import com.midea.cloud.srm.model.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 
 * 供应商分析配置
 * 
 * <pre>
 * 。
 * </pre>
 * 
 * @author kuangzm
 * @version 1.00.00
 * 
 *          <pre>
 * 	修改记录
 * 	修改后版本:
 *	修改人： 
 *	修改日期:2020年12月3日 下午7:14:06
 *	修改内容:
 *          </pre>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_rep_supplier_config")
public class SupplierConfig extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 配置ID
	 **/
	@TableId("CONFIG_ID")
	private Long configId;
	/**
	 * 订单金额
	 **/
	@TableField("ORDER_AMOUNT")
	private BigDecimal orderAmount;
	/**
	 * 供应商区域分布阶段一
	 **/
	@TableField("AREA_ONE")
	private Integer areaOne;
	/**
	 * 供应商区域分布阶段二开始值
	 **/
	@TableField("AREA_TWO_START")
	private Integer areaTwoStart;
	/**
	 * 供应商区域分布阶段二结束值
	 **/
	@TableField("AREA_TWO_END")
	private Integer areaTwoEnd;
	/**
	 * 供应商区域分布阶段三开始值
	 **/
	@TableField("AREA_THREE_START")
	private Integer areaThreeStart;
	/**
	 * 供应商区域分布阶段三结束值
	 **/
	@TableField("AREA_THREE_END")
	private Integer areaThreeEnd;
	/**
	 * 供应商区域分布阶段四
	 **/
	@TableField("AREA_FOUR")
	private Integer areaFour;
	/**
	 * 采购金额供方占比阶段一
	 **/
	@TableField("PURCHASE_ONE")
	private Integer purchaseOne;
	/**
	 * 采购金额供方占比阶段二开始值
	 **/
	@TableField("PURCHASE_TWO_START")
	private Integer purchaseTwoStart;
	/**
	 * 采购金额供方占比阶段二结束值
	 **/
	@TableField("PURCHASE_TWO_END")
	private Integer purchaseTwoEnd;
	/**
	 * 采购金额供方占比阶段三开始值
	 **/
	@TableField("PURCHASE_THREE_START")
	private Integer purchaseThreeStart;
	/**
	 * 采购金额供方占比阶段四
	 **/
	@TableField("PURCHASE_FOUR")
	private Integer purchaseFour;
	/**
	 * 采购金额供方占比阶段三结束值
	 **/
	@TableField("PURCHASE_THREE_END")
	private Integer purchaseThreeEnd;
	/**
	 * 品类供方数占比阶段一
	 **/
	@TableField("CATEGORY_ONE")
	private Integer categoryOne;
	/**
	 * 品类供方数占比阶段二开始值
	 **/
	@TableField("CATEGORY_TWO_START")
	private Integer categoryTwoStart;
	/**
	 * 品类供方数占比阶段二结束值
	 **/
	@TableField("CATEGORY_TWO_END")
	private Integer categoryTwoEnd;
	/**
	 * 品类供方数占比阶段三开始值
	 **/
	@TableField("CATEGORY_THREE_START")
	private Integer categoryThreeStart;
	/**
	 * 品类供方数占比阶段三结束值
	 **/
	@TableField("CATEGORY_THREE_END")
	private Integer categoryThreeEnd;
	/**
	 * 品类供方数占比阶段四
	 **/
	@TableField("CATEGORY_FOUR")
	private Integer categoryFour;
	
	/**
	 * 品类降本金额阶段一
	 **/
	@TableField("CATEGORY_CR_ONE")
	private Integer categoryCrOne;
	/**
	 * 品类降本金额阶段二开始值
	 **/
	@TableField("CATEGORY_CR_TWO_START")
	private Integer categoryCrTwoStart;
	/**
	 * 品类降本金额阶段二结束值
	 **/
	@TableField("CATEGORY_CR_TWO_END")
	private Integer categoryCrTwoEnd;
	/**
	 * 品类降本金额阶段三开始值
	 **/
	@TableField("CATEGORY_CR_THREE_START")
	private Integer categoryCrThreeStart;
	/**
	 * 品类降本金额阶段三结束值
	 **/
	@TableField("CATEGORY_CR_THREE_END")
	private Integer categoryCrThreeEnd;
	/**
	 * 品类降本金额阶段四
	 **/
	@TableField("CATEGORY_CR_FOUR")
	private Integer categoryCrFour;

	/**
	 * 创建人ID
	 */
	@TableField(value = "CREATED_ID", fill = FieldFill.INSERT)
	private Long createdId;

	/**
	 * 创建人
	 */
	@TableField(value = "CREATED_BY", fill = FieldFill.INSERT)
	private String createdBy;

	/**
	 * 创建时间
	 */
	@TableField(value = "CREATION_DATE", fill = FieldFill.INSERT)
	private Date creationDate;

	/**
	 * 创建人IP
	 */
	@TableField(value = "CREATED_BY_IP", fill = FieldFill.INSERT)
	private String createdByIp;

	/**
	 * 最后更新时间
	 */
	@TableField(value = "LAST_UPDATE_DATE", fill = FieldFill.UPDATE)
	private Date lastUpdateDate;

	/**
	 * 最后更新人ID
	 */
	@TableField(value = "LAST_UPDATED_ID", fill = FieldFill.UPDATE)
	private Long lastUpdatedId;

	/**
	 * 最后更新人
	 */
	@TableField(value = "LAST_UPDATED_BY", fill = FieldFill.UPDATE)
	private String lastUpdatedBy;

	/**
	 * 最后更新人IP
	 */
	@TableField(value = "LAST_UPDATED_BY_IP", fill = FieldFill.UPDATE)
	private String lastUpdatedByIp;

	/**
	 * 租户
	 */
	@TableField("TENANT_ID")
	private Long tenantId;

	/**
	 * 版本号
	 */
	@TableField("VERSION")
	private Long version;
	
	@TableField("SYN_DATE")
	private Date synDate;

}
