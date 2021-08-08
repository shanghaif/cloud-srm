package com.midea.cloud.srm.model.pm.po.dto;

import com.midea.cloud.srm.model.common.BasePage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 *
 *
 *
 * <pre>
 * 查询净价参数
 * </pre>
 *
 * @author zhizhao1.fan@meicloud.com
 * @version 1.00.00
 *
 *          <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020年4月27日 下午2:48:38
 *  修改内容:
 *          </pre>
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain = true)
public class NetPriceQueryDTO extends BasePage{

	private static final long serialVersionUID = 8409741531222576941L;
	private Long organizationId;// 采购组织ID
	private String fullPathId; // 组织全路径虚拟ID
	private Long materialId;// 物料ID
	private Long vendorId;// 供应商ID
	private Long orderQuantity;// 采购数量
	private Date requirementDate;// 需求日期

}
