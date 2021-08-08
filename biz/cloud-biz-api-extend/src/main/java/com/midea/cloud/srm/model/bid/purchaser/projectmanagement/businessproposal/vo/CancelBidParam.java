package com.midea.cloud.srm.model.bid.purchaser.projectmanagement.businessproposal.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 
 * 
 * <pre>
 * 作废投标
 * </pre>
 * 
 * @author zhizhao1.fan@meicloud.com
 * @version 1.00.00
 * 
 *          <pre>
 *  修改记录
 *  修改后版本: 
 *  修改人: 
 *  修改日期: 2020年4月11日 上午10:27:57  
 *  修改内容:
 *          </pre>
 */
@Data
@Accessors(chain = true)
public class CancelBidParam {

	private Long orderHeadId;// 投标头表ID
	private String rejectReason;// 作废原因

}
