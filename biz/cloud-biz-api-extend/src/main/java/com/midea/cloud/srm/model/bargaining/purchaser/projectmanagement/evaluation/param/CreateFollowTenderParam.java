package com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.evaluation.param;

import java.util.Date;

import lombok.Data;

/**
 * 
 * 
 * <pre>
 * 发起跟标确认
 * </pre>
 * 
 * @author zhizhao1.fan@meicloud.com
 * @version 1.00.00
 * 
 *          <pre>
 *  修改记录
 *  修改后版本: 
 *  修改人: 
 *  修改日期: 2020年4月6日 下午3:58:48  
 *  修改内容:
 *          </pre>
 */
@Data
public class CreateFollowTenderParam {

	private Long bidingId;// 招标ID
	private Date startTime;// 开始时间
	private Date endTime;// 结束时间

}
