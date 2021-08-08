package com.midea.cloud.srm.model.bid.purchaser.projectmanagement.businessproposal.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 
 * 
 * <pre>
 * 商务评标列表
 * </pre>
 * 
 * @author zhizhao1.fan@meicloud.com
 * @version 1.00.00
 * 
 *          <pre>
 *  修改记录
 *  修改后版本: 
 *  修改人: 
 *  修改日期: 2020年3月25日 上午10:33:26  
 *  修改内容:
 *          </pre>
 */
@Data
@Accessors(chain = true)
public class BusinessItemVO {

	private Long orderHeadId;// 投标头表ID
	private Integer round;// 轮次
	private String vendorCode;// 供应商编码
	private String vendorName;// 供应商名称
	private String bidDetail;// 投标详情
	private Date submitTime;// 提交时间
	private String rejectReason;// 作废原因
	private String orderStatus;// 投标状态
	private Long bidVendorId;//报名表，用于重新生成

}
