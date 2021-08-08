package com.midea.cloud.srm.pr.shopcart.utils;

import java.util.LinkedHashMap;

/**
 * <pre>
 *  功能名称 购物车自定义导出工具
 * </pre>
 *
 * @author haiping2.li@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/9/29 17:12
 *  修改内容:
 * </pre>
 */
public class ShopCartExportUtils {
	/**
	 * 中文导出标题
	 */
	public static final LinkedHashMap<String,String> shopCartTitlesCH;

	static {
		shopCartTitlesCH = new LinkedHashMap<>();

		shopCartTitlesCH.put("purchaseType", "采购类型");
		shopCartTitlesCH.put("requirementDate", "需求时间"); //
		shopCartTitlesCH.put("requirementNum", "数量");
		shopCartTitlesCH.put("ifCatalog", "是否目录化");
		shopCartTitlesCH.put("materialCode", "物料编码");
		shopCartTitlesCH.put("materialName", "物料描述");
		shopCartTitlesCH.put("categoryName", "品类");
		shopCartTitlesCH.put("specification", "规格/型号");
		shopCartTitlesCH.put("unitName", "单位");
		shopCartTitlesCH.put("orgName", "业务实体");
		shopCartTitlesCH.put("organizationName", "库存组织");
		shopCartTitlesCH.put("contractNo", "合同编号");
		shopCartTitlesCH.put("unitPrice", "预算单价");
		shopCartTitlesCH.put("currencyName", "币种");

		/*shopCartTitlesCH.put("status", "状态");
		shopCartTitlesCH.put("returnReason", "退回原因");
		shopCartTitlesCH.put("summaryNickname", "汇总人");
		shopCartTitlesCH.put("noticeNickname", "通知人");
		shopCartTitlesCH.put("supplierCode", "供应商编码");
		shopCartTitlesCH.put("supplierName", "供应商名称");
		shopCartTitlesCH.put("createdBy", "创建人");
		shopCartTitlesCH.put("creationDate", "创建时间");*/
	}

	// 返回字段属性
	public static LinkedHashMap<String,String> getShopCartTitles() {
		return shopCartTitlesCH;
	}
}
